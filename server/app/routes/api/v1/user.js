module.exports = function (settings, router) {

    var jwt = require('jwt-simple');
    var User = require('../../../models/mongodb/user');

    var passport = require('passport');

    /**
     * @api {post} /api/v1/user/signup   Signup
     * @apiName Signup
     * @apiGroup User
     * @apiDescription Sign-up a new user.
     * @apiVersion 1.0.0
     *
     * @apiParam {String} email             email of the user.
     * @apiParam {String} username          username of the user.
     * @apiParam {String} password          password of the user.
     * @apiParam {String} [fullname]        full name of the user.
     *
     * @apiSuccess {Boolean} success true.
     * @apiSuccess {String} msg message of the successful creation of the user.
     */
    router.post('/user/signup', function (req, res) {
        if (!req.body.username || !req.body.email || !req.body.password) {
            res.json({success: false, msg: 'Please pass username and password.'});
        } else {
            var newUser = new User({
                username: req.body.username,
                password: req.body.password,
                fullname: req.body.fullname,
                email: req.body.email
            });
            // save the user
            newUser.save(function (err) {
                if (err) {
                    return res.json({success: false, msg: err});
                }
                res.status(200).json({success: true, msg: 'Successful created new user.'});
            });
        }
    });


    /**
     * @api {post} /api/v1/user/login Login
     * @apiName Login
     * @apiGroup User
     * @apiDescription Authenticates the user.
     * @apiVersion 1.0.0
     *
     * @apiParam {String} username      username of the user.
     * @apiParam {String} password      password of the user.
     *
     * @apiSuccess {Boolean} success true.
     * @apiSuccess {String} token the JWT token.
     * @apiSuccess {json} user info.
     *
     * @apiSuccessExample {json} Success-Response:
     *    HTTP/1.1 200 OK
     *    {
     *         "success": true,
     *         "token": "",
     *         "user": {
     *           "username": "jm",
     *           "fullname": "John Medeski",
     *           "email": "jm@mickey.dw",
     *           "role": "standard",
     *           "picture": "placeholder.png"
     *         }
     *       }
     *
     */
    router.post('/user/login', function (req, res) {
        User.findOne({
            $or: [
                {username: req.body.username},
                {email: req.body.username},
            ]
        }, function (err, user) {

            if (err) throw err;

            if (!user) {
                res.send({success: false, msg: 'Authentication failed. User not found.'});
            } else {
                // check if password matches
                user.comparePassword(req.body.password, function (err, isMatch) {
                    if (isMatch && !err) {
                        // if user is found and password is right create a token
                        var token = jwt.encode({_id: user._id, username: user.username}, settings.config.secret);

                        var userInfo = JSON.parse(JSON.stringify(user));

                        delete userInfo['_id'];

                        delete userInfo['__v'];

                        delete userInfo['password'];

                        res.json({success: true, token: 'JWT ' + token, user: userInfo});

                    } else {
                        res.send({success: false, msg: 'Authentication failed. Wrong password.'});
                    }
                });
            }
        });
    });


    /**
     * @api {put} /api/v1/user/password Update User password
     * @apiName Update User password
     * @apiGroup User
     * @apiDescription Update the user password.
     * @apiVersion 1.0.0
     *
     * @apiHeader {String} authentication User access token.
     *
     * @apiparam {String} oldPassword The current password of the user.
     * @apiparam {String} newPassword The new password of the user.
     * @apiparam {String} confirmPassword The confirm password (needs to be equal to newPassword).
     *
     *
     * @apiSuccessExample Success-Response
     * HTTP/1.1 200 OK
     * {
     *      "success": true,
     *      "msg": "Password successfully updated."
     * }
     *
     *
     * @apiError NoCurrentPassword  No current password.
     * @apiErrorExample NoCurrentPassword:
     * HTTP/1.1 400 Bad Request
     * {
     *     "success": false,
     *     "msg": "Old password should be provided."
     * }
     *
     *
     * @apiError NoNewPassword  No new password.
     * @apiErrorExample NoNewPassword:
     * HTTP/1.1 400 Bad Request
     * {
     *     "success": false,
     *     "msg": "New password should be provided."
     * }
     *
     *
     * @apiError NoConfirmPassword  No confirm password.
     * @apiErrorExample NoConfirmPassword:
     * HTTP/1.1 400 Bad Request
     * {
     *     "success": false,
     *     "msg": "Confirm password should be provided."
     * }
     *
     *
     * @apiError PasswordsDoNoMatch New passwords don't match.
     * @apiErrorExample PasswordsDoNoMatch:
     * HTTP/1.1 400 Bad Request
     * {
     *     "success": false,
     *     "msg": "Password and confirm password do not match."
     * }
     *
     * @apiError UserNotFound   User not found.
     * @apiErrorExample UserNotFound:
     * HTTP/1.1 404 Not Found
     * {
     *     "success": false,
     *     "msg": "User not found."
     * }
     *
     */
    router.put('/user/password', passport.authenticate('jwt', {session: false}), function (req, res) {

        if (!req.body.oldPassword) {
            res.status(400).json({success: false, msg: 'Old password should be provided'});
        } else if (!req.body.newPassword) {
            res.status(400).json({success: false, msg: 'New password should be provided'});
        } else if (!req.body.confirmPassword) {
            res.status(400).json({success: false, msg: 'Confirm password should be provided'});
        } else if (req.body.newPassword !== req.body.confirmPassword) {
            res.status(400).json({success: false, msg: 'Password and confirm password do not match'});
        } else {
            User.findOne({
                username: req.user.username
            }, function (err, user) {

                if (err) throw err;

                if (!user) {
                    res.status(404).send({success: false, msg: 'User not found.'});
                } else {
                    // check if password matches
                    user.comparePassword(req.body.oldPassword, function (err, isMatch) {

                        if (isMatch && !err) {

                            user['password'] = req.body.newPassword;

                            user.save(function (err) {
                                if (err) {
                                    return res.status(500).json({success: false, msg: err});
                                } else {
                                    res.status(200).json({success: true, msg: 'Password successfully updated.'});
                                }
                            });

                        } else {
                            res.status(400).send({success: false, msg: 'Old password does not match.'});
                        }
                    });
                }
            });
        }

    });


    /**
     * @api {get} /api/v1/user Get User info
     * @apiName User info
     * @apiGroup User
     * @apiDescription Retrieves info on the user.
     * @apiVersion 1.0.0
     *
     * @apiHeader {String} authentication User access token.
     *
     * @apiSuccessExample {json} Success-Response:
     *    HTTP/1.1 200 OK
     *    {
     *         "username": "jm",
     *         "fullname": "John Medeski",
     *         "email": "jm@mickey.dw",
     *         "picture": "placeholder.png"
     *         "language": "it_IT"
     *    }
     *
     */
    router.get('/user', passport.authenticate('jwt', {session: false}), function (req, res) {

        var userInfo = JSON.parse(JSON.stringify(req.user));

        delete userInfo['_id'];

        delete userInfo['__v'];

        delete userInfo['password'];

        res.json(userInfo);
    });


    /**
     * @api {put} /api/v1/user Update User
     * @apiName Update user
     * @apiGroup User
     * @apiDescription Update info on the user.
     * @apiVersion 1.0.0
     *
     * @apiHeader {String} authentication User access token.
     *
     * @apiParam {String} [email]           email of the user.
     * @apiParam {String} [fullname]        full name of the user.
     * @apiParam {String} [picture]         path to the picture of the user.
     * @apiParam {String} [language]        default language for the user.
     *
     * @apiSuccessExample {json} Success-Response:
     *    HTTP/1.1 200 OK
     *    {
     *      success: true,
     *      msg: 'User successfully updated.'
     *    }
     *
     */
    router.put('/user', passport.authenticate('jwt', {session: false}), function (req, res) {

        User.findOne({
            username: req.user.username
        }, function (err, user) {

            if (err) throw err;

            if (!user) {
                res.status(404).send({success: false, msg: 'User not found.'});
            } else {

                console.log("%j", user);
                console.log("%j", req.body);

                user["email"] = (typeof req.body.email !== 'undefined' && req.body.email.length > 0)
                    ? req.body.email
                    : user["email"];

                user["fullname"] = (typeof req.body.fullname !== 'undefined' && req.body.fullname.length > 0)
                    ? req.body.fullname
                    : user["fullname"];

                user["picture"] = (typeof req.body.picture !== 'undefined' && req.body.picture.length > 0)
                    ? req.body.picture
                    : user["picture"];

                user["language"] = (typeof req.body.language !== 'undefined' && req.body.language.length > 0)
                    ? req.body.language
                    : user["language"];

                user.save(function (err) {
                    if (err) {
                        return res.status(500).json({success: false, msg: err});
                    } else {
                        res.status(200).json({success: true, msg: 'User successfully updated.'});
                    }
                });

            }
        });
    });

    return router;

}