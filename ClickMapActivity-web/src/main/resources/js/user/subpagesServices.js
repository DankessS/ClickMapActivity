/**
 * Created by Daniel Palonek on 2016-10-06.
 */
var SubpagesServices = angular.module('SubpagesServices', ['ngResource']);

SubpagesServices.factory('SubpagesService', [
    '$resource', function ($resource) {
        return $resource('', [], {
            
        })
    }]);