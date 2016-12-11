package com.academy.web.config;

import com.academy.cache.UserCache;
import com.academy.model.dto.ActivityDTO;
import com.academy.model.dto.PointsDTO;
import com.academy.model.dto.SubpageDTO;
import com.academy.model.dto.WebsiteDTO;
import com.academy.service.ActivityService;
import com.academy.service.PointsService;
import com.academy.service.SubpageService;
import com.academy.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Daniel Palonek on 2016-10-20.
 */
@Component
public class CacheLoader {

    @Autowired
    UserCache cache;

    @Autowired
    WebsiteService websiteService;

    @Autowired
    SubpageService subpageService;

    @Autowired
    ActivityService activityService;

    @Autowired
    PointsService pointsService;

    @Async
    public void load(final String username) {
        cache.setLoggedUsername(username);
        Iterable<WebsiteDTO> websites = websiteService.getUserWebsites();
        cache.setUserWebsites(websites);
        websites.forEach( w -> {
            final Iterable<SubpageDTO> subpages = subpageService.getSubgapesForWebsiteId(w.getId());
            subpages.forEach(s-> {
                Collection<ActivityDTO> activities = (Collection)activityService.getBySubpageId(s.getId());
                cache.setSubpageActivities(s.getId(), activities);
                activities.parallelStream()
                        .sorted((a1,a2) -> a1.getDate().compareTo(a2.getDate()))
                        .forEach(a-> {
                            Iterable<PointsDTO> points = pointsService.getByActivityId(a.getId());
                            s.setDisplays(new Long(((Collection)points).size()) + s.getDisplays());
                            cache.setActivityPoints(a.getId(), points);
                        });
                if(activities.size() > 0) {
                    s.setLastUpdateEpoch(new ArrayList<>(activities).get(activities.size() - 1).getDate().toInstant(ZoneOffset.UTC).getEpochSecond());
                }
            });
            cache.setWebsiteSubpages(w.getId(), StreamSupport.stream(subpages.spliterator(), false).collect(Collectors.toMap(SubpageDTO::getId, Function.identity())));
        });
    }

    public void clean() {
        cache.getUserWebsites().forEach(w -> {
            cache.getWebsiteSubpages(((WebsiteDTO) w).getId()).forEach(s-> {
                cache.getSubpageActivities(((SubpageDTO)s).getId()).forEach(a->
                    cache.deleteActivityPoints(((ActivityDTO)a).getId())
                );
                cache.deleteSubpageActivities(((SubpageDTO) s).getId());
            });
            cache.deleteWebsiteSubpages(((WebsiteDTO) w).getId());
        });
        cache.removeUserWebsites();
        cache.removeLoggedUsername();
    }
}
