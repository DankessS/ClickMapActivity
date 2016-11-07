package com.academy.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public class ActivityDTO implements Serializable {

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

    public int compare(LocalDateTime other) {
        return this.getDate().compareTo(other);
    }
}
