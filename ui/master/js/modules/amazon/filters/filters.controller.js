(function () {
    'use strict';

    angular
        .module('app.amazon.filters')
        .controller('AmazonFiltersController', AmazonFiltersController);

    AmazonFiltersController.$inject = ['ngDialog', 'Notify', '$stateParams', 'ngTableParams', '$resource', '$log', 'AmazonFiltersService', 'API_URL'];
    function AmazonFiltersController(ngDialog, Notify, $stateParams, ngTableParams, $resource, $log, AmazonFiltersService, API_URL) {
        var vm = this;

        var marketplace = $stateParams.marketplace
        vm.ruleForm = {
            selectedBrands: [],
            marketplace: marketplace
        };

        var url = $resource(API_URL + '/amazon/' + marketplace + '/rules');

        vm.params = new ngTableParams({
            page: 1,           // show first page
            count: 10
        }, {
            getData: function ($defer, params) {
                url.get(params.url(), function (data) {
                    params.total(data.page.totalElements);
                    $defer.resolve(data._embedded.rules);
                });
            }
        });

        // init brands dropdown
        AmazonFiltersService.getBrands()
            .then(function(data) {
                vm.brands = data._embedded.brands;
                $log.info('Brands initialized.')
            }, function(error) {
                $log.error(error);
            });


        vm.saveRule = function (ruleForm) {
            AmazonFiltersService.addRule(ruleForm)
                .then(function(response) {
                    var brands = [];
                    for (var i = 0, len = ruleForm.selectedBrands.length; i < len; i++) {
                        brands.push(ruleForm.selectedBrands[i]._id);
                    }
                    return AmazonFiltersService.addBrandsToRule(response._id, brands);
                }, function(error) {
                    Notify.alert(
                        'Qualcosa è andato storto :(',
                        {status: 'danger'}
                    );
                })
                .then(function(response) {
                    Notify.alert(
                        'La regola è stata aggiunta con successo',
                        {status: 'success'}
                    )
                    // reload table
                    vm.params.reload();
                    // clean form
                    vm.ruleForm = {
                        selectedBrands: [],
                        marketplace: marketplace
                    };
                }, function(error) {
                    Notify.alert(
                        'Qualcosa è andato storto :(',
                        {status: 'danger'}
                    );
                })
        }

        vm.toggleRule = function (rule) {
            rule.isActive = !rule.isActive;
            AmazonFiltersService.updateRule(rule)
                .then(function(response) {
                    vm.params.reload();
                    Notify.alert(
                        'La regola è stata aggiornata con successo',
                        {status: 'success'}
                    )
                }, function(error) {
                    Notify.alert(
                        'Qualcosa è andato storto :(',
                        {status: 'danger'}
                    );
                });
        }

        vm.deleteFilter = function (ruleId) {
            if (confirm('Sei sicuro di voler eliminare la regola?')) {
                AmazonFiltersService.deleteRule(ruleId)
                    .then(function(response) {
                        vm.params.reload();
                        Notify.alert(
                            'La regola è stata rimossa con successo',
                            {status: 'success'}
                        )
                    }, function(error) {
                        Notify.alert(
                            'Qualcosa è andato storto :(',
                            {status: 'danger'}
                        );
                    });
            } else {
                // Do nothing!
            }
        }

        vm.getRuleBrands = function (ruleId) {
            AmazonFiltersService.getRuleBrands(ruleId)
            .then(function(data) {
                var ruleBrands = '';

                for (var i = 0, len = data._embedded.brands.length; i < len; i++) {
                    ruleBrands += data._embedded.brands[i].name;
                    if (i < len - 1)
                        ruleBrands += ', ';
                }

                ngDialog.open({ template: 'brandsDialog', controller: 'AmazonFiltersController', data: ruleBrands });
            }, function(error) {
                $log.error(error);
            });
        }
    }
})();
