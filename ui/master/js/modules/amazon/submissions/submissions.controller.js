/**=========================================================
 * Module: NGTableCtrl.js
 * Controller for ngTables
 =========================================================*/

(function () {
    'use strict';

    angular
        .module('app.amazon.submissions')
        .controller('AmazonSubmissionsController', AmazonSubmissionsController);

    AmazonSubmissionsController.$inject = ['AmazonSettingsService', '$log', '$scope', 'ngTableParams', '$resource', '$timeout', 'API_URL', 'DEFAULT_POLLER_DELAY', '$stateParams'];
    function AmazonSubmissionsController(AmazonSettingsService, $log, $scope, ngTableParams, $resource, $timeout, API_URL, DEFAULT_POLLER_DELAY, $stateParams) {
        var vm = this;
        vm.API_URL = API_URL;
        var marketplace = $stateParams.marketplace;

        vm.pollerDelay = DEFAULT_POLLER_DELAY;

        var url = $resource(API_URL + '/amazon/' + marketplace + '/submission-reports');

        vm.params = new ngTableParams({
            page: 1,           // show first page
            count: 10
        }, {
            getData: function ($defer, params) {
                url.get(params.url(), function (data) {
                    params.total(data.page.totalElements);
                    $defer.resolve(data._embedded.amazonFeedSubmissions);
                });
            }
        });

        $scope.$on('panel-refresh', function (event, id) {
            $timeout(function () {
                vm.params.reload();
                $scope.$broadcast('removeSpinner', id);
            }, 1000);
        });


        vm.poller = function () {
            vm.params.reload();
            if (vm.pollerDelay >= DEFAULT_POLLER_DELAY) {
                clearInterval(interval);
                interval = setInterval(vm.poller, vm.pollerDelay);
            }
        }
        var interval = setInterval(vm.poller, vm.pollerDelay);
        vm.poller();

        vm.downloadFeedSubmissionResult = function (feedSubmissionId) {
            AmazonSettingsService.downloadFeedSubmissionResult(feedSubmissionId);
        }
    }
})();


