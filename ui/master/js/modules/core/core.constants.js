/**=========================================================
 * Module: constants.js
 * Define constants to inject across the application
 =========================================================*/

(function () {
    'use strict';
    var yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);

    angular
        .module('app.core')
        .constant('APP_MEDIAQUERY', {
            'desktopLG': 1200,
            'desktop': 992,
            'tablet': 768,
            'mobile': 480
        })
        .constant(
            'API_URL', 'http://147.75.205.77:3033/api/v1'
            //'API_URL', 'http://151.80.36.187:3000/api/v1'
            //'API_URL', 'http://localhost:3000/api/v1'
        )
        .constant(
            'DEFAULT_POLLER_DELAY', 10000
        )
        .constant('LOCAL_STORAGE_KEYS', {
            'TOKEN': 'TOKEN',
            'USER': 'USER',
            'PREFERENCES': 'PREFERENCES'
        })
    ;
})();