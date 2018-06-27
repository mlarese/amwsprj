/**=========================================================
 * Module: NGTableCtrl.js
 * Controller for ngTables
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.amazon.logs')
        .controller('AmazonLogsController', AmazonLogsController);

    AmazonLogsController.$inject = ['$stateParams','$scope', 'ngTableParams', '$resource', '$timeout', 'API_URL', '$log'];
    function AmazonLogsController($stateParams, $scope, ngTableParams, $resource, $timeout, API_URL, $log) {
        var vm = this;
        var marketplace = $stateParams.marketplace;

        var url = $resource(API_URL + '/schedules/' + marketplace + '/amazon');

        vm.params = new ngTableParams({
            page: 1,           // show first page
            count: 10
        }, {
            getData: function ($defer, params) {
                url.get(params.url(), function (data) {
                    params.total(data.lastExecutions.length);
                    $defer.resolve(data.lastExecutions);
                });
            }
        });

        $scope.$on('panel-refresh', function (event, id) {
            $timeout(function () {
                vm.params.reload();
                $scope.$broadcast('removeSpinner', id);
            }, 1000);
        });

    }
})();


