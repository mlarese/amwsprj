(function () {
    'use strict';

    angular
        .module('app.dashboard')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['AmazonSettingsService', 'Notify', 'DashboardService', '$log'];
    function DashboardController(AmazonSettingsService, Notify, DashboardService, $log) {
        var vm = this;

        vm.health = {};

        vm.marketplacesStatus = {
            'amazon-it' : false,
            'amazon-uk' : false
        }

        DashboardService.getSystemHealth()
            .then(function (data) {
                vm.health = data;
                $log.debug("Health:", vm.health);
            }, function (error) {
                Notify.alert(
                    'Qualcosa è andato storto :(',
                    {status: 'danger'}
                );
            });

        // get current scheduler settings for Amazon UK
        AmazonSettingsService.getSettings('amazon-uk')
            .then(function (data) {
                vm.marketplacesStatus['amazon-uk'] = (data.triggers[0]['triggerState'] == 'NORMAL') ? true : false;
            }, function (error) {
                Notify.alert(
                    'Qualcosa è andato storto :(',
                    {status: 'danger'}
                );
            });

        // get current scheduler settings for Amazon IT
        AmazonSettingsService.getSettings('amazon-it')
            .then(function (data) {
                vm.marketplacesStatus['amazon-it'] = (data.triggers[0]['triggerState'] == 'NORMAL') ? true : false;
            }, function (error) {
                Notify.alert(
                    'Qualcosa è andato storto :(',
                    {status: 'danger'}
                );
            });

        vm.toggleDataFeed = function (marketplace) {
            if (confirm('Sei sicuro di voler cambiare lo stato del data feeed?')) {
                if (vm.marketplacesStatus[marketplace])
                    vm.disableDataFeed(marketplace);
                else
                    vm.enableDataFeed(marketplace);
            } else {
                // Do nothing!
            }

        }

        vm.enableDataFeed = function (marketplace) {
            AmazonSettingsService.enableDataFeed(marketplace)
                .then(function (response) {
                    vm.marketplacesStatus[marketplace] = true;
                    Notify.alert(
                        'Il data feed è in esecuzione',
                        {status: 'success'}
                    )
                }, function (response) {
                    Notify.alert(
                        'Qualcosa è andato storto :(',
                        {status: 'danger'}
                    );
                })
        }

        vm.disableDataFeed = function (marketplace) {
            AmazonSettingsService.disableDataFeed(marketplace)
                .then(function (response) {
                    vm.marketplacesStatus[marketplace] = false;
                    Notify.alert(
                        'Il data feed è in pausa',
                        {status: 'success'}
                    )
                }, function (response) {
                    Notify.alert(
                        'Qualcosa è andato storto :(',
                        {status: 'danger'}
                    );
                })
        }
    }
})();
