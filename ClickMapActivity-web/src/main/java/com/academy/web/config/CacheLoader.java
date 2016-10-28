package com.academy.web.config;

import com.academy.cache.UserCache;
import com.academy.model.dto.SubpageDTO;
import com.academy.model.dto.WebsiteDTO;
import com.academy.service.ActivityService;
import com.academy.service.SubpageService;
import com.academy.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void load(final String username) {
        cache.setLoggedUsername(username);
        Iterable<WebsiteDTO> websites = websiteService.getUserWebsites();
        cache.setUserWebsites(websites);
        websites.forEach( w -> {
            final Iterable<SubpageDTO> subpages = subpageService.getSubgapesForWebsiteId(w.getId());
            cache.setWebsiteSubpages(w.getId(), subpages);
            subpages.forEach(s->
                cache.setSubpageActivities(s.getId(), activityService.getBySubpageId(s.getId()))
            );
        });
    }

    public void clean() {
        cache.getUserWebsites().forEach(w -> {
            cache.getWebsiteSubpages(((WebsiteDTO) w).getId()).forEach(s->
                cache.deleteSubpageActivities(((SubpageDTO)s).getId())
            );
            cache.deleteWebsiteSubpages(((WebsiteDTO) w).getId());
        });
        cache.removeUserWebsites();
        cache.removeLoggedUsername();
    }
}
