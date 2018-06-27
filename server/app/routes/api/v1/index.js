module.exports = function (settings) {

    var express = require('express');
    var router = express.Router();

    require('./user')(settings, router);
    require('./schedules')(settings, router);
    require('./amazon')(settings, router);
    require('./system')(settings, router);
    return router;
}