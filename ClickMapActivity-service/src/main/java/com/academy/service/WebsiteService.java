package com.academy.service;

import com.academy.cache.UserCache;
import com.academy.model.dao.Website;
import com.academy.model.dto.WebsiteDTO;
import com.academy.repo.WebsiteRepo;
import com.academy.service.mappers.WebsiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
public class WebsiteService extends AbstractService<Website,WebsiteDTO,WebsiteRepo,WebsiteMapper> {

    @Autowired
    AccountService accountService;

    @Autowired
    UserCache cache;

    public Iterable<WebsiteDTO> getUserWebsites() {
        String username = cache.getLoggedUsername();
        Long accountId = accountService.findAccountIdByUsername(username);
        Iterable<Website> websites = repo.findByAccountId(accountId);
        return mapper.convertToDTO(websites);
    }

    public boolean saveWebsite(String websiteUrl) {
        WebsiteDTO website = new WebsiteDTO();
        website.setAccountId(accountService.getLoggedUserAccountId());
        website.setUrl(websiteUrl);
        save(website);
        cache.setUserWebsites(getUserWebsites());
        return true;
    }

    public boolean delete(Long websiteId) {
        repo.delete(websiteId);
        cache.setUserWebsites(getUserWebsites());
        return true;
    }

}
