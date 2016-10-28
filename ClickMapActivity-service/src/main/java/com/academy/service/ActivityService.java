package com.academy.service;

import com.academy.cache.UserCache;
import com.academy.model.dao.Activity;
import com.academy.model.dto.ActivityDTO;
import com.academy.model.dto.SubpageDTO;
import com.academy.model.dto.WebsiteDTO;
import com.academy.repo.ActivityRepo;
import com.academy.service.mappers.ActivityMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
public class ActivityService extends AbstractService<Activity,ActivityDTO,ActivityRepo,ActivityMapper> {

    private final static Logger LOGGER = LogManager.getLogger(ActivityService.class);

    @Autowired
    private UserCache cache;

    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private SubpageService subpageService;

    @Autowired
    private PointsService pointsService;

    public void logActivity(String websiteName, String subpageName, Iterable points) {
        LocalDateTime receivedTime = LocalDateTime.now();
        final WebsiteDTO websiteDTO = websiteService.getByName(websiteName);
        if(websiteDTO == null) {
            LOGGER.warn("Request from unauthorized site [" + websiteName + "]");
            return;
        }
        final SubpageDTO subpageDTO = subpageService.getByNameAndWebsiteId(subpageName,websiteDTO.getId());
        if(subpageDTO == null) {
            LOGGER.warn("Request from authorized site [" + websiteName + "], but from unauthorized subpage [" + subpageName + "]");
            return;
        }
        Activity activity = addActivity(receivedTime,subpageDTO.getId());
        if(activity == null) {
            LOGGER.warn("Could not insert activity for request from site [" + websiteName + "] and subpage [" + subpageName + "]");
            return;
        }
        final Long activityId = activity.getId();
        points.forEach( p -> pointsService.addPointsCouple(activityId,(String)p ) );
    }

    public Activity addActivity(LocalDateTime date, Long subpageId) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setDate(date);
        activityDTO.setSubpageId(subpageId);
        return repo.save(mapper.convertToDAO(activityDTO));
    }

    public Iterable<ActivityDTO> getBySubpageId(Long subpageId) {
        return mapper.convertToDTO(repo.findBySubpageId(subpageId));
    }

}
