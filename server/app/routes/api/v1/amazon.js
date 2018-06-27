var Config = require('../../../../config/config.js');
var config = new Config();
var constants = require('../../../services/v1/constants.js');

module.exports = function (settings, router) {
    var request = require('request');
    var jwt = require('jwt-simple');
    var passport = require('passport');
    const jobGroup = 'amazon';

    router.post('/amazon/:marketplace/schedules/data-feed', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.post({
            uri: settings['config']['core']['host'] + '/schedules/' + req.params.marketplace + '/amazon',
            json: {
                "triggers": [
                    {
                        "name": req.params.marketplace + "-trigger",
                        "description": "Data feed trigger for " + req.params.marketplace.toUpperCase(),
                        "cronExpression": req.body.cron_expression,
                        "timeZone": "Europe/Rome"
                    }
                ],
                "job": {
                    "jobDataMap": {
                        "ftpHost": req.body.ftp_host,
                        "ftpPort": req.body.ftp_port,
                        "ftpUser": req.body.ftp_user,
                        "ftpPassword": req.body.ftp_password,
                        "pathToFile": req.body.path_to_file,
                        "marketplaceId": constants[req.params.marketplace]
                    }
                }
            }
        }).pipe(res);
    });

    router.post('/amazon/schedules/submission-report', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.post({
            uri: settings['config']['core']['host'] + '/schedules/' + jobGroup + '/amazon-submission-report',
            json: {
                "triggers": [
                    {
                        "name": "amazon-submission-report-trigger",
                        "description": "Amazon submission report trigger",
                        "cronExpression": req.body.cron_expression,
                        "timeZone": "Europe/Rome"
                    }
                ]
            }
        }).pipe(res);
    });

    router.post('/amazon/jobs/submission-report', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.post({
            uri: settings['config']['core']['host'] + '/batch/operations/jobs/amazon-submission-report',
            form: {
                jobParameters: 'ts=' + Math.round(new Date() / 1000)
            }
        }).pipe(res);
    });

    router.post('/amazon/jobs/data-feed', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.post({
            uri: settings['config']['core']['host'] + '/batch/operations/jobs/amazon',
            form: {
                jobParameters: 'ts=' + Math.round(new Date() / 1000) + ',pathToFile=' + req.body.path_to_file + ',marketplaceId=' + req.body.marketplace_id + ',ftpHost=' + req.body.ftp_host + ',ftpPort=' + req.body.ftp_port + ',ftpUser=' + req.body.ftp_user + ',ftpPassword= ' + req.body.ftp_password
            }
        }).pipe(res);
    });

    router.get('/amazon/:marketplace/submission-reports', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.get({
            uri: settings['config']['core']['host'] + '/repositories/amazonFeedSubmissions/search/findAllByMarketplaceId',
            qs: {
                page: req.query.page - 1,
                size: req.query.count,
                sort: 'submittedDate,desc',
                marketplaceId: constants[req.params.marketplace]
            }
        }).pipe(res);
    });

    router.get('/amazon/feed-submission-result/:feedSubmissionId', function (req, res) {
        request.get({
            uri: settings['config']['core']['host'] + '/mws/feed-submission-result/' + req.params.feedSubmissionId
        }).pipe(res);
    });

    router.get('/amazon/brands', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.get({
            uri: settings['config']['core']['host'] + '/repositories/brands',
            qs: {
                page: req.query.page - 1,
                size: req.query.count,
                sort: 'name,asc'
            }
        }).pipe(res);
    });

    router.get('/amazon/brands/:brandId', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.get({
            uri: settings['config']['core']['host'] + '/repositories/brands/' + req.params.brandId
        }).pipe(res);
    });

    router.post('/amazon/brands', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.post({
            uri: settings['config']['core']['host'] + '/repositories/brands',
            json: {
                name: req.body.name,
                _id: req.body._id
            }
        }).pipe(res);
    });

    router.delete('/amazon/brands/:brandId', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.delete({
            uri: settings['config']['core']['host'] + '/repositories/brands/' + req.params.brandId
        }).pipe(res);
    });

    router.get('/amazon/rules', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.get({
            uri: settings['config']['core']['host'] + '/repositories/rules'
        }).pipe(res);
    });

    router.get('/amazon/:marketplace/rules', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.get({
            uri: settings['config']['core']['host'] + '/repositories/rules/search/findAllByMarketplaceId',
            qs: {
                page: req.query.page - 1,
                size: req.query.count,
                sort: '_id,asc',
                marketplaceId: constants[req.params.marketplace]
            }
        }).pipe(res);
    });

    router.get('/amazon/rules/:ruleId', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.get({
            uri: settings['config']['core']['host'] + '/repositories/rules/' + req.params.ruleId
        }).pipe(res);
    });

    router.post('/amazon/rules', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.post({
            uri: settings['config']['core']['host'] + '/repositories/rules',
            json: {
                name: req.body.name,
                fromDate: req.body.fromDate,
                toDate: req.body.toDate,
                isActive: req.body.isActive,
                marketplaceId: constants[req.body.marketplace]
            }
        }).pipe(res);
    });

    router.delete('/amazon/rules/:ruleId', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.delete({
            uri: settings['config']['core']['host'] + '/repositories/rules/' + req.params.ruleId
        }).pipe(res);
    });

    router.put('/amazon/rules/:ruleId', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.put({
            uri: settings['config']['core']['host'] + '/repositories/rules/' + req.params.ruleId,
            json: {
                name: req.body.name,
                fromDate: req.body.fromDate,
                toDate: req.body.toDate,
                isActive: req.body.isActive,
                marketplaceId: req.body.marketplaceId
            }
        }).pipe(res);
    });

    router.get('/amazon/rules/:ruleId/brands', passport.authenticate('jwt', {session: false}), function (req, res) {
        request.get({
            uri: settings['config']['core']['host'] + '/repositories/rules/' + req.params.ruleId + '/brands'
        }).pipe(res);
    });

    router.put('/amazon/rules/:ruleId/brands', passport.authenticate('jwt', {session: false}), function (req, res) {
        var rawListOfBrands = '';

        for (var i = 0, len = req.body.length; i < len; i++) {
            rawListOfBrands += settings['config']['core']['host'] + '/repositories/brands/' + req.body[i] + '\n';
        }

        request.put({
            uri: settings['config']['core']['host'] + '/repositories/rules/' + req.params.ruleId + '/brands',
            headers: {
                'Content-Type': 'text/uri-list'
            },
            body: rawListOfBrands
        }).pipe(res);
    });
}