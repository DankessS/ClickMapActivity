package com.academy.model.dto;

import java.time.LocalDateTime;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public class ActivityDTO {

    private Long id;
    private LocalDateTime date;
    private Long subpageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getSubpageId() {
        return subpageId;
    }

    public void setSubpageId(Long subpageId) {
        this.subpageId = subpageId;
    }
}
