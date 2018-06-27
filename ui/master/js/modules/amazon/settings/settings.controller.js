(function () {
    'use strict';

    angular
        .module('app.amazon.settings')
        .controller('AmazonSettingsController', AmazonSettingsController);

    AmazonSettingsController.$inject = ['$log', 'AmazonSettingsService', 'Notify', '$stateParams'];

    function AmazonSettingsController($log, AmazonSettingsService, Notify, $stateParams) {
        var vm = this;

        vm.settingsForm = {};
        var marketplace = $stateParams.marketplace;
        $log.info(marketplace);

        if (marketplace === 'amazon-uk') {
            vm.form = {
                path_to_file: '/gioiellerie/UK/OirDataFeed_EN.xml',
                marketplace_id: 'A1F83G8C2ARO7P',
                ftp_host: '151.80.36.187',
                ftp_port: '21',
                ftp_user: 'devuser',
                ftp_password: 'e2qsfLEj'
            }
        }
        else if (marketplace === 'amazon-it') {
            vm.form = {
                path_to_file: '/gioiellerie/OirFeed_esempio_IT.xml',
                marketplace_id: 'APJ6JRA9NG5V4',
                ftp_host: '151.80.36.187',
                ftp_port: '21',
                ftp_user: 'devuser',
                ftp_password: 'e2qsfLEj'
            };
        }

        vm.executionId = '';

        // init settings form
        AmazonSettingsService.getSettings(marketplace)
            .then(function (data) {
                var settings = data.jobDetail.jobDataMap;
                var schedule = data.triggers[0];
                vm.settingsForm = {
                    "cron_expression": schedule['trigger']['cronExpression'],
                    "ftp_host": settings['ftpHost'],
                    "ftp_port": settings['ftpPort'],
                    "ftp_user": settings['ftpUser'],
                    "ftp_password": settings['ftpPassword'],
                    "path_to_file": settings['pathToFile']
                }
            }, function (error) {
                Notify.alert(
                    'Qualcosa è andato storto :(',
                    {status: 'danger'}
                );
            });

        vm.updateSettings = function (settingsForm) {
            AmazonSettingsService.updateSettings(marketplace, settingsForm)
                .then(function (response) {
                        $log.debug(response);
                        Notify.alert(
                            'Configurazione aggiornata con successo',
                            {status: 'success'}
                        )
                    }, function (response) {
                        Notify.alert(
                            'Qualcosa è andato storto :(',
                            {status: 'danger'}
                        );
                    }
                );
        }

        vm.startDataFeed = function (form) {
            AmazonSettingsService.startDataFeed(form)
                .then(function (response) {
                        vm.executionId = response;
                        Notify.alert(
                            'Data feed inviato con successo',
                            {status: 'success'}
                        )
                    }, function (response) {
                        Notify.alert(
                            'Qualcosa è andato storto :(',
                            {status: 'danger'}
                        );
                    }
                );
        }
    }
})();
