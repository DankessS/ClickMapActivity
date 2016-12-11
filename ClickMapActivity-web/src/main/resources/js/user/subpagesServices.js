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

            getChartData: {
                url: '/subpages/chart/:dateFrom/:dateTo/:gran',
                params: {dateFrom: "@dateFrom",
                         dateTo: "@dateTo",
                         gran: "@gran"},
                method: 'GET'
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