(function () {
    'use strict';

    angular
        .module('app.authentication')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['AuthService', '$state'];
    function LoginController(AuthService, $state) {
        var vm = this;

        vm.user = {
            username: '',
            password: ''
        };

        // place the message if something goes wrong
        vm.authMsg = '';

        vm.login = function (isFormValid) {
            vm.authMsg = '';
            if (isFormValid) {
                AuthService.login(vm.user).then(function (response) {
                    // go to the Dashboard after login
                    $state.go('app.dashboard');
                }, function (error) {
                    vm.authMsg = 'Username e/o password non corretti';
                });
            }
            else {
                // set as dirty if the user click directly to login so we show the validation messages
                vm.loginForm.account_username.$dirty = true;
                vm.loginForm.account_password.$dirty = true;
            }
        };
    }
})();