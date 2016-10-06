/**
 * Created by Daniel Palonek on 2016-09-16.
 */
var WebsitesControllers = angular.module('WebsitesControllers', []);

WebsitesControllers.controller('WebsitesController', ['$scope', '$routeParams', '$route', 'WebsitesService',
    function ($scope, $routeParams, $route, WebsitesService) {
    $scope.userWebsites = {};
    $scope.websiteUrl;
    $scope.deleteConfirm;

    WebsitesService.getUserWebsites(function (websites) {
        $scope.userWebsites = websites;
    });

    $scope.addWebsite = function () {
        WebsitesService.saveWebsite({websiteUrl: $scope.websiteUrl}, function () {
            $route.reload();
        });
    };
    
    $scope.deleteWebsite = function (website) {
        WebsitesService.deleteWebsite({id: website.id}, function() {
            $route.reload();
        });
    }
}]);