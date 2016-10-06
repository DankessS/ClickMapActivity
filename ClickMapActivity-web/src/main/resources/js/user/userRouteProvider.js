/**
 * Created by Daniel Palonek on 2016-09-10.
 */
var mainApp = angular.module("mainApp", ['ngRoute', 'ngAnimate', 'IndexControllers', 'IndexServices',
    'AccountControllers', 'WebsitesControllers', 'WebsitesServices', 'AccountServices', 'ui.grid', 'ui.grid.pagination']);

mainApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
    when('/register', {
        templateUrl: '/public/register.html'
    }).
    when('/main', {
        templateUrl: '/public/main.html',
        controller: 'IndexController'
    }).
    when('/websites', {
        templateUrl: '/user/websites.html',
        controller: 'WebsitesController'
    }).
    otherwise({
        templateUrl: '/public/main.html',
        controller: 'IndexController'
    });
}]);

mainApp.run(['$rootScope', '$location', 'IndexService', function ($rootScope, $location, IndexService) {
    $rootScope.$on('$routeChangeStart', function (event) {
       IndexService.getLoggedUsername(function (username) {
           if (username.value === null) {
               window.location = "/#" + $location.path();
           }
       }) ;
    });
}]);