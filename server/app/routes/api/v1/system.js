var Config = require('../../../../config/config.js');
var config = new Config();


module.exports = function (settings, router) {

    var jwt = require('jwt-simple');
    var passport = require('passport');
    var request = require('request');

    router.get('/health', passport.authenticate('jwt', {session: false}), function (req, res) {
        request({
            uri: settings['config']['core']['host'] + '/health/'
        }).pipe(res);
    });

    router.get('/log', passport.authenticate('jwt', {session: false}), function (req, res) {
        request({
            uri: settings['config']['core']['host'] + '/logfile'
        }).pipe(res);
    });
}