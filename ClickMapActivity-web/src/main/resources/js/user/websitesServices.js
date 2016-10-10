/**
 * Created by Daniel Palonek on 2016-09-16.
 */
var WebsitesServices = angular.module('WebsitesServices', ['ngResource']);

WebsitesServices.factory('WebsitesServiceRepo', [
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
            },
            
            saveRequestedWebsite: {
                method: 'PUT',
                params: {id: '@id'},
                url: '/websites/saveRequestedWebsite'
            },

            getRequestedWebsite: {
                method: 'GET',
                url: '/websites/getRequestedWebsite'
            }
        });
    }
]);

WebsitesServices.service('WebsitesService', function () {
    var userWebsites = {};

    this.setUserWebsites = function (newReqWebsites) {
        userWebsites = newReqWebsites;
    };

    this.getUserWebsites = function() {
        return userWebsites;
    }

    this.getRequestedWebsite = function (url) {
        var result = {};
        userWebsites.forEach( function (w) {
            if(w.url === url) {
                result = w;
            }    
        });
        return result;
    };
});