(function () {
    'use strict';

    angular
        .module('app.profile')
        .factory('ProfileService', ProfileService);

    ProfileService.$inject['$http', '$q', '$localStorage', 'API_URL', 'LOCAL_STORAGE_KEYS'];

    function ProfileService($http, $q, $localStorage, API_URL, LOCAL_STORAGE_KEYS) {

        function getUserProfile() {
            return $http.get(API_URL + '/user')
                .then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }

                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function getLocalUserProfile() {
            return $localStorage[LOCAL_STORAGE_KEYS.USER];
        }

        function getUserPreferences() {
            return $localStorage[LOCAL_STORAGE_KEYS.PREFERENCES];
        }

        function storeUserProfile(user) {
            return $localStorage[LOCAL_STORAGE_KEYS.USER] = user;
        }

        function initUserPreferences(user) {
            var preferences = {};
            storeUserPreferences(preferences);
            return preferences;
        }

        function storeUserPreferences(preferences) {
            return $localStorage[LOCAL_STORAGE_KEYS.PREFERENCES] = preferences;
        }

        function updatePassword(data) {
            return $http.put(API_URL + '/user/password', data)
                .then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }

                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function updateProfile(data) {
            return $http.put(API_URL + '/user', data)
                .then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }

                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        return {
            getUserProfile: getUserProfile,
            getUserPreferences: getUserPreferences,
            storeUserProfile: storeUserProfile,
            initUserPreferences: initUserPreferences,
            storeUserPreferences: storeUserPreferences,
            updateProfile: updateProfile,
            updatePassword: updatePassword,
            getLocalUserProfile: getLocalUserProfile
        };
    }
})();