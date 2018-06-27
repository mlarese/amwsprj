exports.init = function(callback) {

    var User     = require('../../app/models/mongodb/user');
    var users    = require("./data/users.json")["users"];

    User.remove({}, function(){

        users.forEach(function(cl) {

            var user = new User(cl);
            user.save(function(err) {
                if (err) {
                    console.log("Error when saving user with id %s",user.id);
                }
            });

        });

        callback('done');

    });
}

