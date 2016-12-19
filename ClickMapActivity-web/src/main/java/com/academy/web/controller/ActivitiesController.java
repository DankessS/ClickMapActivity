package com.academy.web.controller;

import com.academy.model.PointsWrapper;
import com.academy.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Daniel Palonek on 2016-10-25.
 */
@RestController
@RequestMapping("/activities")
public class ActivitiesController {

    @Autowired
    private ActivityService activityService;

    @RequestMapping(value = "/log/{wName}/{sName}/{res}", method = RequestMethod.POST, consumes = "application/json")
    public void logActivity(@PathVariable("wName") String websiteName,
                            @PathVariable("sName") String subpageName,
                            @PathVariable("res") String resolution,
                            @RequestBody PointsWrapper points) {
        activityService.logActivity(websiteName, subpageName, resolution, points.getPoints());
    }

}
