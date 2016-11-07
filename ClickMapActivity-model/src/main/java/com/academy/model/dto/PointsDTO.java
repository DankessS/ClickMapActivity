package com.academy.model.dto;

import java.io.Serializable;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public class PointsDTO implements Serializable {

    private Long id;
    private String pairValue;
    private Long activityId;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getPairValue() {
        return pairValue;
    }

    public void setPairValue(String pairValue) {
        this.pairValue = pairValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
