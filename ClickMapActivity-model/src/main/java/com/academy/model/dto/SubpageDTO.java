package com.academy.model.dto;

import java.io.Serializable;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public class SubpageDTO implements Serializable {

    private Long id;
    private String name;
    private Integer resX;
    private Integer resY;
    private Long lastUpdateEpoch;
    private Long displays;
    private Long websiteId;

    public Long getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public Integer getResY() {
        return resY;
    }

    public void setResY(Integer resY) {
        this.resY = resY;
    }

    public Integer getResX() {
        return resX;
    }

    public void setResX(Integer resX) {
        this.resX = resX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLastUpdateEpoch() {
        return lastUpdateEpoch;
    }

    public void setLastUpdateEpoch(Long lastUpdateEpoch) {
        this.lastUpdateEpoch = lastUpdateEpoch;
    }

    public Long getDisplays() {
        return displays;
    }

    public void setDisplays(Long displays) {
        this.displays = displays;
    }
}
