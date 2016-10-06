/**
 * Created by Daniel Palonek on 2016-09-16.
 */
var WebsitesServices = angular.module('WebsitesServices', ['ngResource']);

WebsitesServices.factory('WebsitesService', [
    '$resource', function ($resource) {
        return $resource('', [], {
            
           getUserWebsites: {
               method: 'GET',
               url: "/websites/getUserWebsites",
               isArray: true
           },
            
            saveWebsite: {
                params: {websiteUrl: "@websiteUrl"},
                method: 'POST',
                url: '/websites/add/:websiteUrl'
            },

            deleteWebsite: {
                method: 'DELETE',
                params: {id: "@id"},
                url: '/websites/delete/:id'
            }
        });
    }]);