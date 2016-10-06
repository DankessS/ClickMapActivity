/**
 * Created by Daniel Palonek on 2016-09-09.
 */
var indexControllers = angular.module('IndexControllers', []);

indexControllers.controller('IndexController', ['$scope', '$location', 'IndexService', function ($scope, $location, IndexService) {
    $scope.username = '';
    IndexService.getLoggedUsername(function (username) {
       if(username.value !== null) {
           $scope.username = username.value;
       }
    });

    $scope.logout = function() {
        IndexService.logout( function() {
            IndexService.getLoggedUsername(function (username) {
                if(username.value === null) {
                    window.location = "/#" + $location.path();
                }
            });
        });
    };
}]);