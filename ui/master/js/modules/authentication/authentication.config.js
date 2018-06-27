(function() {
    'use strict';

    angular
        .module('app.authentication')
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('AuthInterceptor');
        });
})();
