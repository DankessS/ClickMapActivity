package com.academy.model.dto;

import java.io.Serializable;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public class WebsiteDTO implements Serializable {

    private Long id;
    private String url;
    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
