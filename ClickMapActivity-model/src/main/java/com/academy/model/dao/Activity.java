package com.academy.model.dao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * Created by DankessS on 2016-08-10.
 */
@Entity
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "activity", cascade = {CascadeType.ALL})
    private Set<Points> points;

    private LocalDateTime date;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Subpage subpage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Points> getPoints() {
        return points;
    }

    public void setPoints(Set<Points> points) {
        this.points = points;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Subpage getSubpage() {
        return subpage;
    }

    public void setSubpage(Subpage subpage) {
        this.subpage = subpage;
    }
}
