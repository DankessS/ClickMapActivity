package com.academy.model.dao;

import javax.persistence.*;
import java.util.List;

/**
 * Created by DankessS on 2016-08-10.
 */
@Entity
public class Website {

    @Id
    @GeneratedValue
    private Long id;
    private String url;

    @ManyToOne
    private Account account;

    @OneToMany(mappedBy = "website", cascade = {CascadeType.ALL})
    private List<Subpage> subpages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Subpage> getSubpages() {
        return subpages;
    }

    public void setSubpages(List<Subpage> subpages) {
        this.subpages = subpages;
    }
}
