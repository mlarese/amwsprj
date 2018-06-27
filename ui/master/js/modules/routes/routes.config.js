/**=========================================================
 * Module: config.js
 * App routes and resources configuration
 =========================================================*/


(function () {
    'use strict';

    angular
        .module('app.routes')
        .config(routesConfig);

    routesConfig.$inject = ['$stateProvider', '$locationProvider', '$urlRouterProvider', 'RouteHelpersProvider'];
    function routesConfig($stateProvider, $locationProvider, $urlRouterProvider, helper) {

        // Set the following to true to enable the HTML5 Mode
        // You may have to set <base> tag in index and a routing configuration in your server
        $locationProvider.html5Mode(false);

        // defaults to dashboard
        $urlRouterProvider.otherwise('/app/dashboard');

        //
        // Application Routes
        // -----------------------------------
        $stateProvider
            .state('app', {
                url: '/app',
                abstract: true,
                templateUrl: helper.basepath('app.html'),
                resolve: helper.resolveFor('modernizr', 'icons', 'whirl', 'screenfull')
            })
            .state('app.dashboard', {
                url: '/dashboard',
                templateUrl: helper.basepath('dashboard.html'),
                controller: 'DashboardController',
                controllerAs: 'dashboardCtrl'

            })
            .state('app.issues', {
                url: 'https://bitbucket.org/mariskamm/data-feed-release/issues/new',
                external: true
            })
            .state('app.amazon-uk-logs', {
                url: '/amazon/:marketplace/logs',
                templateUrl: helper.basepath('amazon/uk/logs.html'),
                controller: 'AmazonLogsController',
                controllerAs: 'amazonLogsCtrl',
                resolve: helper.resolveFor('ngTable')
            })
            .state('app.amazon-uk-submissions', {
                url: '/amazon/:marketplace/submissions',
                templateUrl: helper.basepath('amazon/uk/submissions.html'),
                controller: 'AmazonSubmissionsController',
                controllerAs: 'amazonSubmissionsCtrl',
                resolve: helper.resolveFor('ngTable')
            })
            .state('app.amazon-uk-filters', {
                url: '/amazon/:marketplace/filters',
                templateUrl: helper.basepath('amazon/uk/filters.html'),
                controller: 'AmazonFiltersController',
                controllerAs: 'amazonFiltersCtrl',
                resolve: angular.extend(helper.resolveFor('ui.select', 'ngTable', 'ngDialog'),{
                    tpl: function() { return { path: helper.basepath('ngdialog-template.html') }; }
                })
            })
            .state('app.amazon-uk-settings', {
                url: '/amazon/:marketplace/settings',
                controller: 'AmazonSettingsController',
                controllerAs: 'amazonSettingsCtrl',
                templateUrl: helper.basepath('amazon/uk/settings.html')
            })
            .state('app.amazon-it-logs', {
                url: '/amazon/:marketplace/logs',
                templateUrl: helper.basepath('amazon/logs.html'),
                controller: 'AmazonLogsController',
                controllerAs: 'amazonLogsCtrl',
                resolve: helper.resolveFor('ngTable')
            })
            .state('app.amazon-it-submissions', {
                url: '/amazon/:marketplace/submissions',
                templateUrl: helper.basepath('amazon/it/submissions.html'),
                controller: 'AmazonSubmissionsController',
                controllerAs: 'amazonSubmissionsCtrl',
                resolve: helper.resolveFor('ngTable')
            })
            .state('app.amazon-it-filters', {
                url: '/amazon/:marketplace/filters',
                templateUrl: helper.basepath('amazon/it/filters.html'),
                controller: 'AmazonFiltersController',
                controllerAs: 'amazonFiltersCtrl',
                resolve: angular.extend(helper.resolveFor('ui.select', 'ngTable', 'ngDialog'),{
                    tpl: function() { return { path: helper.basepath('ngdialog-template.html') }; }
                })
            })
            .state('app.amazon-it-settings', {
                url: '/amazon/:marketplace/settings',
                controller: 'AmazonSettingsController',
                controllerAs: 'amazonSettingsCtrl',
                templateUrl: helper.basepath('amazon/it/settings.html')
            })
            .state('app.profile', {
                url: '/profile',
                templateUrl: helper.basepath('profile.html'),
                controller: 'ProfileController',
                controllerAs: 'profileCtrl',
                resolve: helper.resolveFor('filestyle')
            })
            .state('app.documentation', {
                url: '/documentation',
                templateUrl: helper.basepath('documentation.html'),
                resolve: helper.resolveFor('flatdoc')
            })
            //
            // Single Page Routes
            // -----------------------------------
            .state('page', {
                url: '/page',
                templateUrl: 'app/pages/page.html',
                resolve: helper.resolveFor('modernizr', 'icons'),
                controller: ['$rootScope', function ($rootScope) {
                    $rootScope.app.layout.isBoxed = false;
                }]
            })
            .state('page.login', {
                url: '/login',
                title: 'Login',
                templateUrl: 'app/pages/login.html',
                controller: 'LoginController',
                controllerAs: 'loginCtrl'
            })
            .state('page.register', {
                url: '/register',
                title: 'Register',
                templateUrl: 'app/pages/register.html'
            })
            .state('page.404', {
                url: '/404',
                templateUrl: 'app/pages/404.html'
            })
            .state('page.500', {
                url: '/500',
                templateUrl: 'app/pages/500.html'
            })
            .state('page.maintenance', {
                url: '/maintenance',
                templateUrl: 'app/pages/maintenance.html'
            });
    } // routesConfig

})();

