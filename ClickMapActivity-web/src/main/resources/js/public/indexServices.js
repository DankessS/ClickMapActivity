/**
 * Created by Daniel Palonek on 2016-09-05.
 */
var IndexServices = angular.module("IndexServices", ['ngResource']);

IndexServices.factory('IndexService', [
    '$resource', function ($resource) {
        return $resource('', {}, {

            login: {
                method: 'GET',
                url: '/login'
            },
            
            logout: {
                method: 'GET',
                url: '/logout'
            },

            getLoggedUsername: {
                method: 'GET',
                url: '/account/logged/name'
            }
        });
    }]);