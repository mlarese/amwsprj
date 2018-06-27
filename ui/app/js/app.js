/*!
 * 
 * Angle - Bootstrap Admin App + AngularJS
 * 
 * Version: 3.5.1
 * Author: @themicon_co
 * Website: http://themicon.co
 * License: https://wrapbootstrap.com/help/licenses
 * 
 */

// APP START
// ----------------------------------- 

(function() {
    'use strict';

    angular
        .module('angle', [
            'app.core',
            'app.authentication',
            'app.routes',
            'app.sidebar',
            'app.preloader',
            'app.loadingbar',
            'app.dashboard',
            'app.log',
            'app.settings',
            'app.utils',
            'app.pages',
            'app.flatdoc',
            'app.profile',
            'app.notify',
            'app.panels',
            'app.translate',
            'app.amazon.logs',
            'app.amazon.submissions',
            'app.amazon.filters',
            'app.amazon.settings'
        ]);
})();


(function() {
    'use strict';

    angular
        .module('app.authentication', []);
})();
(function() {
    'use strict';

    angular
        .module('app.dashboard', []);
})();
(function() {
    'use strict';

    angular
        .module('app.core', [
            'ngRoute',
            'ngAnimate',
            'ngStorage',
            'ngCookies',
            'pascalprecht.translate',
            'ui.bootstrap',
            'ui.router',
            'oc.lazyLoad',
            'cfp.loadingBar',
            'ngSanitize',
            'ngResource',
            'ui.utils'
        ]);
})();
(function() {
    'use strict';

    angular
        .module('app.colors', []);
})();
(function() {
    'use strict';

    angular
        .module('app.flatdoc', []);
})();
(function() {
    'use strict';

    angular
        .module('app.lazyload', []);
})();
(function() {
    'use strict';

    angular
        .module('app.loadingbar', []);
})();
(function() {
    'use strict';

    angular
        .module('app.log', []);
})();
(function() {
    'use strict';

    angular
        .module('app.notify', []);
})();
(function() {
    'use strict';

    angular
        .module('app.pages', []);
})();
(function() {
    'use strict';

    angular
        .module('app.panels', []);
})();
(function() {
    'use strict';

    angular
        .module('app.preloader', []);
})();


(function() {
    'use strict';

    angular
        .module('app.profile', []);
})();
(function() {
    'use strict';

    angular
        .module('app.settings', []);
})();
(function() {
    'use strict';

    angular
        .module('app.routes', [
            'app.lazyload'
        ]);
})();
(function() {
    'use strict';

    angular
        .module('app.sidebar', []);
})();
(function() {
    'use strict';

    angular
        .module('app.translate', []);
})();
(function() {
    'use strict';

    angular
        .module('app.utils', [
          'app.colors'
          ]);
})();

(function() {
    'use strict';

    angular
        .module('app.amazon.filters', []);
})();
(function() {
    'use strict';

    angular
        .module('app.amazon.logs', []);
})();
(function() {
    'use strict';

    angular
        .module('app.amazon.settings', []);
})();
(function() {
    'use strict';

    angular
        .module('app.amazon.submissions', []);
})();
(function() {
    'use strict';

    angular
        .module('app.authentication')
        .config(["$httpProvider", function ($httpProvider) {
            $httpProvider.interceptors.push('AuthInterceptor');
        }]);
})();

(function() {
    'use strict';

    angular
        .module('app.authentication')
        .constant('AUTH_EVENTS', {
            notAuthenticated: 'auth-not-authenticated'
        });
})();

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
(function() {
    'use strict';

    angular
        .module('app.authentication')
        .service('AuthService', ["$localStorage", "$q", "$http", "API_URL", "LOCAL_STORAGE_KEYS", "ProfileService", "$log", function($localStorage, $q, $http, API_URL, LOCAL_STORAGE_KEYS, ProfileService, $log) {
            var isAuthenticated = false;
            var authToken;

            function loadUserCredentials() {
                var token = $localStorage[LOCAL_STORAGE_KEYS.TOKEN];
                if (token) {
                    useCredentials(token);
                }
            }

            function storeUserCredentials(token) {
                $localStorage[LOCAL_STORAGE_KEYS.TOKEN] = token;
                useCredentials(token);
            }

            function useCredentials(token) {
                isAuthenticated = true;
                authToken = token;

                // Set the token as header for your requests!
                $http.defaults.headers.common.Authorization = authToken;
            }

            function destroyUserCredentials() {
                authToken = undefined;
                isAuthenticated = false;
                $http.defaults.headers.common.Authorization = undefined;
                delete $localStorage[LOCAL_STORAGE_KEYS.TOKEN];
                delete $localStorage[LOCAL_STORAGE_KEYS.USER];
            }

            function destroyUserPreferences() {
                delete $localStorage[LOCAL_STORAGE_KEYS.PREFERENCES];
            }

            function login(user) {
                return $q(function(resolve, reject) {
                    $http.post(API_URL + '/user/login', user).then(function(result) {
                        if (result.data.success) {
                            // store credentials
                            storeUserCredentials(result.data.token);

                            // store user info
                            ProfileService.storeUserProfile(result.data.user);

                            // initialize preferences
                            ProfileService.initUserPreferences(result.data.user);

                            resolve(result.data.user);
                        } else {
                            reject(result.data.msg);
                        }
                    });
                });
            };

            function logout() {
                destroyUserCredentials();
                destroyUserPreferences();
            };

            loadUserCredentials();

            return {
                login: login,
                logout: logout,
                isAuthenticated: function() {return isAuthenticated;},
                storeUserCredentials: storeUserCredentials
            };

        }])

        .factory('AuthInterceptor', ["$rootScope", "$q", "AUTH_EVENTS", function ($rootScope, $q, AUTH_EVENTS) {
            return {
                responseError: function (response) {
                    $rootScope.$broadcast({
                        401: AUTH_EVENTS.notAuthenticated,
                    }[response.status], response);
                    return $q.reject(response);
                }
            };
        }])
})();

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

(function () {
    'use strict';

    DashboardService.$inject = ["$http", "$q", "API_URL"];
    angular
        .module('app.dashboard')
        .factory('DashboardService', DashboardService);

    DashboardService.$inject['$http', '$q', 'API_URL'];

    function DashboardService($http, $q, API_URL) {

        function getSystemHealth() {
            return $http.get(API_URL + '/health')
                .then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }

                }, function (response) {
                    return response.data;
                });
        }

        return {
            getSystemHealth: getSystemHealth
        };
    }
})();
(function() {
    'use strict';

    angular
        .module('app.core')
        .config(coreConfig);

    coreConfig.$inject = ['$controllerProvider', '$compileProvider', '$filterProvider', '$provide', '$animateProvider', '$logProvider', '$localStorageProvider'];
    function coreConfig($controllerProvider, $compileProvider, $filterProvider, $provide, $animateProvider, $logProvider, $localStorageProvider){

        var core = angular.module('app.core');
        // registering components after bootstrap
        core.controller = $controllerProvider.register;
        core.directive  = $compileProvider.directive;
        core.filter     = $filterProvider.register;
        core.factory    = $provide.factory;
        core.service    = $provide.service;
        core.constant   = $provide.constant;
        core.value      = $provide.value;

        // Disables animation on items with class .ng-no-animation
        $animateProvider.classNameFilter(/^((?!(ng-no-animation)).)*$/);

        // Improve performance disabling debugging features
        // $compileProvider.debugInfoEnabled(false);

        // Enable/disable logger
        $logProvider.debugEnabled(true);

        // Set ngStorage prefix
        $localStorageProvider.setKeyPrefix('OIR_');

    }

})();
/**=========================================================
 * Module: constants.js
 * Define constants to inject across the application
 =========================================================*/

(function () {
    'use strict';
    var yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);

    angular
        .module('app.core')
        .constant('APP_MEDIAQUERY', {
            'desktopLG': 1200,
            'desktop': 992,
            'tablet': 768,
            'mobile': 480
        })
        .constant(
            'API_URL', 'http://147.75.205.77:3033/api/v1'
            //'API_URL', 'http://151.80.36.187:3000/api/v1'
            //'API_URL', 'http://localhost:3000/api/v1'
        )
        .constant(
            'DEFAULT_POLLER_DELAY', 10000
        )
        .constant('LOCAL_STORAGE_KEYS', {
            'TOKEN': 'TOKEN',
            'USER': 'USER',
            'PREFERENCES': 'PREFERENCES'
        })
    ;
})();
(function() {
    'use strict';

    angular
        .module('app.core')
        .run(appRun);

    appRun.$inject = ['$rootScope', '$state', 'AuthService', 'AUTH_EVENTS', '$stateParams',  '$window', '$templateCache', 'Colors'];

    function appRun($rootScope, $state, AuthService, AUTH_EVENTS, $stateParams, $window, $templateCache, Colors) {

        // Set reference to access them from any scope
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.$storage = $window.localStorage;

        // Uncomment this to disable template cache
        /*$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
         if (typeof(toState) !== 'undefined'){
         $templateCache.remove(toState.templateUrl);
         }
         });*/

        // Allows to use branding color with interpolation
        // {{ colorByName('primary') }}
        $rootScope.colorByName = Colors.byName;

        // cancel click event easily
        $rootScope.cancel = function($event) {
            $event.stopPropagation();
        };

        // Logout
        $rootScope.logout = function () {
            AuthService.logout();
            $state.go('page.login');
        };

        $rootScope.$on(AUTH_EVENTS.notAuthenticated, function(event) {
            AuthService.logout();
            $state.go('page.login');
        });

        // Hooks Example
        // -----------------------------------

        // Hook not found
        $rootScope.$on('$stateNotFound',
            function(event, unfoundState/*, fromState, fromParams*/) {
                console.log(unfoundState.to); // "lazy.state"
                console.log(unfoundState.toParams); // {a:1, b:2}
                console.log(unfoundState.options); // {inherit:false} + default options
            });
        // Hook error
        $rootScope.$on('$stateChangeError',
            function(event, toState, toParams, fromState, fromParams, error){
                console.log(error);
            });
        // Hook success
        $rootScope.$on('$stateChangeSuccess',
            function(/*event, toState, toParams, fromState, fromParams*/) {
                // display new view from top
                $window.scrollTo(0, 0);
                // Save the route title
                $rootScope.currTitle = $state.current.title;
            });

        // Load a title dynamically
        $rootScope.currTitle = $state.current.title;
        $rootScope.pageTitle = function() {
            var title = $rootScope.app.name + ' - ' + ($rootScope.currTitle || $rootScope.app.description);
            document.title = title;
            return title;
        };

    }

})();


(function() {
    'use strict';

    angular
        .module('app.colors')
        .constant('APP_COLORS', {
          'primary':                '#5d9cec',
          'success':                '#27c24c',
          'info':                   '#23b7e5',
          'warning':                '#ff902b',
          'danger':                 '#f05050',
          'inverse':                '#131e26',
          'green':                  '#37bc9b',
          'pink':                   '#f532e5',
          'purple':                 '#7266ba',
          'dark':                   '#3a3f51',
          'yellow':                 '#fad732',
          'gray-darker':            '#232735',
          'gray-dark':              '#3a3f51',
          'gray':                   '#dde6e9',
          'gray-light':             '#e4eaec',
          'gray-lighter':           '#edf1f2'
        })
        ;
})();
/**=========================================================
 * Module: colors.js
 * Services to retrieve global colors
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.colors')
        .service('Colors', Colors);

    Colors.$inject = ['APP_COLORS'];
    function Colors(APP_COLORS) {
        this.byName = byName;

        ////////////////

        function byName(name) {
          return (APP_COLORS[name] || '#fff');
        }
    }

})();

/**=========================================================
 * Module: flatdoc.js
 * Creates the flatdoc markup and initializes the plugin
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.flatdoc')
        .directive('flatdoc', flatdoc);

    function flatdoc() {

        var directive = {
            template: '<div role="flatdoc"><div role="flatdoc-menu"></div><div role="flatdoc-content"></div></div>',
            link: link,
            restrict: 'EA'
        };
        return directive;

        function link(scope, element, attrs) {

            Flatdoc.run({
                fetcher: Flatdoc.file(attrs.src)
            });

            var $root = $('html, body');
            var menuLinks;

            var $doc = $(document).on('flatdoc:ready', function() {

                var docMenu = $('[role="flatdoc-menu"]');

                menuLinks = docMenu.find('a').on('click', function(e) {
                    e.preventDefault();
                    e.stopPropagation();

                    var $this = $(this);

                    docMenu.find('a.active').removeClass('active');
                    $this.addClass('active');

                    $root.animate({
                        scrollTop: $(this.getAttribute('href')).offset().top - ($('.topnavbar').height() + 10)
                    }, 800);
                });

            });

            // dettach all events
            scope.$on('$destroy', function() {
                menuLinks && menuLinks.off();
                $doc.off('flatdoc:ready');
            });

        }
    }

})();

(function() {
    'use strict';

    angular
        .module('app.lazyload')
        .config(lazyloadConfig);

    lazyloadConfig.$inject = ['$ocLazyLoadProvider', 'APP_REQUIRES'];
    function lazyloadConfig($ocLazyLoadProvider, APP_REQUIRES){

      // Lazy Load modules configuration
      $ocLazyLoadProvider.config({
        debug: false,
        events: true,
        modules: APP_REQUIRES.modules
      });

    }
})();
(function() {
    'use strict';

    angular
        .module('app.lazyload')
        .constant('APP_REQUIRES', {
          // jQuery based and standalone scripts
          scripts: {
            'modernizr':          ['vendor/modernizr/modernizr.custom.js'],
            'icons':              ['vendor/fontawesome/css/font-awesome.min.css',
                                   'vendor/simple-line-icons/css/simple-line-icons.css'],
            'flatdoc':            ['vendor/flatdoc/flatdoc.js'],
            'filestyle':          ['vendor/bootstrap-filestyle/src/bootstrap-filestyle.js'],
            'whirl':              ['vendor/whirl/dist/whirl.css'],
            'screenfull':         ['vendor/screenfull/dist/screenfull.js']
          },
          // Angular based script (use the right module name)
          modules: [
            {name: 'ngTable',                   files: ['vendor/ng-table/dist/ng-table.min.js',
                'vendor/ng-table/dist/ng-table.min.css']},
            {name: 'ngTableExport',             files: ['vendor/ng-table-export/ng-table-export.js']},
            {name: 'ui.select',                 files: ['vendor/angular-ui-select/dist/select.js',
                'vendor/angular-ui-select/dist/select.css']},
            {name: 'ngDialog',                  files: ['vendor/ngDialog/js/ngDialog.min.js',
                'vendor/ngDialog/css/ngDialog.min.css',
                'vendor/ngDialog/css/ngDialog-theme-default.min.css'] }
          ]
        })
        ;

})();

(function() {
    'use strict';

    angular
        .module('app.loadingbar')
        .config(loadingbarConfig)
        ;
    loadingbarConfig.$inject = ['cfpLoadingBarProvider'];
    function loadingbarConfig(cfpLoadingBarProvider){
      cfpLoadingBarProvider.includeBar = true;
      cfpLoadingBarProvider.includeSpinner = false;
      cfpLoadingBarProvider.latencyThreshold = 500;
      cfpLoadingBarProvider.parentSelector = '.wrapper > section';
    }
})();
(function() {
    'use strict';

    angular
        .module('app.loadingbar')
        .run(loadingbarRun)
        ;
    loadingbarRun.$inject = ['$rootScope', '$timeout', 'cfpLoadingBar'];
    function loadingbarRun($rootScope, $timeout, cfpLoadingBar){

      // Loading bar transition
      // ----------------------------------- 
      var thBar;
      $rootScope.$on('$stateChangeStart', function() {
          if($('.wrapper > section').length) // check if bar container exists
            thBar = $timeout(function() {
              cfpLoadingBar.start();
            }, 0); // sets a latency Threshold
      });
      $rootScope.$on('$stateChangeSuccess', function(event) {
          event.targetScope.$watch('$viewContentLoaded', function () {
            $timeout.cancel(thBar);
            cfpLoadingBar.complete();
          });
      });

    }

})();
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

(function () {
    'use strict';

    LogService.$inject = ["$http", "$q", "API_URL"];
    angular
        .module('app.log')
        .factory('LogService', LogService);

    LogService.$inject['$http', '$q', 'API_URL'];

    function LogService($http, $q, API_URL) {

        function getLog() {
            return $http.get(API_URL + '/log')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return response.data;
                });
        }

        return {
            getLog: getLog
        };
    }
})();
/**=========================================================
 * Module: notify.js
 * Directive for notify plugin
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.notify')
        .directive('notify', notify);

    notify.$inject = ['$window', 'Notify'];
    function notify ($window, Notify) {

        var directive = {
            link: link,
            restrict: 'A',
            scope: {
              options: '=',
              message: '='
            }
        };
        return directive;

        function link(scope, element) {

          element.on('click', function (e) {
            e.preventDefault();
            Notify.alert(scope.message, scope.options);
          });
        }

    }

})();


/**=========================================================
 * Module: notify.js
 * Create a notifications that fade out automatically.
 * Based on Notify addon from UIKit (http://getuikit.com/docs/addons_notify.html)
 =========================================================*/

(function() {
    'use strict';
    angular
        .module('app.notify')
        .service('Notify', Notify);

    Notify.$inject = ['$timeout'];
    function Notify($timeout) {

        this.alert = notifyAlert;

        ////////////////

        function notifyAlert(msg, opts) {
            if ( msg ) {
                $timeout(function(){
                    $.notify(msg, opts || {});
                });
            }
        }
    }

})();

/**
 * Notify Addon definition as jQuery plugin
 * Adapted version to work with Bootstrap classes
 * More information http://getuikit.com/docs/addons_notify.html
 */
(function($){
    'use strict';
    var containers = {},
        messages   = {},
        notify     =  function(options){
            if ($.type(options) === 'string') {
                options = { message: options };
            }
            if (arguments[1]) {
                options = $.extend(options, $.type(arguments[1]) === 'string' ? {status:arguments[1]} : arguments[1]);
            }
            return (new Message(options)).show();
        },
        closeAll  = function(group, instantly){
            var id;
            if(group) {
                for(id in messages) { if(group===messages[id].group) messages[id].close(instantly); }
            } else {
                for(id in messages) { messages[id].close(instantly); }
            }
        };
    var Message = function(options){
        // var $this = this;
        this.options = $.extend({}, Message.defaults, options);
        this.uuid    = 'ID'+(new Date().getTime())+'RAND'+(Math.ceil(Math.random() * 100000));
        this.element = $([
            // @geedmo: alert-dismissable enables bs close icon
            '<div class="uk-notify-message alert-dismissable">',
                '<a class="close">&times;</a>',
                '<div>'+this.options.message+'</div>',
            '</div>'
        ].join('')).data('notifyMessage', this);
        // status
        if (this.options.status) {
            this.element.addClass('alert alert-'+this.options.status);
            this.currentstatus = this.options.status;
        }
        this.group = this.options.group;
        messages[this.uuid] = this;
        if(!containers[this.options.pos]) {
            containers[this.options.pos] = $('<div class="uk-notify uk-notify-'+this.options.pos+'"></div>').appendTo('body').on('click', '.uk-notify-message', function(){
                $(this).data('notifyMessage').close();
            });
        }
    };
    $.extend(Message.prototype, {
        uuid: false,
        element: false,
        timout: false,
        currentstatus: '',
        group: false,
        show: function() {
            if (this.element.is(':visible')) return;
            var $this = this;
            containers[this.options.pos].show().prepend(this.element);
            var marginbottom = parseInt(this.element.css('margin-bottom'), 10);
            this.element.css({'opacity':0, 'margin-top': -1*this.element.outerHeight(), 'margin-bottom':0}).animate({'opacity':1, 'margin-top': 0, 'margin-bottom':marginbottom}, function(){
                if ($this.options.timeout) {
                    var closefn = function(){ $this.close(); };
                    $this.timeout = setTimeout(closefn, $this.options.timeout);
                    $this.element.hover(
                        function() { clearTimeout($this.timeout); },
                        function() { $this.timeout = setTimeout(closefn, $this.options.timeout);  }
                    );
                }
            });
            return this;
        },
        close: function(instantly) {
            var $this    = this,
                finalize = function(){
                    $this.element.remove();
                    if(!containers[$this.options.pos].children().length) {
                        containers[$this.options.pos].hide();
                    }
                    delete messages[$this.uuid];
                };
            if(this.timeout) clearTimeout(this.timeout);
            if(instantly) {
                finalize();
            } else {
                this.element.animate({'opacity':0, 'margin-top': -1* this.element.outerHeight(), 'margin-bottom':0}, function(){
                    finalize();
                });
            }
        },
        content: function(html){
            var container = this.element.find('>div');
            if(!html) {
                return container.html();
            }
            container.html(html);
            return this;
        },
        status: function(status) {
            if(!status) {
                return this.currentstatus;
            }
            this.element.removeClass('alert alert-'+this.currentstatus).addClass('alert alert-'+status);
            this.currentstatus = status;
            return this;
        }
    });
    Message.defaults = {
        message: '',
        status: 'normal',
        timeout: 5000,
        group: null,
        pos: 'top-center'
    };

    $.notify          = notify;
    $.notify.message  = Message;
    $.notify.closeAll = closeAll;

    return notify;
}(jQuery));

/**=========================================================
 * Module: access-login.js
 * Demo for login api
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.pages')
        .controller('LoginFormController', LoginFormController);

    LoginFormController.$inject = ['$http', '$state'];
    function LoginFormController($http, $state) {
        var vm = this;

        activate();

        ////////////////

        function activate() {
          // bind here all data from the form
          vm.account = {};
          // place the message if something goes wrong
          vm.authMsg = '';

          vm.login = function() {
            vm.authMsg = '';

            if(vm.loginForm.$valid) {

              $http
                .post('api/account/login', {email: vm.account.email, password: vm.account.password})
                .then(function(response) {
                  // assumes if ok, response is an object with some data, if not, a string with error
                  // customize according to your api
                  if ( !response.account ) {
                    vm.authMsg = 'Incorrect credentials.';
                  }else{
                    $state.go('app.dashboard');
                  }
                }, function() {
                  vm.authMsg = 'Server Request Error';
                });
            }
            else {
              // set as dirty if the user click directly to login so we show the validation messages
              /*jshint -W106*/
              vm.loginForm.account_email.$dirty = true;
              vm.loginForm.account_password.$dirty = true;
            }
          };
        }
    }
})();

/**=========================================================
 * Module: access-register.js
 * Demo for register account api
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.pages')
        .controller('RegisterFormController', RegisterFormController);

    RegisterFormController.$inject = ['$http', '$state'];
    function RegisterFormController($http, $state) {
        var vm = this;

        activate();

        ////////////////

        function activate() {
          // bind here all data from the form
          vm.account = {};
          // place the message if something goes wrong
          vm.authMsg = '';
            
          vm.register = function() {
            vm.authMsg = '';

            if(vm.registerForm.$valid) {

              $http
                .post('api/account/register', {email: vm.account.email, password: vm.account.password})
                .then(function(response) {
                  // assumes if ok, response is an object with some data, if not, a string with error
                  // customize according to your api
                  if ( !response.account ) {
                    vm.authMsg = response;
                  }else{
                    $state.go('app.dashboard');
                  }
                }, function() {
                  vm.authMsg = 'Server Request Error';
                });
            }
            else {
              // set as dirty if the user click directly to login so we show the validation messages
              /*jshint -W106*/
              vm.registerForm.account_email.$dirty = true;
              vm.registerForm.account_password.$dirty = true;
              vm.registerForm.account_agreed.$dirty = true;
              
            }
          };
        }
    }
})();

/**=========================================================
 * Collapse panels * [panel-collapse]
 =========================================================*/
(function() {
    'use strict';

    angular
        .module('app.panels')
        .directive('panelCollapse', panelCollapse);

    function panelCollapse () {
        var directive = {
            controller: Controller,
            restrict: 'A',
            scope: false
        };
        return directive;
    }

    Controller.$inject = ['$scope', '$element', '$timeout', '$localStorage'];
    function Controller ($scope, $element, $timeout, $localStorage) {
      var storageKeyName = 'panelState';

      // Prepare the panel to be collapsible
      var $elem   = $($element),
          parent  = $elem.closest('.panel'), // find the first parent panel
          panelId = parent.attr('id');

      // Load the saved state if exists
      var currentState = loadPanelState( panelId );
      if ( typeof currentState !== 'undefined') {
        $timeout(function(){
            $scope[panelId] = currentState; },
          10);
      }

      // bind events to switch icons
      $element.bind('click', function(e) {
        e.preventDefault();
        savePanelState( panelId, !$scope[panelId] );

      });
  
      // Controller helpers
      function savePanelState(id, state) {
        if(!id) return false;
        var data = angular.fromJson($localStorage[storageKeyName]);
        if(!data) { data = {}; }
        data[id] = state;
        $localStorage[storageKeyName] = angular.toJson(data);
      }
      function loadPanelState(id) {
        if(!id) return false;
        var data = angular.fromJson($localStorage[storageKeyName]);
        if(data) {
          return data[id];
        }
      }
    }

})();

/**=========================================================
 * Dismiss panels * [panel-dismiss]
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.panels')
        .directive('panelDismiss', panelDismiss);

    function panelDismiss () {

        var directive = {
            controller: Controller,
            restrict: 'A'
        };
        return directive;

    }

    Controller.$inject = ['$scope', '$element', '$q', 'Utils'];
    function Controller ($scope, $element, $q, Utils) {
      var removeEvent   = 'panel-remove',
          removedEvent  = 'panel-removed';

      $element.on('click', function (e) {
        e.preventDefault();

        // find the first parent panel
        var parent = $(this).closest('.panel');

        removeElement();

        function removeElement() {
          var deferred = $q.defer();
          var promise = deferred.promise;
          
          // Communicate event destroying panel
          $scope.$emit(removeEvent, parent.attr('id'), deferred);
          promise.then(destroyMiddleware);
        }

        // Run the animation before destroy the panel
        function destroyMiddleware() {
          if(Utils.support.animation) {
            parent.animo({animation: 'bounceOut'}, destroyPanel);
          }
          else destroyPanel();
        }

        function destroyPanel() {

          var col = parent.parent();
          parent.remove();
          // remove the parent if it is a row and is empty and not a sortable (portlet)
          col
            .filter(function() {
            var el = $(this);
            return (el.is('[class*="col-"]:not(.sortable)') && el.children('*').length === 0);
          }).remove();

          // Communicate event destroyed panel
          $scope.$emit(removedEvent, parent.attr('id'));

        }

      });
    }
})();



/**=========================================================
 * Refresh panels
 * [panel-refresh] * [data-spinner="standard"]
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.panels')
        .directive('panelRefresh', panelRefresh);

    function panelRefresh () {
        var directive = {
            controller: Controller,
            restrict: 'A',
            scope: false
        };
        return directive;

    }

    Controller.$inject = ['$scope', '$element'];
    function Controller ($scope, $element) {
      var refreshEvent   = 'panel-refresh',
          whirlClass     = 'whirl',
          defaultSpinner = 'standard';

      // catch clicks to toggle panel refresh
      $element.on('click', function (e) {
        e.preventDefault();

        var $this   = $(this),
            panel   = $this.parents('.panel').eq(0),
            spinner = $this.data('spinner') || defaultSpinner
            ;

        // start showing the spinner
        panel.addClass(whirlClass + ' ' + spinner);

        // Emit event when refresh clicked
        $scope.$emit(refreshEvent, panel.attr('id'));

      });

      // listen to remove spinner
      $scope.$on('removeSpinner', removeSpinner);

      // method to clear the spinner when done
      function removeSpinner (ev, id) {
        if (!id) return;
        var newid = id.charAt(0) === '#' ? id : ('#'+id);
        angular
          .element(newid)
          .removeClass(whirlClass);
      }
    }
})();



/**=========================================================
 * Module panel-tools.js
 * Directive tools to control panels.
 * Allows collapse, refresh and dismiss (remove)
 * Saves panel state in browser storage
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.panels')
        .directive('paneltool', paneltool);

    paneltool.$inject = ['$compile', '$timeout'];
    function paneltool ($compile, $timeout) {
        var directive = {
            link: link,
            restrict: 'E',
            scope: false
        };
        return directive;

        function link(scope, element, attrs) {

          var templates = {
            /* jshint multistr: true */
            collapse:'<a href="#" panel-collapse="" uib-tooltip="Collapse Panel" ng-click="{{panelId}} = !{{panelId}}"> \
                        <em ng-show="{{panelId}}" class="fa fa-plus ng-no-animation"></em> \
                        <em ng-show="!{{panelId}}" class="fa fa-minus ng-no-animation"></em> \
                      </a>',
            dismiss: '<a href="#" panel-dismiss="" uib-tooltip="Chiudi pannello">\
                       <em class="fa fa-times"></em>\
                     </a>',
            refresh: '<a href="#" panel-refresh="" data-spinner="{{spinner}}" uib-tooltip="Aggiorna dati">\
                       <em class="fa fa-refresh"></em>\
                     </a>'
          };

          var tools = scope.panelTools || attrs;

          $timeout(function() {
            element.html(getTemplate(element, tools )).show();
            $compile(element.contents())(scope);

            element.addClass('pull-right');
          });

          function getTemplate( elem, attrs ){
            var temp = '';
            attrs = attrs || {};
            if(attrs.toolCollapse)
              temp += templates.collapse.replace(/{{panelId}}/g, (elem.parent().parent().attr('id')) );
            if(attrs.toolDismiss)
              temp += templates.dismiss;
            if(attrs.toolRefresh)
              temp += templates.refresh.replace(/{{spinner}}/g, attrs.toolRefresh);
            return temp;
          }
        }// link
    }

})();

(function() {
    'use strict';

    angular
        .module('app.preloader')
        .directive('preloader', preloader);

    preloader.$inject = ['$animate', '$timeout', '$q'];
    function preloader ($animate, $timeout, $q) {

        var directive = {
            restrict: 'EAC',
            template: 
              '<div class="preloader-progress">' +
                  '<div class="preloader-progress-bar" ' +
                       'ng-style="{width: loadCounter + \'%\'}"></div>' +
              '</div>'
            ,
            link: link
        };
        return directive;

        ///////

        function link(scope, el) {

          scope.loadCounter = 0;

          var counter  = 0,
              timeout;

          // disables scrollbar
          angular.element('body').css('overflow', 'hidden');
          // ensure class is present for styling
          el.addClass('preloader');

          appReady().then(endCounter);

          timeout = $timeout(startCounter);

          ///////

          function startCounter() {

            var remaining = 100 - counter;
            counter = counter + (0.015 * Math.pow(1 - Math.sqrt(remaining), 2));

            scope.loadCounter = parseInt(counter, 10);

            timeout = $timeout(startCounter, 20);
          }

          function endCounter() {

            $timeout.cancel(timeout);

            scope.loadCounter = 100;

            $timeout(function(){
              // animate preloader hiding
              $animate.addClass(el, 'preloader-hidden');
              // retore scrollbar
              angular.element('body').css('overflow', '');
            }, 300);
          }

          function appReady() {
            var deferred = $q.defer();
            var viewsLoaded = 0;
            // if this doesn't sync with the real app ready
            // a custom event must be used instead
            var off = scope.$on('$viewContentLoaded', function () {
              viewsLoaded ++;
              // we know there are at least two views to be loaded 
              // before the app is ready (1-index.html 2-app*.html)
              if ( viewsLoaded === 2) {
                // with resolve this fires only once
                $timeout(function(){
                  deferred.resolve();
                }, 3000);

                off();
              }

            });

            return deferred.promise;
          }

        } //link
    }

})();
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
(function () {
    'use strict';

    ProfileService.$inject = ["$http", "$q", "$localStorage", "API_URL", "LOCAL_STORAGE_KEYS"];
    angular
        .module('app.profile')
        .factory('ProfileService', ProfileService);

    ProfileService.$inject['$http', '$q', '$localStorage', 'API_URL', 'LOCAL_STORAGE_KEYS'];

    function ProfileService($http, $q, $localStorage, API_URL, LOCAL_STORAGE_KEYS) {

        function getUserProfile() {
            return $http.get(API_URL + '/user')
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

        function getLocalUserProfile() {
            return $localStorage[LOCAL_STORAGE_KEYS.USER];
        }

        function getUserPreferences() {
            return $localStorage[LOCAL_STORAGE_KEYS.PREFERENCES];
        }

        function storeUserProfile(user) {
            return $localStorage[LOCAL_STORAGE_KEYS.USER] = user;
        }

        function initUserPreferences(user) {
            var preferences = {};
            storeUserPreferences(preferences);
            return preferences;
        }

        function storeUserPreferences(preferences) {
            return $localStorage[LOCAL_STORAGE_KEYS.PREFERENCES] = preferences;
        }

        function updatePassword(data) {
            return $http.put(API_URL + '/user/password', data)
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

        function updateProfile(data) {
            return $http.put(API_URL + '/user', data)
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
            getUserProfile: getUserProfile,
            getUserPreferences: getUserPreferences,
            storeUserProfile: storeUserProfile,
            initUserPreferences: initUserPreferences,
            storeUserPreferences: storeUserPreferences,
            updateProfile: updateProfile,
            updatePassword: updatePassword,
            getLocalUserProfile: getLocalUserProfile
        };
    }
})();
(function () {
    'use strict';

    angular
        .module('app.settings')
        .run(settingsRun);

    settingsRun.$inject = ['$rootScope', 'ProfileService'];

    function settingsRun($rootScope, ProfileService) {

        // Hides/show user avatar on sidebar from any element
        $rootScope.toggleUserBlock = function () {
            $rootScope.$broadcast('toggleUserBlock');
        };

        // Set user
        $rootScope.user = ProfileService.getLocalUserProfile();

        // Global Settings
        // -----------------------------------
        $rootScope.app = {
            name: 'OIR',
            description: 'Data Feed Dashboard',
            year: ((new Date()).getFullYear()),
            layout: {
                isFixed: true,
                isCollapsed: false,
                isBoxed: false,
                isRTL: false,
                horizontal: false,
                isFloat: false,
                asideHover: false,
                theme: false,
                asideScrollbar: false,
                isCollapsedText: false
            },
            useFullLayout: false,
            hiddenFooter: false,
            offsidebarOpen: false,
            asideToggled: false,
            viewAnimation: 'ng-fadeInUp'
        };

        // Setup the layout mode
        $rootScope.app.layout.horizontal = ( $rootScope.$stateParams.layout === 'app-h');

        // Close submenu when sidebar change from collapsed to normal
        $rootScope.$watch('app.layout.isCollapsed', function (newValue) {
            if (newValue === false)
                $rootScope.$broadcast('closeSidebarMenu');
        });

    }

})();
/**=========================================================
 * Module: helpers.js
 * Provides helper functions for routes definition
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.routes')
        .provider('RouteHelpers', RouteHelpersProvider)
        ;

    RouteHelpersProvider.$inject = ['APP_REQUIRES'];
    function RouteHelpersProvider(APP_REQUIRES) {

      /* jshint validthis:true */
      return {
        // provider access level
        basepath: basepath,
        resolveFor: resolveFor,
        // controller access level
        $get: function() {
          return {
            basepath: basepath,
            resolveFor: resolveFor
          };
        }
      };

      // Set here the base of the relative path
      // for all app views
      function basepath(uri) {
        return 'app/views/' + uri;
      }

      // Generates a resolve object by passing script names
      // previously configured in constant.APP_REQUIRES
      function resolveFor() {
        var _args = arguments;
        return {
          deps: ['$ocLazyLoad','$q', function ($ocLL, $q) {
            // Creates a promise chain for each argument
            var promise = $q.when(1); // empty promise
            for(var i=0, len=_args.length; i < len; i ++){
              promise = andThen(_args[i]);
            }
            return promise;

            // creates promise to chain dynamically
            function andThen(_arg) {
              // also support a function that returns a promise
              if(typeof _arg === 'function')
                  return promise.then(_arg);
              else
                  return promise.then(function() {
                    // if is a module, pass the name. If not, pass the array
                    var whatToLoad = getRequired(_arg);
                    // simple error check
                    if(!whatToLoad) return $.error('Route resolve: Bad resource name [' + _arg + ']');
                    // finally, return a promise
                    return $ocLL.load( whatToLoad );
                  });
            }
            // check and returns required data
            // analyze module items with the form [name: '', files: []]
            // and also simple array of script files (for not angular js)
            function getRequired(name) {
              if (APP_REQUIRES.modules)
                  for(var m in APP_REQUIRES.modules)
                      if(APP_REQUIRES.modules[m].name && APP_REQUIRES.modules[m].name === name)
                          return APP_REQUIRES.modules[m];
              return APP_REQUIRES.scripts && APP_REQUIRES.scripts[name];
            }

          }]};
      } // resolveFor

    }


})();


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


/**=========================================================
 * Module: config.js
 * App routes and resources configuration
 =========================================================*/


(function() {
    'use strict';

    angular
        .module('app.routes')
        // authentication check
        .run(["$rootScope", "$state", "$window", "AuthService", function ($rootScope, $state, $window, AuthService) {
            $rootScope.$on('$stateChangeStart', function (event,next, nextParams, fromState) {
                if (!AuthService.isAuthenticated()) {
                    if (next.name !== 'page.login') {
                        event.preventDefault();
                        $state.go('page.login');
                    }
                }

                else if (next.external) {
                    event.preventDefault();
                    $window.open(next.url, '_blank');
                }
            });
        }])
})();


/**=========================================================
 * Module: sidebar-menu.js
 * Handle sidebar collapsible elements
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.sidebar')
        .controller('SidebarController', SidebarController);

    SidebarController.$inject = ['$rootScope', '$scope', '$state', 'SidebarLoader', 'Utils'];
    function SidebarController($rootScope, $scope, $state, SidebarLoader,  Utils) {

        activate();

        ////////////////

        function activate() {
          var collapseList = [];

          // demo: when switch from collapse to hover, close all items
          var watchOff1 = $rootScope.$watch('app.layout.asideHover', function(oldVal, newVal){
            if ( newVal === false && oldVal === true) {
              closeAllBut(-1);
            }
          });


          // Load menu from json file
          // -----------------------------------

          SidebarLoader.getMenu(sidebarReady);

          function sidebarReady(items) {
            $scope.menuItems = items;
          }

          // Handle sidebar and collapse items
          // ----------------------------------

          $scope.getMenuItemPropClasses = function(item) {
            return (item.heading ? 'nav-heading' : '') +
              //(isActive(item) ? ' active' : '');
                (isActive(item) ? '' : '');
          };

          $scope.addCollapse = function($index, item) {
            collapseList[$index] = $rootScope.app.layout.asideHover ? true : !isActive(item);
          };

          $scope.isCollapse = function($index) {
            return (collapseList[$index]);
          };

          $scope.toggleCollapse = function($index, isParentItem) {

            // collapsed sidebar doesn't toggle drodopwn
            if( Utils.isSidebarCollapsed() || $rootScope.app.layout.asideHover ) return true;

            // make sure the item index exists
            if( angular.isDefined( collapseList[$index] ) ) {
              if ( ! $scope.lastEventFromChild ) {
                collapseList[$index] = !collapseList[$index];
                closeAllBut($index);
              }
            }
            else if ( isParentItem ) {
              closeAllBut(-1);
            }

            $scope.lastEventFromChild = isChild($index);

            return true;

          };

          // Controller helpers
          // -----------------------------------

            // Check item and children active state
            function isActive(item) {

              if(!item) return;

              if( !item.sref || item.sref === '#') {
                var foundActive = false;
                angular.forEach(item.submenu, function(value) {
                  if(isActive(value)) foundActive = true;
                });
                return foundActive;
              }
              else
                return $state.is(item.sref) || $state.includes(item.sref);
            }

            function closeAllBut(index) {
              index += '';
              for(var i in collapseList) {
                if(index < 0 || index.indexOf(i) < 0)
                  collapseList[i] = true;
              }
            }

            function isChild($index) {
              /*jshint -W018*/
              return (typeof $index === 'string') && !($index.indexOf('-') < 0);
            }

            $scope.$on('$destroy', function() {
                watchOff1();
            });

        } // activate
    }

})();

/**=========================================================
 * Module: sidebar.js
 * Wraps the sidebar and handles collapsed state
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.sidebar')
        .directive('sidebar', sidebar);

    sidebar.$inject = ['$rootScope', '$timeout', '$window', 'Utils'];
    function sidebar ($rootScope, $timeout, $window, Utils) {
        var $win = angular.element($window);
        var directive = {
            // bindToController: true,
            // controller: Controller,
            // controllerAs: 'vm',
            link: link,
            restrict: 'EA',
            template: '<nav class="sidebar" ng-transclude></nav>',
            transclude: true,
            replace: true
            // scope: {}
        };
        return directive;

        function link(scope, element, attrs) {

          var currentState = $rootScope.$state.current.name;
          var $sidebar = element;

          var eventName = Utils.isTouch() ? 'click' : 'mouseenter' ;
          var subNav = $();

          $sidebar.on( eventName, '.nav > li', function() {

            if( Utils.isSidebarCollapsed() || $rootScope.app.layout.asideHover ) {

              subNav.trigger('mouseleave');
              subNav = toggleMenuItem( $(this), $sidebar);

              // Used to detect click and touch events outside the sidebar
              sidebarAddBackdrop();

            }

          });

          var eventOff1 = scope.$on('closeSidebarMenu', function() {
            removeFloatingNav();
          });

          // Normalize state when resize to mobile
          $win.on('resize.sidebar', function() {
            if( ! Utils.isMobile() )
          	asideToggleOff();
          });

          // Adjustment on route changes
          var eventOff2 = $rootScope.$on('$stateChangeStart', function(event, toState) {
            currentState = toState.name;
            // Hide sidebar automatically on mobile
            asideToggleOff();

            $rootScope.$broadcast('closeSidebarMenu');
          });

      	  // Autoclose when click outside the sidebar
          if ( angular.isDefined(attrs.sidebarAnyclickClose) ) {

            var wrapper = $('.wrapper');
            var sbclickEvent = 'click.sidebar';

            var watchOff1 = $rootScope.$watch('app.asideToggled', watchExternalClicks);

          }

          //////

          function watchExternalClicks(newVal) {
            // if sidebar becomes visible
            if ( newVal === true ) {
              $timeout(function(){ // render after current digest cycle
                wrapper.on(sbclickEvent, function(e){
                  // if not child of sidebar
                  if( ! $(e.target).parents('.aside').length ) {
                    asideToggleOff();
                  }
                });
              });
            }
            else {
              // dettach event
              wrapper.off(sbclickEvent);
            }
          }

          function asideToggleOff() {
            $rootScope.app.asideToggled = false;
            if(!scope.$$phase) scope.$apply(); // anti-pattern but sometimes necessary
      	  }

          scope.$on('$destroy', function() {
            // detach scope events
            eventOff1();
            eventOff2();
            watchOff1();
            // detach dom events
            $sidebar.off(eventName);
            $win.off('resize.sidebar');
            wrapper.off(sbclickEvent);
          });

        }

        ///////

        function sidebarAddBackdrop() {
          var $backdrop = $('<div/>', { 'class': 'dropdown-backdrop'} );
          $backdrop.insertAfter('.aside-inner').on('click mouseenter', function () {
            removeFloatingNav();
          });
        }

        // Open the collapse sidebar submenu items when on touch devices
        // - desktop only opens on hover
        function toggleTouchItem($element){
          $element
            .siblings('li')
            .removeClass('open')
            .end()
            .toggleClass('open');
        }

        // Handles hover to open items under collapsed menu
        // -----------------------------------
        function toggleMenuItem($listItem, $sidebar) {

          removeFloatingNav();

          var ul = $listItem.children('ul');

          if( !ul.length ) return $();
          if( $listItem.hasClass('open') ) {
            toggleTouchItem($listItem);
            return $();
          }

          var $aside = $('.aside');
          var $asideInner = $('.aside-inner'); // for top offset calculation
          // float aside uses extra padding on aside
          var mar = parseInt( $asideInner.css('padding-top'), 0) + parseInt( $aside.css('padding-top'), 0);
          var subNav = ul.clone().appendTo( $aside );

          toggleTouchItem($listItem);

          var itemTop = ($listItem.position().top + mar) - $sidebar.scrollTop();
          var vwHeight = $win.height();

          subNav
            .addClass('nav-floating')
            .css({
              position: $rootScope.app.layout.isFixed ? 'fixed' : 'absolute',
              top:      itemTop,
              bottom:   (subNav.outerHeight(true) + itemTop > vwHeight) ? 0 : 'auto'
            });

          subNav.on('mouseleave', function() {
            toggleTouchItem($listItem);
            subNav.remove();
          });

          return subNav;
        }

        function removeFloatingNav() {
          $('.dropdown-backdrop').remove();
          $('.sidebar-subnav.nav-floating').remove();
          $('.sidebar li.open').removeClass('open');
        }
    }


})();


    (function() {
    'use strict';

    angular
        .module('app.sidebar')
        .service('SidebarLoader', SidebarLoader);

    SidebarLoader.$inject = ['$http'];
    function SidebarLoader($http) {
        this.getMenu = getMenu;

        ////////////////

        function getMenu(onReady, onError) {
          var menuJson = 'server/sidebar-menu.json',
              menuURL  = menuJson + '?v=' + (new Date().getTime()); // jumps cache
            
          onError = onError || function() { alert('Failure loading menu'); };

          $http
            .get(menuURL)
            .success(onReady)
            .error(onError);
        }
    }
})();
(function() {
    'use strict';

    angular
        .module('app.sidebar')
        .controller('UserBlockController', UserBlockController);

    UserBlockController.$inject = ['$scope'];
    function UserBlockController($scope) {

        activate();

        ////////////////

        function activate() {

          $scope.userBlockVisible = true;

          var detach = $scope.$on('toggleUserBlock', function(/*event, args*/) {

            $scope.userBlockVisible = ! $scope.userBlockVisible;

          });

          $scope.$on('$destroy', detach);
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('app.translate')
        .config(translateConfig);

    translateConfig.$inject = ['$translateProvider'];
    function translateConfig($translateProvider){

      $translateProvider.useStaticFilesLoader({
          prefix : 'app/i18n/',
          suffix : '.json'
      });

      $translateProvider.preferredLanguage('it_IT');
      $translateProvider.useLocalStorage();
      $translateProvider.usePostCompiling(true);
      $translateProvider.useSanitizeValueStrategy('sanitizeParameters');

    }
})();
(function() {
    'use strict';

    angular
        .module('app.translate')
        .run(translateRun)
        ;
    translateRun.$inject = ['$rootScope', '$translate'];
    
    function translateRun($rootScope, $translate){

      // Internationalization
      // ----------------------

      $rootScope.language = {
        // Handles language dropdown
        listIsOpen: false,
        // list of available languages
        available: {
          'en':       'English',
          'it_IT':    'Italiano'
        },
        // display always the current ui language
        init: function () {
          var proposedLanguage = $translate.proposedLanguage() || $translate.use();
          var preferredLanguage = $translate.preferredLanguage(); // we know we have set a preferred one in app.config
          $rootScope.language.selected = $rootScope.language.available[ (proposedLanguage || preferredLanguage) ];
        },
        set: function (localeId) {
          // Set the new idiom
          $translate.use(localeId);
          // save a reference for the current language
          $rootScope.language.selected = $rootScope.language.available[localeId];
          // finally toggle dropdown
          $rootScope.language.listIsOpen = ! $rootScope.language.listIsOpen;
        }
      };

      $rootScope.language.init();

    }
})();
/**=========================================================
 * Module: animate-enabled.js
 * Enable or disables ngAnimate for element with directive
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.utils')
        .directive('animateEnabled', animateEnabled);

    animateEnabled.$inject = ['$animate'];
    function animateEnabled ($animate) {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element, attrs) {
          scope.$watch(function () {
            return scope.$eval(attrs.animateEnabled, scope);
          }, function (newValue) {
            $animate.enabled(!!newValue, element);
          });
        }
    }

})();

/**=========================================================
 * Module: browser.js
 * Browser detection
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.utils')
        .service('Browser', Browser);

    Browser.$inject = ['$window'];
    function Browser($window) {
      return $window.jQBrowser;
    }

})();

/**=========================================================
 * Module: clear-storage.js
 * Removes a key from the browser storage via element click
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.utils')
        .directive('resetKey', resetKey);

    resetKey.$inject = ['$state', '$localStorage'];
    function resetKey ($state, $localStorage) {
        var directive = {
            link: link,
            restrict: 'A',
            scope: {
              resetKey: '@'
            }
        };
        return directive;

        function link(scope, element) {
          element.on('click', function (e) {
              e.preventDefault();

              if(scope.resetKey) {
                delete $localStorage[scope.resetKey];
                $state.go($state.current, {}, {reload: true});
              }
              else {
                $.error('No storage key specified for reset.');
              }
          });
        }
    }

})();

/**=========================================================
 * Module: fullscreen.js
 * Toggle the fullscreen mode on/off
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.utils')
        .directive('toggleFullscreen', toggleFullscreen);

    toggleFullscreen.$inject = ['Browser'];
    function toggleFullscreen (Browser) {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element) {
          // Not supported under IE
          if( Browser.msie ) {
            element.addClass('hide');
          }
          else {
            element.on('click', function (e) {
                e.preventDefault();

                if (screenfull.enabled) {
                  
                  screenfull.toggle();
                  
                  // Switch icon indicator
                  if(screenfull.isFullscreen)
                    $(this).children('em').removeClass('fa-expand').addClass('fa-compress');
                  else
                    $(this).children('em').removeClass('fa-compress').addClass('fa-expand');

                } else {
                  $.error('Fullscreen not enabled');
                }

            });
          }
        }
    }


})();

/**=========================================================
 * Module: load-css.js
 * Request and load into the current page a css file
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.utils')
        .directive('loadCss', loadCss);

    function loadCss () {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element, attrs) {
          element.on('click', function (e) {
              if(element.is('a')) e.preventDefault();
              var uri = attrs.loadCss,
                  link;

              if(uri) {
                link = createLink(uri);
                if ( !link ) {
                  $.error('Error creating stylesheet link element.');
                }
              }
              else {
                $.error('No stylesheet location defined.');
              }

          });
        }
        
        function createLink(uri) {
          var linkId = 'autoloaded-stylesheet',
              oldLink = $('#'+linkId).attr('id', linkId + '-old');

          $('head').append($('<link/>').attr({
            'id':   linkId,
            'rel':  'stylesheet',
            'href': uri
          }));

          if( oldLink.length ) {
            oldLink.remove();
          }

          return $('#'+linkId);
        }
    }

})();

/**=========================================================
 * Module: now.js
 * Provides a simple way to display the current time formatted
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.utils')
        .directive('now', now);

    now.$inject = ['dateFilter', '$interval'];
    function now (dateFilter, $interval) {
        var directive = {
            link: link,
            restrict: 'EA'
        };
        return directive;

        function link(scope, element, attrs) {
          var format = attrs.format;

          function updateTime() {
            var dt = dateFilter(new Date(), format);
            element.text(dt);
          }

          updateTime();
          var intervalPromise = $interval(updateTime, 1000);

          scope.$on('$destroy', function(){
            $interval.cancel(intervalPromise);
          });

        }
    }

})();

/**=========================================================
 * Module: table-checkall.js
 * Tables check all checkbox
 =========================================================*/
(function() {
    'use strict';

    angular
        .module('app.utils')
        .directive('checkAll', checkAll);

    function checkAll () {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element) {
          element.on('change', function() {
            var $this = $(this),
                index= $this.index() + 1,
                checkbox = $this.find('input[type="checkbox"]'),
                table = $this.parents('table');
            // Make sure to affect only the correct checkbox column
            table.find('tbody > tr > td:nth-child('+index+') input[type="checkbox"]')
              .prop('checked', checkbox[0].checked);

          });
        }
    }

})();

/**=========================================================
 * Module: trigger-resize.js
 * Triggers a window resize event from any element
 =========================================================*/
(function() {
    'use strict';

    angular
        .module('app.utils')
        .directive('triggerResize', triggerResize);

    triggerResize.$inject = ['$window', '$timeout'];
    function triggerResize ($window, $timeout) {
        var directive = {
            link: link,
            restrict: 'A'
        };
        return directive;

        function link(scope, element, attributes) {
          element.on('click', function(){
            $timeout(function(){
              // all IE friendly dispatchEvent
              var evt = document.createEvent('UIEvents');
              evt.initUIEvent('resize', true, false, $window, 0);
              $window.dispatchEvent(evt);
              // modern dispatchEvent way
              // $window.dispatchEvent(new Event('resize'));
            }, attributes.triggerResize || 300);
          });
        }
    }

})();

/**=========================================================
 * Module: utils.js
 * Utility library to use across the theme
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('app.utils')
        .service('Utils', Utils);

    Utils.$inject = ['$window', 'APP_MEDIAQUERY'];
    function Utils($window, APP_MEDIAQUERY) {

        var $html = angular.element('html'),
            $win  = angular.element($window),
            $body = angular.element('body');

        return {
          // DETECTION
          support: {
            transition: (function() {
                    var transitionEnd = (function() {

                        var element = document.body || document.documentElement,
                            transEndEventNames = {
                                WebkitTransition: 'webkitTransitionEnd',
                                MozTransition: 'transitionend',
                                OTransition: 'oTransitionEnd otransitionend',
                                transition: 'transitionend'
                            }, name;

                        for (name in transEndEventNames) {
                            if (element.style[name] !== undefined) return transEndEventNames[name];
                        }
                    }());

                    return transitionEnd && { end: transitionEnd };
                })(),
            animation: (function() {

                var animationEnd = (function() {

                    var element = document.body || document.documentElement,
                        animEndEventNames = {
                            WebkitAnimation: 'webkitAnimationEnd',
                            MozAnimation: 'animationend',
                            OAnimation: 'oAnimationEnd oanimationend',
                            animation: 'animationend'
                        }, name;

                    for (name in animEndEventNames) {
                        if (element.style[name] !== undefined) return animEndEventNames[name];
                    }
                }());

                return animationEnd && { end: animationEnd };
            })(),
            requestAnimationFrame: window.requestAnimationFrame ||
                                   window.webkitRequestAnimationFrame ||
                                   window.mozRequestAnimationFrame ||
                                   window.msRequestAnimationFrame ||
                                   window.oRequestAnimationFrame ||
                                   function(callback){ window.setTimeout(callback, 1000/60); },
            /*jshint -W069*/
            touch: (
                ('ontouchstart' in window && navigator.userAgent.toLowerCase().match(/mobile|tablet/)) ||
                (window.DocumentTouch && document instanceof window.DocumentTouch)  ||
                (window.navigator['msPointerEnabled'] && window.navigator['msMaxTouchPoints'] > 0) || //IE 10
                (window.navigator['pointerEnabled'] && window.navigator['maxTouchPoints'] > 0) || //IE >=11
                false
            ),
            mutationobserver: (window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver || null)
          },
          // UTILITIES
          isInView: function(element, options) {
              /*jshint -W106*/
              var $element = $(element);

              if (!$element.is(':visible')) {
                  return false;
              }

              var window_left = $win.scrollLeft(),
                  window_top  = $win.scrollTop(),
                  offset      = $element.offset(),
                  left        = offset.left,
                  top         = offset.top;

              options = $.extend({topoffset:0, leftoffset:0}, options);

              if (top + $element.height() >= window_top && top - options.topoffset <= window_top + $win.height() &&
                  left + $element.width() >= window_left && left - options.leftoffset <= window_left + $win.width()) {
                return true;
              } else {
                return false;
              }
          },

          langdirection: $html.attr('dir') === 'rtl' ? 'right' : 'left',

          isTouch: function () {
            return $html.hasClass('touch');
          },

          isSidebarCollapsed: function () {
            return $body.hasClass('aside-collapsed') || $body.hasClass('aside-collapsed-text');
          },

          isSidebarToggled: function () {
            return $body.hasClass('aside-toggled');
          },

          isMobile: function () {
            return $win.width() < APP_MEDIAQUERY.tablet;
          }

        };
    }
})();

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

(function () {
    'use strict';

    AmazonFiltersService.$inject = ["$q", "$http", "API_URL", "Notify"];
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

(function () {
    'use strict';

    AmazonSettingsService.$inject = ["$http", "$q", "API_URL"];
    angular
        .module('app.amazon.settings')
        .factory('AmazonSettingsService', AmazonSettingsService);

    AmazonSettingsService.$inject['$http', '$q', 'API_URL'];

    function AmazonSettingsService($http, $q, API_URL) {
        function getSettings(marketplace) {
            return $http.get(API_URL + '/schedules/' + marketplace + '/amazon/')
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

        function updateSettings(marketplace, settingsForm) {
            return $http.post(API_URL + '/amazon/' + marketplace + '/schedules/data-feed', settingsForm)
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

        function startDataFeed(form) {
            return $http.post(API_URL + '/amazon/jobs/data-feed', form)
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function submissionReport() {
            return $http.post(API_URL + '/amazon/jobs/submission-report')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function enableDataFeed(marketplace) {
            return $http.post(API_URL + '/schedules/' + marketplace + '/amazon/enable')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function disableDataFeed(marketplace) {
            return $http.post(API_URL + '/schedules/' + marketplace + '/amazon/disable')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        function getLastExecutions(marketplace) {
            return $http.get(API_URL + '/schedules/' + marketplace + '/amazon')
                .then(function (response) {
                    return response.data;
                }, function (response) {
                    return $q.reject(response.data);
                });
        }

        return {
            getSettings: getSettings,
            updateSettings: updateSettings,
            enableDataFeed: enableDataFeed,
            disableDataFeed: disableDataFeed,
            startDataFeed: startDataFeed,
            submissionReport: submissionReport,
            getLastExecutions: getLastExecutions
        };
    }
})();

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


