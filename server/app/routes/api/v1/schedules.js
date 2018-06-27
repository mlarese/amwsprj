var Config = require('../../../../config/config.js');
var config = new Config();

module.exports = function (settings, router) {
    var request = require('request');
    var jwt = require('jwt-simple');
    var passport = require('passport');

    router.get('/schedules', passport.authenticate('jwt', {session: false}), function (req, res) {
        request({
            uri: settings['config']['core']['host'] + '/schedules/'
        }).pipe(res);
    });

    router.get('/schedules/:jobGroup/:jobId', passport.authenticate('jwt', {session: false}), function (req, res) {
        request({
            uri: settings['config']['core']['host'] + '/schedules/' + req.params.jobGroup + '/' + req.params.jobId
        }).pipe(res);
    });

    router.delete('/schedules/:jobGroup/:jobId', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.delete({
            uri: settings['config']['core']['host'] + '/schedules/' + req.params.jobGroup + '/' + req.params.jobId
        }).pipe(res);
    });


    router.get('/schedules/:jobGroup', passport.authenticate('jwt', {session: false}), function (req, res) {
        request({
            uri: settings['config']['core']['host'] + '/schedules/' + req.params.jobGroup
        }).pipe(res);
    });

    router.post('/schedules/:jobGroup/:jobId/enable', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.post({
            uri: settings['config']['core']['host'] + '/schedules/' + req.params.jobGroup + '/' + req.params.jobId + '/enable'
        }).pipe(res);
    });

    router.post('/schedules/:jobGroup/:jobId/disable', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.post({
            uri: settings['config']['core']['host'] + '/schedules/' + req.params.jobGroup + '/' + req.params.jobId + '/disable'
        }).pipe(res);
    });
}