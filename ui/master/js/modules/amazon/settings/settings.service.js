(function () {
    'use strict';

    angular
        .module('app.amazon.settings')
        .factory('AmazonSettingsService', AmazonSettingsService);

    AmazonSettingsService.$inject['$http', '$q', 'API_URL'];

    function AmazonSettingsService($http, $q, API_URL) {
        function getSettings(marketplace) {
            return $http.get(API_URL + '/schedules/' + marketplace + '/amazon/')
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

        function updateSettings(marketplace, settingsForm) {
            return $http.post(API_URL + '/amazon/' + marketplace + '/schedules/data-feed', settingsForm)
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

        function startDataFeed(form) {
            return $http.post(API_URL + '/amazon/jobs/data-feed', form)
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function submissionReport() {
            return $http.post(API_URL + '/amazon/jobs/submission-report')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function enableDataFeed(marketplace) {
            return $http.post(API_URL + '/schedules/' + marketplace + '/amazon/enable')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function disableDataFeed(marketplace) {
            return $http.post(API_URL + '/schedules/' + marketplace + '/amazon/disable')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function getLastExecutions(marketplace) {
            return $http.get(API_URL + '/schedules/' + marketplace + '/amazon')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        return {
            getSettings: getSettings,
            updateSettings: updateSettings,
            enableDataFeed: enableDataFeed,
            disableDataFeed: disableDataFeed,
            startDataFeed: startDataFeed,
            submissionReport: submissionReport,
            getLastExecutions: getLastExecutions
        };
    }
})();
