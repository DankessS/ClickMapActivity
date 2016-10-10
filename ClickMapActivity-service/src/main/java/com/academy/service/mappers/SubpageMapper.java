package com.academy.service.mappers;

import com.academy.model.dao.Subpage;
import com.academy.model.dao.Website;
import com.academy.model.dto.SubpageDTO;
import com.academy.repo.WebsiteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
@Component
public class SubpageMapper implements Mapper<Subpage, SubpageDTO> {

    @Autowired
    WebsiteRepo websiteRepo;

    @Override
    public Subpage convertToDAO(SubpageDTO dto) {
        final Subpage dao = new Subpage();
        dao.setId(dto.getId());
        dao.setName(dto.getName());
        dao.setResX(dto.getResX());
        dao.setResY(dto.getResY());
        if(dto.getWebsiteId() != null) {
            Website website = websiteRepo.findOne(dto.getWebsiteId());
            if(website != null) {
                dao.setWebsite(website);
            }
        }
        return dao;
    }

    @Override
    public SubpageDTO convertToDTO(Subpage dao) {
        final SubpageDTO dto = new SubpageDTO();
        dto.setId(dao.getId());
        dto.setResX(dao.getResX());
        dto.setResY(dao.getResY());
        dto.setName(dao.getName());
        if(dao.getWebsite() != null) {
            dto.setWebsiteId(dao.getWebsite().getId());
        }
        return dto;
    }

}
