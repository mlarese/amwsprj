(function() {
    'use strict';

    angular
        .module('app.authentication')
        .constant('AUTH_EVENTS', {
            notAuthenticated: 'auth-not-authenticated'
        });
})();
