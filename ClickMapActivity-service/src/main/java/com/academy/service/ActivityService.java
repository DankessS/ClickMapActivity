package com.academy.service;

import com.academy.cache.UserCache;
import com.academy.model.dao.Activity;
import com.academy.model.dto.ActivityDTO;
import com.academy.model.dto.PointsDTO;
import com.academy.model.dto.SubpageDTO;
import com.academy.model.dto.WebsiteDTO;
import com.academy.repo.ActivityRepo;
import com.academy.service.mappers.ActivityMapper;
import com.academy.service.mappers.PointsMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
public class ActivityService extends AbstractService<Activity, ActivityDTO, ActivityRepo, ActivityMapper> {

    private final static Logger LOGGER = LogManager.getLogger(ActivityService.class);

    @Autowired
    private UserCache cache;

    @Autowired
    private WebsiteService websiteService;

    @Autowired
    private SubpageService subpageService;

    @Autowired
    private PointsService pointsService;

    @Autowired
    private PointsMapper pointsMapper;

    public void logActivity(String websiteName, String subpageName, String resolution, Iterable<String> points) throws IllegalStateException {
        LocalDateTime receivedTime = LocalDateTime.now();
        final WebsiteDTO websiteDTO = websiteService.getByName(websiteName);
        List<String> pointsList = StreamSupport.stream(points.spliterator(),false).collect(Collectors.toList());
        if (websiteDTO == null) {
            LOGGER.warn("Request from unauthorized site [" + websiteName + "]");
            return;
        }
        final SubpageDTO subpageDTO = subpageService.getByNameAndWebsiteId(subpageName, websiteDTO.getId());
        if (subpageDTO == null) {
            LOGGER.warn("Request from authorized site [" + websiteName + "], but from unauthorized subpage [" + subpageName + "]");
            return;
        }
        points = adjustResolution(subpageDTO.getResX(), subpageDTO.getResY(), resolution, pointsList);
        Activity activity = insertActivity(receivedTime, subpageDTO.getId());
        subpageDTO.setLastUpdateEpoch(activity.getDate().toInstant(ZoneOffset.UTC).getEpochSecond());
        subpageDTO.setDisplays(new Long(((Collection)cache.getSubpageActivities(subpageDTO.getId())).size()));
        cache.updateWebsiteSubpage(websiteDTO.getId(), subpageDTO.getId(), subpageDTO);
        if (activity == null) {
            LOGGER.warn("Could not insert activity for request from site [" + websiteName + "] and subpage [" + subpageName + "]");
            return;
        }
        final Long activityId = activity.getId();
        List<PointsDTO> convertedPoints = new ArrayList<>();
        points.forEach(p ->
               convertedPoints.add(pointsMapper.convertToDTO(pointsService.addPointsCouple(activityId, p)))
        );
        if(cache.getLoggedUsername() != null) {
            cache.setActivityPoints(activityId, convertedPoints);
        }
    }

    private Iterable adjustResolution(Integer subX, Integer subY, String pointsRes, List<String> points) {
        String[] separatedRes = pointsRes.split("x");
        List<String> convertedPoints = new ArrayList<>();
        try {
            Integer pointsX = Integer.parseInt(separatedRes[0]);
            Integer pointsY = Integer.parseInt(separatedRes[1]);
            if (separatedRes.length == 2 && Integer.parseInt(separatedRes[0]) > 0 && Integer.parseInt(separatedRes[1]) > 0) {
                final double propX = (double)subX / (double)pointsX;
                final double propY = (double)subY / (double)pointsY;
                points.forEach(p-> {
                    final String [] tmp = p.split(";");
                    convertedPoints.add((int)(Integer.parseInt(tmp[0]) * propX) + ";" + (int)(Integer.parseInt(tmp[1]) * propY));
                });
            } else {
                throw new IllegalStateException();
            }
        } catch (Exception e) {
            LOGGER.warn("Error while adjusting points resolution, subpage resolution: " +
                    subX.toString() + "x" + subY.toString() + ", points resolution: " + pointsRes);
            throw e;
        }
        return convertedPoints;
    }


    public Activity insertActivity(LocalDateTime date, Long subpageId) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setDate(date);
        activityDTO.setSubpageId(subpageId);
        Activity a = repo.save(mapper.convertToDAO(activityDTO));
        if(cache.getLoggedUsername() != null) {
            activityDTO.setId(a.getId());
            cache.addSubpageActivity(subpageId, activityDTO);
        }
        return a;
    }

    public Iterable<ActivityDTO> getBySubpageId(Long subpageId) {
        return mapper.convertToDTO(repo.findBySubpageId(subpageId));
    }

}