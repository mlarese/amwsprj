describe('Authentication Service', function () {

    var API_URL,
        LOCAL_STORAGE_KEYS,
        AuthService,
        $localStorage,
        $httpBackend;

    var user = {
        username: 'userX',
        password: 'passwordX'
    };

    beforeEach(module('angle'));

    beforeEach(inject(function ($injector) {
        AuthService = $injector.get("AuthService");
        LOCAL_STORAGE_KEYS = $injector.get("LOCAL_STORAGE_KEYS");
        $localStorage = $injector.get("$localStorage");
        API_URL = $injector.get("API_URL");
        $httpBackend = $injector.get('$httpBackend');
    }));

    beforeEach(function () {
        $httpBackend.when('GET', 'app/i18n/it_IT.json')
            .respond(200);
    });

    it('should have Authentication service be defined', function () {
        expect(AuthService).toBeDefined();
    });

    it("should not be authenticated upon starting up", function () {
        expect(AuthService.isAuthenticated()).toBeFalsy();
    });

    it('should store user credentials after calling storeUserCredentials', function () {
        var token = 'token';
        AuthService.storeUserCredentials(token);
        var storedToken = $localStorage[LOCAL_STORAGE_KEYS.TOKEN];
        expect(storedToken).toBe(token);
    });

    describe('Login and logout functions', function () {
        beforeEach(function () {
            $httpBackend.when('POST', API_URL + '/user/login')
                .respond(200, {success: true, token: "token", user: user});
        });

        afterEach(function () {
            $httpBackend.verifyNoOutstandingExpectation();
            $httpBackend.verifyNoOutstandingRequest();
        });

        it('should have sent a POST request to the login API', function () {
            AuthService.login(user);
            $httpBackend.expectPOST(API_URL + '/user/login');
            $httpBackend.flush();
        });

        it('should store user profile and credentials to local storage after successful login', function () {
            // check if local storage is empty
            expect($localStorage[LOCAL_STORAGE_KEYS.TOKEN]).toBeUndefined();
            expect($localStorage[LOCAL_STORAGE_KEYS.USER]).toBeUndefined();
            expect($localStorage[LOCAL_STORAGE_KEYS.PREFERENCES]).toBeUndefined();

            // perform login
            AuthService.login(user);
            $httpBackend.flush();

            expect($localStorage[LOCAL_STORAGE_KEYS.TOKEN]).toBeDefined();
            expect($localStorage[LOCAL_STORAGE_KEYS.USER]).toBeDefined();
            expect($localStorage[LOCAL_STORAGE_KEYS.PREFERENCES]).toBeDefined();
        })

        it('should remove user profile and credentials from local storage after logout', function () {
            // perform login
            AuthService.login(user);
            $httpBackend.flush();

            // perform logout
            AuthService.logout();
            expect($localStorage[LOCAL_STORAGE_KEYS.TOKEN]).toBeUndefined();
            expect($localStorage[LOCAL_STORAGE_KEYS.USER]).toBeUndefined();
            expect($localStorage[LOCAL_STORAGE_KEYS.PREFERENCES]).toBeUndefined();
        });
    });
});