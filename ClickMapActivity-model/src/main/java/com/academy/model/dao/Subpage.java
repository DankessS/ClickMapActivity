package com.academy.model.dao;

import javax.persistence.*;
import java.util.List;

/**
 * Created by DankessS on 2016-08-10.
 */
@Entity
public class Subpage {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer resX;
    private Integer resY;

    @ManyToOne
    private Website website;

    @OneToMany(mappedBy = "subpage")
    private List<Activity> activityPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResX() {
        return resX;
    }

    public void setResX(Integer resX) {
        this.resX = resX;
    }

    public Integer getResY() {
        return resY;
    }

    public void setResY(Integer resY) {
        this.resY = resY;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public List<Activity> getActivityPoints() {
        return activityPoints;
    }

    public void setActivityPoints(List<Activity> activityPoints) {
        this.activityPoints = activityPoints;
    }
}
