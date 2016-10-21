package com.academy.service.mappers;

import com.academy.model.dao.Activity;
import com.academy.model.dao.Subpage;
import com.academy.model.dto.ActivityDTO;
import com.academy.repo.SubpageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
@Component
public class ActivityMapper implements Mapper<Activity, ActivityDTO> {

    @Autowired
    SubpageRepo subpageRepo;

    @Override
    public Activity convertToDAO(ActivityDTO dto) {
        final Activity dao = new Activity();
        dao.setId(dto.getId());
        dao.setDate(dto.getDate() == null ? new Date() : dto.getDate());
        if(dto.getSubpageId() != null) {
            Subpage subpage = subpageRepo.findOne(dto.getSubpageId());
            if(subpage != null) {
                dao.setSubpage(subpage);
            }
        }
        return dao;
    }

    @Override
    public ActivityDTO convertToDTO(Activity dao) {
        final ActivityDTO dto = new ActivityDTO();
        dto.setId(dao.getId());
        dto.setDate(dao.getDate());
        if(dao.getSubpage() != null) {
            dto.setSubpageId(dao.getSubpage().getId());
        }
        return dto;
    }

}
