/**
 * Created by Daniel Palonek on 2016-09-16.
 */
var WebsitesControllers = angular.module('WebsitesControllers', []);

WebsitesControllers.controller('WebsitesController', ['$scope', '$location', '$routeParams', '$route', 'WebsitesService', 'WebsitesServiceRepo',
    function ($scope, $location, $routeParams, $route, WebsitesService, WebsitesServiceRepo) {
    $scope.userWebsites = {};
    $scope.websiteUrl;
    $scope.deleteConfirm;

    WebsitesServiceRepo.getUserWebsites(function (websites) {
        $scope.userWebsites = websites;
        WebsitesService.setUserWebsites(websites);
    });

    $scope.addWebsite = function () {
        WebsitesServiceRepo.saveWebsite({websiteUrl: $scope.websiteUrl}, function () {
            $route.reload();
        });
    };
    
    $scope.deleteWebsite = function (website) {
        WebsitesServiceRepo.deleteWebsite({id: website.id}, function() {
            $route.reload();
        });
    }

    $scope.showSubpages = function(website) {
        WebsitesServiceRepo.saveRequestedWebsite(website, function() {
            $location.path('/subpages/' + website.url);
        });
    }

}]);