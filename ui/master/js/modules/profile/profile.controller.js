(function () {
    'use strict';

    angular
        .module('app.profile')
        .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['Notify', 'ProfileService', '$log'];
    function ProfileController(Notify, ProfileService, $log) {
        var vm = this;

        vm.user = {};
        vm.profileForm = {}

        ProfileService.getUserProfile()
            .then(function (user) {
                vm.user = user;
                $log.debug("User:", vm.user);

                vm.profileForm = {
                    fullname: vm.user.fullname,
                    email: vm.user.email,
                    language: vm.user.language
                }
            }, function (error) {
                Notify.alert(
                    'Something went wrong',
                    {status: 'danger'}
                );
            });

        vm.updateProfile = function (profileForm) {
            ProfileService.updateProfile(profileForm)
                .then(function (response) {
                        if (response.success) {
                            Notify.alert(
                                response.msg,
                                {status: 'success'}
                            );

                        }
                        else {
                            Notify.alert(
                                response.msg,
                                {status: 'danger'}
                            );
                        }
                    }, function (response) {
                        Notify.alert(
                            response.msg,
                            {status: 'danger'}
                        );
                    }, function () {

                    }
                );
        }

        vm.updatePassword = function () {
            ProfileService.updatePassword(vm.passwords)
                .then(function (response) {
                        if (response.success) {
                            Notify.alert(
                                response.msg,
                                {status: 'success'}
                            );

                        }
                        else {
                            Notify.alert(
                                response.msg,
                                {status: 'danger'}
                            );
                        }
                    }, function (response) {
                        Notify.alert(
                            response.msg,
                            {status: 'danger'}
                        );
                    }, function () {

                    }
                );
            // reset form
            vm.passwords = {};
            vm.updatePasswordForm.$setPristine();
            vm.updatePasswordForm.$setUntouched();
        }
    }
})();