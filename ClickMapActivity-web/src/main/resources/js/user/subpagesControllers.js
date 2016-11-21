/**
 * Created by Daniel Palonek on 2016-10-06.
 */
var SubpagesControllers = angular.module('SubpagesControllers', []);

SubpagesControllers.controller('SubpagesController', ['$scope', '$http', '$route', '$timeout', '$location', '$routeParams', 'SubpagesService', 'WebsitesService', 'WebsitesServiceRepo',
    function ($scope, $http, $route, $timeout, $location, $routeParams, SubpagesService, WebsitesService, WebsitesServiceRepo) {
        $scope.shouldShow = true;
        $scope.subpages = {};
        $scope.isSubpageExists = false;
        $scope.websiteUrl = window.location.href.split("/subpages/")[1];
        $scope.isFileLoaded = false;
        $scope.imgName = {};
        $scope.displays = {};
        $scope.dateTo = moment().format("YYYY-MM-DD HH:mm:ss");
        $scope.dateFrom = moment().subtract(1, 'hours').format("YYYY-MM-DD HH:mm:ss");

        // var d1 = new Date();
        // var d2 = new Date();
        // d2.setHours(d1.getHours() - 1);
        // $scope.dateFrom = d1.toLocaleString();
        // $scope.dateFrom = d2.toLocaleString();

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

        $scope.loadImageName = function () {
            $scope.imgName = window.location.href.split("/subpages/image/")[1];
        };

        $scope.redirectToImage = function (name) {
            $location.path('/subpages/image/' + name);
        };

        $scope.showDate = function (epoch) {
            if (epoch === 0) {
                return "Inactive";
            }
            var date = new Date(epoch * 1000);
            return date.toLocaleString();
        };

        $scope.getDateTo = function () {
            var d = new Date();
            d.setHours(d.getHours() + 1);
            return d.toISOString();
        };

        $scope.getDateFrom = function (hours) {
            var d = new Date();
            d.setHours(d.getHours() + 1 - hours);
            return d.toISOString();
        };

        $scope.getSubpageImage = function (name) {
            $http({
                method: 'GET',
                url: '/subpages/images/name/dateFrom/dateTo',
                params: {name: name,
                    dateFrom: $scope.dateFrom,
                    dateTo: $scope.dateTo},
                responseType: 'arraybuffer'
            })
                .then(function (response) {
                    console.log(response);
                    var str = _arrayBufferToBase64(response.data);
                    console.log(str);
                    // str is base64 encoded.
                }, function (response) {
                    console.error('error in getting static img.');
                });
        };


        function _arrayBufferToBase64(buffer) {
            var binary = '';
            var bytes = new Uint8Array(buffer);
            var len = bytes.byteLength;
            for (var i = 0; i < len; i++) {
                binary += String.fromCharCode(bytes[i]);
            }
            return window.btoa(binary);
        }

    }]);









