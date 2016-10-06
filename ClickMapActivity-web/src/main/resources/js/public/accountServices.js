/**
 * Created by Daniel Palonek on 2016-09-08.
 */
var AccountServices = angular.module('AccountServices',['ngResource', 'toaster']);

AccountServices.factory('AccountService', [
    '$resource', function ($resource) {
        return $resource('', [], {

            checkIfAccountExist: {
                method: 'GET',
                url: '/account/exist/:username',
                params: {username: "@username"}
            },

            register: {
                method: 'POST',
                url: '/account/register'
            }
        });
    }]);