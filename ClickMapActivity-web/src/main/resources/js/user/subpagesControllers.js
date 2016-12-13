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
        $scope.dateFrom = moment().subtract(7, 'days').format("YYYY-MM-DD HH:mm:ss");
        $scope.granulation = {};
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
                    axisLabel: 'Date (day)',
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

        function generateData() {
            return stream_layers(1,100+Math.random()*50,.1).map(function(data, i) {
                console.info(data);
                return {
                    key: 'Stream',
                    values: data
                };
            });
        }

        function stream_layers(n, m, o) {
            if (arguments.length < 3) o = 0;
            function bump(a) {
                var x = 1 / (.1 + Math.random()),
                    y = 2 * Math.random() - .5,
                    z = 10 / (.1 + Math.random());
                for (var i = 0; i < m; i++) {
                    var w = (i / m - y) * z;
                    a[i] += x * Math.exp(-w * w);
                }
            }
            return d3.range(n).map(function() {
                var a = [], i;
                for (i = 0; i < m; i++) a[i] = o + o * Math.random();
                for (i = 0; i < 5; i++) bump(a);
                return a.map(stream_index);
            });
        }

        function stream_index(d, i) {
            return {x: i, y: Math.max(0, d)};
        }

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









