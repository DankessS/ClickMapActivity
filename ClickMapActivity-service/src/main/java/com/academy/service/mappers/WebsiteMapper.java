package com.academy.service.mappers;

import com.academy.model.dao.Account;
import com.academy.model.dao.Website;
import com.academy.model.dto.WebsiteDTO;
import com.academy.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public class WebsiteMapper implements Mapper<Website,WebsiteDTO> {

    @Autowired
    AccountRepo accountRepo;

    @Override
    public Website convertToDAO(WebsiteDTO dto) {
        final Website dao = new Website();
        dao.setId(dto.getId());
        dao.setUrl(dto.getUrl());
        if(dto.getAccountId() != null) {
            Account account = accountRepo.findOne(dto.getAccountId());
            if (account != null) {
                dao.setAccount(account);
            }
        }
        return dao;
    }

    @Override
    public WebsiteDTO convertToDTO(Website dao) {
        final WebsiteDTO dto = new WebsiteDTO();
        dto.setId(dao.getId());
        dto.setUrl(dao.getUrl());
        if(dao.getAccount() != null) {
            dto.setAccountId(dao.getAccount().getId());
        }
        return dto;
    }

}
