/**
 * Created by Daniel Palonek on 2016-09-05.
 */
var accountControllers = angular.module(
    'AccountControllers', []);

accountControllers.controller('AccountController', ['$scope', '$routeParams', '$location', 'AccountService', 'toaster', function ($scope, $routeParams, $location, AccountService, toaster) {
    $scope.username = "";
    $scope.password = "";
    $scope.firstNam = "";
    $scope.surname = "";
    $scope.phone = "";
    $scope.email = "";
    $scope.isUserNameExisted = false;
    $scope.isUserNameTooong = false;
    $scope.isUsernameInvalidFormat = false;
    $scope.isPasswordTooLong = false;
    $scope.isFirstNameTooLong = false;
    $scope.isFirstNameInvalidFormat = false;
    $scope.isSurnameTooLong = false;
    $scope.isSurnameInvalidFormat = false;
    $scope.isPhoneInvalidFormat = {};
    $scope.firstEmpty = true;
    $scope.secondEmpty = true;
    $scope.thirdEmpty = true;
    $scope.fourthEmpty = true;

    document.body.addEventListener("click", logActivity, false);

    function logActivity(e) {
        var x = e.clientX;
        var y = e.clientY;
        var w = $(document).width();
        var h = $(document).height();
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/activities/log/www.clickmapactivity.com/Register/" + w + "x" + h, false);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify({
            points: [
                x + ";" + y
            ]
        }));
    }

    $scope.checkUsernameValidate = function() {
        AccountService.checkIfAccountExist({username: $scope.username}, function (rsp) {
                $scope.isUserNameExisted = rsp.value;
        });
        $scope.isUserNameTooLong = $scope.username.length > 10;
        $scope.isUsernameInvalidFormat = !(/^[a-zA-Z0-9_]+$/.test($scope.username));
        $scope.firstEmpty = $scope.username.length == 1;
    };

    $scope.checkPasswordValidate = function() {
        $scope.isPasswordTooLong = $scope.password.length > 20;
        $scope.secondEmpty = $scope.password.length == 1;
    };

    $scope.checkFirstNameValidate = function() {
        $scope.isFirstNameTooLong = $scope.firstName.length > 15;
        $scope.isFirstNameInvalidFormat = !(/^[a-zA-Z_]+$/.test($scope.firstName));
        $scope.thirdEmpty = $scope.firstName.length == 1;
    };

    $scope.checkSurnameValidate = function() {
        $scope.isSurnameTooLong = $scope.surname.length > 20;
        $scope.isSurnameInvalidFormat = !(/^[a-zA-Z_]+$/.test($scope.surname));
        $scope.fourthEmpty = $scope.surname.length == 1;
    };

    $scope.checkPhoneValidate = function() {
        $scope.isPhoneInvalidFormat = !(/^(\d{3})-(\d{3})-(\d{3}\d?)$/.test($scope.phone));
    };

    $scope.checkEmailValidate = function() {

    };

    $scope.register = function() {
        var reqBody = {
            username: $scope.username,
            password: $scope.password,
            firstName: $scope.firstName,
            surname: $scope.surname,
            phone: $scope.phone,
            email: $scope.email
        };

        AccountService.register(reqBody, function (rsp) {
            if (rsp.value) {
                toaster.pop({
                    type: 'success',
                    title: 'Account registered successfully',
                    body: 'You can now log in'
                });
                $scope.username = "";
                $scope.password = "";
                $scope.firstName = "";
                $scope.surname = "";
                $scope.phone = "";
                $scope.email = "";

            } else {
                toaster.pop({
                    type: 'warning',
                    title: 'Account not registered',
                    body: 'Error occured please repeat registration'
                });
            }
        });

    }
}]);