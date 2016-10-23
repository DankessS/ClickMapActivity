/**
 * Created by Daniel Palonek on 2016-10-06.
 */
var SubpagesControllers = angular.module('SubpagesControllers', []);

SubpagesControllers.controller('SubpagesController', ['$scope', '$route','$location', '$routeParams', 'SubpagesService', 'WebsitesService', 'WebsitesServiceRepo',
    function ($scope, $route, $location, $routeParams, SubpagesService, WebsitesService, WebsitesServiceRepo) {
        $scope.shouldShow = true;
        $scope.subpages = {};
        $scope.isSubpageExists = false;
        $scope.websiteUrl = window.location.href.split("/subpages/")[1];
        $scope.isFileLoaded = true;

        SubpagesService.getByWebsiteId(function (subpages) {
            $scope.subpages = subpages;
        });

        if (WebsitesService.getUserWebsites === null || WebsitesService.getUserWebsites === undefined || WebsitesService.getUserWebsites.size === 0) {
            $scope.userWebsites = WebsitesServiceRepo.getUserWebsites();
            WebsitesService.setUserWebsites($scope.userWebsites);
            $scope.website = WebsitesService.getRequestedWebsite($scope.websiteUrl);
        }

        $scope.deleteSubpage = function (name) {
            SubpagesService.deleteSubpage({name: name}, function (rsp) {
                $route.reload();
            });
        };

        $scope.checkIfExists = function () {
            SubpagesService.checkIfExists({name: $scope.subpageName}, function (rsp) {
                $scope.isSubpageExists = rsp.value;
            })
        };

        // $scope.shouldShow = $(document).mouseup(function (e) {
        //     var container = $('#subpage-panel');
        //     if(!container.is(e.target)) {
        //         return true;
        //     }
        // });
        
    }]);