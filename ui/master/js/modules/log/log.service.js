(function () {
    'use strict';

    angular
        .module('app.log')
        .factory('LogService', LogService);

    LogService.$inject['$http', '$q', 'API_URL'];

    function LogService($http, $q, API_URL) {

        function getLog() {
            return $http.get(API_URL + '/log')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return response.data;
                });
        }

        return {
            getLog: getLog
        };
    }
})();