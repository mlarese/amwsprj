describe('Amazon Service', function () {

    var API_URL,
        $httpBackend,
        AmazonSettingsService;

    var dataFeedId = 'test';
    var settingsForm = {};

    beforeEach(module('angle'));

    beforeEach(inject(function ($injector) {
        AmazonSettingsService = $injector.get("AmazonSettingsService");
        API_URL = $injector.get("API_URL");
        $httpBackend = $injector.get('$httpBackend');
    }));

    beforeEach(function () {
        $httpBackend.when('GET', 'app/i18n/it_IT.json')
            .respond(200);
    });

    it('should have a AmazonSettings service be defined', function () {
        expect(AmazonSettingsService).toBeDefined();
    });

    describe('Service functions', function () {
        beforeEach(function () {
            $httpBackend.when('GET', API_URL + '/data-feeds/' + dataFeedId)
                .respond(200);
        });

        beforeEach(function () {
            $httpBackend.when('PUT', API_URL + '/data-feeds/' + dataFeedId)
                .respond(200);
        });

        afterEach(function () {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });

        it('should have sent a GET request to the data-feed API', function () {
            AmazonSettingsService.getSettings(dataFeedId);
            $httpBackend.expectGET(API_URL + '/data-feeds/' + dataFeedId);
            $httpBackend.flush();
        });

        it('should have sent a PUT request to the data-feed API', function () {
            AmazonSettingsService.updateSettings(dataFeedId, settingsForm);
            $httpBackend.expectPUT(API_URL + '/data-feeds/' + dataFeedId);
            $httpBackend.flush();
        });
    });
});