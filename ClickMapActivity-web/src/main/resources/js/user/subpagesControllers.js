/**
 * Created by Daniel Palonek on 2016-10-06.
 */
var SubpagesControllers = angular.module('SubpagesControllers', []);

SubpagesControllers.controller('SubpagesController', ['$scope', '$location', '$routeParams', 'SubpagesService', 'WebsitesService', 'WebsitesServiceRepo',
    function ($scope, $location, $routeParams, SubpagesService, WebsitesService, WebsitesServiceRepo) {
        $scope.shouldShow = true;
        $scope.websiteUrl = window.location.href.split("/subpages/")[1];

        if (WebsitesService.getUserWebsites === null || WebsitesService.getUserWebsites === undefined || WebsitesService.getUserWebsites.size === 0) {
            $scope.userWebsites = WebsitesServiceRepo.getUserWebsites();
            WebsitesService.setUserWebsites($scope.userWebsites);
            $scope.website = WebsitesService.getRequestedWebsite($scope.websiteUrl);
        }

        // $scope.shouldShow = $(document).mouseup(function (e) {
        //     var container = $('#subpage-panel');
        //     if(!container.is(e.target)) {
        //         return true;
        //     }
        // });
        
    }]);