package com.academy.web.controller;

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

    @RequestMapping(value = "/log/{wName}/{sName}", method = RequestMethod.POST)
    public void logActivity(@PathVariable("wName") String websiteName,
                            @PathVariable("sName") String subpageName,
                            Iterable points) {
        activityService.logActivity(websiteName, subpageName, points);
    }

}
