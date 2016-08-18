package com.academy.model;

import javax.persistence.*;

/**
 * Created by DankessS on 2016-08-12.
 */
@Entity
public class Points {

    @Id
    @GeneratedValue
    private Long id;
    private String pairValue;

    @ManyToOne
    private Activity activity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPairValue() {
        return pairValue;
    }

    public void setPairValue(String pairValue) {
        this.pairValue = pairValue;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
