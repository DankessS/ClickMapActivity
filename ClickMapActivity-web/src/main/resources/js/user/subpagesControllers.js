/**
 * Created by Daniel Palonek on 2016-10-06.
 */
var SubpagesControllers = angular.module('SubpagesControllers', []);

SubpagesControllers.controller('SubpagesController', ['$scope', '$http', '$route', '$timeout', '$location', '$routeParams', 'SubpagesService', 'WebsitesService', 'WebsitesServiceRepo','toaster',
    function ($scope, $http, $route, $timeout, $location, $routeParams, SubpagesService, WebsitesService, WebsitesServiceRepo, toaster) {
        $scope.shouldShow = true;
        $scope.subpages = {};
        $scope.isSubpageExists = false;
        $scope.isCaptureMode = false;
        $scope.websiteUrl = window.location.href.split("/subpages/")[1];
        $scope.isFileLoaded = false;
        $scope.isFileChoosed = false;
        $scope.imgName = {};
        $scope.displays = {};
        $scope.dateTo = moment().format("YYYY-MM-DD HH:mm:ss");
        $scope.dateFrom = moment().subtract(7, 'days').format("YYYY-MM-DD HH:mm:ss");
        $scope.granulation = {};
        $scope.subpageUrl = "";
        $scope.granulationTypes = ["day","hour"];
        $scope.data = {};

        $scope.img = {};

        $scope.options = {
            chart: {
                type: 'multiBarChart',
                height: 400,
                margin : {
                    top: 20,
                    right: 20,
                    bottom: 45,
                    left: 45
                },
                clipEdge: true,
                //staggerLabels: true,
                duration: 500,
                stacked: true,
                xAxis: {
                    axisLabel: 'Date (hour)',
                    showMaxMin: false
                },
                yAxis: {
                    axisLabel: 'Clicks',
                    axisLabelDistance: -20,
                    tickFormat: function(d){
                        return d3.format('')(d);
                    }
                },
                color: ['#7a43b6']
            }
        };

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

        $('#datetimepicker6').on('dp.change', function(e) {
            $scope.dateFrom = e.date.format(("YYYY-MM-DD HH:mm:ss"))    ;
        });

        $('#datetimepicker7').on('dp.change', function(e) {
            $scope.dateTo = e.date.format(("YYYY-MM-DD HH:mm:ss"))    ;
        });
        
        $scope.getSubpageImage = function (name) {
            $scope.isFileLoaded = false;
            $http({
                method: 'GET',
                url: '/subpages/images/get',
                params: {
                    name: name,
                    dateFrom: $scope.dateFrom,
                    dateTo: $scope.dateTo
                },
                responseType: 'arraybuffer'
            })
                .then(function (response) {
                    $scope.img = _arrayBufferToBase64(response.data);
                    $scope.isFileLoaded = true;
                }, function (response) {
                    console.error('error in getting static img.');
                });
        };

        $scope.getChartData = function (name) {
            $http({
                method: 'GET',
                url: '/subpages/chart',
                params: {
                    dateFrom: $scope.dateFrom,
                    dateTo: $scope.dateTo,
                    gran: $scope.granulation,
                    name: name
                },
                isArray: true
            })
                .then(function (response) {
                    $scope.data = response.data;
                }, function (response) {
                    console.error('error fetchin response with chart data.');
                });
        };

        $scope.setCaptureMode = function () {
            $scope.isCaptureMode = $scope.subpageUrl.length > 0;
        };

        $scope.captureSubpage = function (url) {
            toaster.pop({
                type: 'success',
                title: 'Please wait.',
                body: 'Your subpage will be added in less than minute.'
            });
            SubpagesService.captureSubpage({subpageName: $scope.subpageName , subpageUrl: url}, function (rsp) {
                if(!rsp.value) {
                    toaster.pop({
                        type: 'warning',
                        title: 'Addding subpage failed',
                        body: 'Please try again.'
                    });
                }
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









