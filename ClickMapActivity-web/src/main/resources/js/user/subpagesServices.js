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

            getSubpageImage: {
                url: '/subpages/images/:name',
                params: {name: "@name"},
                method: 'GET'
            },
            
            deleteSubpage: {
                url: '/subpages/delete/:name',
                params: {name: '@name'},
                method: 'DELETE'
            },

            checkIfExists: {
                url: '/subpages/checkIfExists/:name',
                params: {name: '@name'},
                method: 'GET'
            }

        })
    }]);