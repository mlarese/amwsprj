var JwtStrategy = require('passport-jwt').Strategy;

// load up the user model
var User = require('../app/models/mongodb/user.js');
var Config = require('../config/config');
var config = new Config();

module.exports = function(passport) {
    var opts = {};
    opts.secretOrKey = config.secret;
    passport.use(new JwtStrategy(opts, function(jwt_payload, done) {
        User.findOne({username: jwt_payload.username}, function(err, user) {
            if (err) {
                return done(err, false);
            }
            if (user) {
                done(null, user);
            } else {
                done(null, false);
            }
        });
    }));
};