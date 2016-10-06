/**
 * Created by Daniel Palonek on 2016-09-05.
 */
var mainApp = angular.module("mainApp", ['ngRoute', 'ngAnimate', 'ui.grid.pagination','angular-loading-bar', 'IndexServices', 'IndexControllers', 'AccountServices', 'AccountControllers']);

mainApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.

        when('/register', {
            templateUrl: '/public/register.html',
            controller: 'AccountController'
        }).

        when('/main', {
            templateUrl: '/public/main.html',
            controller: 'IndexController'
        }).

        otherwise({
            templateUrl: '/public/main.html',
            controller: 'IndexController'
        });
}]);

mainApp.run(['$rootScope', '$location', 'IndexService', function ($rootScope, $location, IndexService) {
    $rootScope.$on('$routeChangeStart', function (event) {
       IndexService.getLoggedUsername(function (username) {
           if(username.value !== null) {
               window.location = "/user/" + $location.path();
           }
       });
    });
}]);