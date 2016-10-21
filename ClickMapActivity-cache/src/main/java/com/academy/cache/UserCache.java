package com.academy.cache;

import org.springframework.stereotype.Component;

/**
 * Created by Daniel Palonek on 2016-09-16.
 */
@Component
public class UserCache extends AbstractCacheSupplier {

    public String getLoggedUsername() {
         return (String)getUserContext().getOrDefault(CacheConstants.LOGGED_USERNAME,null);
    }

    public void setLoggedUsername(String username) {
        getUserContext().put(CacheConstants.LOGGED_USERNAME,username);
    }

    public void removeLoggedUsername() {
        getUserContext().remove(CacheConstants.LOGGED_USERNAME);
    }

    public void setUserWebsites(Iterable websites) {
        getMap(CacheConstants.USER_WEBSITES).set(getLoggedUsername(),websites);
    }

    public Iterable getUserWebsites() {
        return (Iterable)getMap(CacheConstants.USER_WEBSITES).get(getLoggedUsername());
    }

    public void removeUserWebsites() {
        getMap(CacheConstants.USER_WEBSITES).remove(getLoggedUsername());
    }

    public void setRequestedWebsite(Object website) {
        getUserContext().put(CacheConstants.REQUESTED_WEBSITE,website);
    }

    public Object getRequestedWebsite() {
        return getUserContext().getOrDefault(CacheConstants.REQUESTED_WEBSITE,null);
    }

    public void setWebsiteSubpages(Long websiteId, Iterable subpages) {
        getMap(CacheConstants.WEBSITE_SUBPAGES).set(websiteId,subpages);
    }

    public Iterable getWebsiteSubpages(Long websiteId) {
        return (Iterable)getMap(CacheConstants.WEBSITE_SUBPAGES).get(websiteId);
    }

    public void deleteWebsiteSubpages(Long websiteId) {
        getMap(CacheConstants.WEBSITE_SUBPAGES).delete(websiteId);
    }


}
