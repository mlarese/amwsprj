(function () {
    'use strict';

    angular
        .module('app.amazon.filters')
        .factory('AmazonFiltersService', AmazonFiltersService);

    AmazonFiltersService.$inject['$q', '$http', 'API_URL', 'Notify'];

    function AmazonFiltersService($q, $http, API_URL, Notify) {

        function getBrands() {
            return $http.get(API_URL + '/amazon/brands?count=1000')
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

        function addRule(rule) {
            return $http.post(API_URL + '/amazon/rules', rule)
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

        function updateRule(rule) {
            return $http.put(API_URL + '/amazon/rules/' + rule._id, rule)
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

        function deleteRule(ruleId) {
            console.log(ruleId);
            return $http.delete(API_URL + '/amazon/rules/' + ruleId)
                .then(function (response) {
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function addBrandsToRule(ruleId, brands) {
            return $http.put(API_URL + '/amazon/rules/' + ruleId + '/brands', brands)
                .then(function (response) {
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function getRuleBrands(rulesId) {
            return $http.get(API_URL + '/amazon/rules/' + rulesId + '/brands')
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
            getBrands: getBrands,
            addRule: addRule,
            deleteRule: deleteRule,
            updateRule: updateRule,
            addBrandsToRule: addBrandsToRule,
            getRuleBrands: getRuleBrands
        };

    }
})();
