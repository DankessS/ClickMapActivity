package com.academy.cache;

import org.hibernate.mapping.Array;
import org.springframework.stereotype.Component;
import sun.text.CollatorUtilities;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.StreamSupport.stream;

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
        getMap(CacheConstants.WEBSITE_SUBPAGES).removeAsync(websiteId);
    }

    public void setSubpageActivities(Long subpageId, Iterable activities) {
        getMap(CacheConstants.ACTIVITIES).set(subpageId,activities);
    }

    public synchronized void addSubpageActivity(Long subpageId, Object activity) {
        Iterable values = (Iterable)getMap(CacheConstants.ACTIVITIES).get(subpageId);
        if(values != null) {
            List l = (List)StreamSupport.stream(values.spliterator(),false).collect(Collectors.toList());
            l.add(activity);
            getMap(CacheConstants.ACTIVITIES).set(subpageId, l);
        } else {
            List c = Arrays.asList(activity);
            getMap(CacheConstants.ACTIVITIES).set(subpageId, c);
        }
    }

    public Iterable getSubpageActivities(Long subpageId) {
        return (Iterable)getMap(CacheConstants.ACTIVITIES).get(subpageId);
    }

    public void deleteSubpageActivities(Long subpageId) {
        getMap(CacheConstants.ACTIVITIES).removeAsync(subpageId);
    }

    public void setActivityPoints(Long activityId, Iterable points) {
        getMap(CacheConstants.POINTS).set(activityId, points);
    }

    public Iterable getActivityPoints(Long activityId) {
        return (Iterable)getMap(CacheConstants.POINTS).get(activityId);
    }

    public void deleteActivityPoints(Long activityId) {
        getMap(CacheConstants.POINTS).removeAsync(activityId);
    }

}