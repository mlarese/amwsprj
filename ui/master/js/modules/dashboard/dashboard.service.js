(function () {
    'use strict';

    angular
        .module('app.dashboard')
        .factory('DashboardService', DashboardService);

    DashboardService.$inject['$http', '$q', 'API_URL'];

    function DashboardService($http, $q, API_URL) {

        function getSystemHealth() {
            return $http.get(API_URL + '/health')
                .then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }

                }, function (response) {
                    return response.data;
                });
        }

        return {
            getSystemHealth: getSystemHealth
        };
    }
})();