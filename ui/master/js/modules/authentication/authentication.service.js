(function() {
    'use strict';

    angular
        .module('app.authentication')
        .service('AuthService', function($localStorage, $q, $http, API_URL, LOCAL_STORAGE_KEYS, ProfileService, $log) {
            var isAuthenticated = false;
            var authToken;

            function loadUserCredentials() {
                var token = $localStorage[LOCAL_STORAGE_KEYS.TOKEN];
                if (token) {
                    useCredentials(token);
                }
            }

            function storeUserCredentials(token) {
                $localStorage[LOCAL_STORAGE_KEYS.TOKEN] = token;
                useCredentials(token);
            }

            function useCredentials(token) {
                isAuthenticated = true;
                authToken = token;

                // Set the token as header for your requests!
                $http.defaults.headers.common.Authorization = authToken;
            }

            function destroyUserCredentials() {
                authToken = undefined;
                isAuthenticated = false;
                $http.defaults.headers.common.Authorization = undefined;
                delete $localStorage[LOCAL_STORAGE_KEYS.TOKEN];
                delete $localStorage[LOCAL_STORAGE_KEYS.USER];
            }

            function destroyUserPreferences() {
                delete $localStorage[LOCAL_STORAGE_KEYS.PREFERENCES];
            }

            function login(user) {
                return $q(function(resolve, reject) {
                    $http.post(API_URL + '/user/login', user).then(function(result) {
                        if (result.data.success) {
                            // store credentials
                            storeUserCredentials(result.data.token);

                            // store user info
                            ProfileService.storeUserProfile(result.data.user);

                            // initialize preferences
                            ProfileService.initUserPreferences(result.data.user);

                            resolve(result.data.user);
                        } else {
                            reject(result.data.msg);
                        }
                    });
                });
            };

            function logout() {
                destroyUserCredentials();
                destroyUserPreferences();
            };

            loadUserCredentials();

            return {
                login: login,
                logout: logout,
                isAuthenticated: function() {return isAuthenticated;},
                storeUserCredentials: storeUserCredentials
            };

        })

        .factory('AuthInterceptor', function ($rootScope, $q, AUTH_EVENTS) {
            return {
                responseError: function (response) {
                    $rootScope.$broadcast({
                        401: AUTH_EVENTS.notAuthenticated,
                    }[response.status], response);
                    return $q.reject(response);
                }
            };
        })
})();
