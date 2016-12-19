/**
 * Created by Daniel Palonek on 2016-10-06.
 */
var SubpagesServices = angular.module('SubpagesServices', ['ngResource']);

SubpagesServices.factory('SubpagesService', [
    '$resource', function ($resource) {
        return $resource('', [], {

            getByWebsiteId: {
                url: '/subpages/getByWebsiteId',
                method: 'GET',
                isArray: true
            },

            captureSubpage: {
                url: '/subpages/capture/:subpageUrl',
                method: 'POST',
                params: {subpageUrl: "@subpageUrl"}
            },

            getChartData: {
                url: '/subpages/chart',
                params: {dateFrom: "@dateFrom",
                         dateTo: "@dateTo",
                         gran: "@gran",
                         name: "@name"},
                method: 'GET',
                isArray: true
            },

            deleteSubpage: {
                url: '/subpages/delete/:name',
                params: {name: '@name'},
                method: 'DELETE'
            },

            getDisplays: {
                url: '/subpages/displays/:id',
                params: {id: '@id'},
                method: 'GET'
            },
            
            checkIfExists: {
                url: '/subpages/checkIfExists/:name',
                params: {name: '@name'},
                method: 'GET'
            }

        })
    }]);