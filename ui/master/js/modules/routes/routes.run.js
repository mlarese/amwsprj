/**=========================================================
 * Module: config.js
 * App routes and resources configuration
 =========================================================*/


(function() {
    'use strict';

    angular
        .module('app.routes')
        // authentication check
        .run(function ($rootScope, $state, $window, AuthService) {
            $rootScope.$on('$stateChangeStart', function (event,next, nextParams, fromState) {
                if (!AuthService.isAuthenticated()) {
                    if (next.name !== 'page.login') {
                        event.preventDefault();
                        $state.go('page.login');
                    }
                }

                else if (next.external) {
                    event.preventDefault();
                    $window.open(next.url, '_blank');
                }
            });
        })
})();

