/**
 * Created by Daniel Palonek on 2016-09-08.
 */
var AccountServices = angular.module('AccountServices',['ngResource']);

AccountServices.factory('AccountService', [
    '$resource', function ($resource) {
        return $resource('', [], {

            checkIfAccountExist: {
                method: 'GET',
                url: '/account/exist/:username',
                params: {username: "@username"}
            }
        });
    }]);