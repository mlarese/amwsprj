(function () {
    'use strict';

    angular
        .module('app.log')
        .controller('LogController', LogController);

    LogController.$inject = ['Notify', 'LogService', '$log'];
    function LogController(Notify, LogService, $log) {
        var vm = this;

        vm.log = '';

        LogService.getLog()
            .then(function (data) {
                vm.log = data;
            }, function (error) {
                Notify.alert(
                    'Qualcosa è andato storto :(',
                    {status: 'danger'}
                );
            });

        vm.goToBottom = function () {
            var objDiv = document.getElementById("log");
            objDiv.scrollTop = objDiv.scrollHeight;
        }

        vm.goToTop = function () {
            var objDiv = document.getElementById("log");
            objDiv.scrollTop = 0;
        }

    }
})();
