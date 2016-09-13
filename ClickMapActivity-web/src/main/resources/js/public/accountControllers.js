/**
 * Created by Daniel Palonek on 2016-09-05.
 */
var accountControllers = angular.module(
    'AccountControllers', []);

accountControllers.controller('AccountController', ['$scope', '$routeParams', '$location', 'AccountService', function ($scope, $routeParams, $location, AccountService) {
    $scope.checkIfExist = function(username) {
        var rsp = AccountService.checkIfAccountExist({username: username});
        return rsp.value;
    }
}]);