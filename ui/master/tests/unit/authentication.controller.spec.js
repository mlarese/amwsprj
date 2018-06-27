describe('Authentication Controller', function () {

    beforeEach(module('angle'));

    var $controller,
        $rootScope,
        $scope,
        AuthService,
        $state,
        $q,
        $httpBackend;
    var deferred = null;

    beforeEach(inject(function ($injector) {
        AuthService = $injector.get("AuthService");
        $state = $injector.get("$state");
        $rootScope = $injector.get("$rootScope");
        $q = $injector.get("$q");
        $httpBackend = $injector.get("$httpBackend");
        $controller = $injector.get("$controller");
        $scope = $rootScope.$new();
    }));

    beforeEach(function () {
        $httpBackend.when('GET', 'app/i18n/it_IT.json')
            .respond(200);
    });

    beforeEach(function () {
        $controller = $controller('LoginController', {
            $scope: $scope
        });
    })

    it('should have an empty user at startup', function () {
        expect($controller.user.username).toBe('');
        expect($controller.user.password).toBe('');
    })

    describe('Login form', function () {

        beforeEach(function () {
            deferred = $q.defer();
            spyOn(AuthService, "login").and.returnValue(deferred.promise);
        });

        it('should call login function and pass the user if the form is valid', function () {
            var isFormValid = true;
            var user = {
                username: 'userX',
                password: 'passwordX'
            };

            $controller.user = user;

            $controller.login(isFormValid);

            expect(AuthService.login).toHaveBeenCalledWith(user);
        })

        it('should place an error message if the login fails', function () {
            var isFormValid = true;

            $controller.login(isFormValid);

            deferred.reject();

            $scope.$digest();

            expect($controller.authMsg).not.toBe('');

        })

        it('should go to dashboard after successfull login', function () {
            var isFormValid = true;

            spyOn($state, "go");

            $controller.login(isFormValid);

            deferred.resolve();

            $scope.$digest();
            expect($state.go).toHaveBeenCalledWith('app.dashboard');
        })
    })
});