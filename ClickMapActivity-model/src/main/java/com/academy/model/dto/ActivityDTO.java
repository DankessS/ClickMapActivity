package com.academy.model.dto;

import java.util.Date;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public class ActivityDTO {

    private Long id;
    private Date date;
    private Long subpageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getSubpageId() {
        return subpageId;
    }

    public void setSubpageId(Long subpageId) {
        this.subpageId = subpageId;
    }
}
