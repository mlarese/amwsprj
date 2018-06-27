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

