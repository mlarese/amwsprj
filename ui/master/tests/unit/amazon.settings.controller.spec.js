describe('Amazon Controller', function () {

    beforeEach(module('angle'));

    var $controller,
        $rootScope,
        $scope,
        AmazonSettingsService,
        $state,
        $q,
        $httpBackend;

    beforeEach(inject(function ($injector) {
        AmazonSettingsService = $injector.get("AmazonSettingsService");
        $state = $injector.get("$state");
        $rootScope = $injector.get("$rootScope");
        $q = $injector.get("$q");
        $httpBackend = $injector.get("$httpBackend");
        $controller = $injector.get("$controller");
    }));

    beforeEach(function () {
        $httpBackend.when('GET', 'app/i18n/it_IT.json')
            .respond(200);
    });

    describe('Amazon ES settings', function () {
        var deferred;

        beforeEach(function () {
            deferred = $q.defer();
            spyOn(AmazonSettingsService, "getSettings").and.returnValue(deferred.promise);
            spyOn(AmazonSettingsService, "updateSettings").and.returnValue(deferred.promise);
        });

        beforeEach(function () {
            $scope = $rootScope.$new();
            $controller = $controller('AmazonSettingsController', {
                $scope: $scope
            });
        });

        it('should expose some properties to the view', function () {
            expect(angular.isObject($controller.settingsForm)).toBe(true);
        });

        it('should get the current settings', function () {
            expect(AmazonSettingsService.getSettings).toHaveBeenCalledWith('amazon-es');
            expect(AmazonSettingsService.getSettings.calls.count()).toBe(1);
        });

        it('should save the settings', function () {
            var form = {
                foo: 'bar'
            };

            var dataFeedId = 'test';

            $controller.updateSettings(dataFeedId, form);
            expect(AmazonSettingsService.updateSettings).toHaveBeenCalledWith(dataFeedId, form);
            expect(AmazonSettingsService.updateSettings.calls.count()).toBe(1);
        });
    });
});