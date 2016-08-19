package com.academy.service.mappers;

import com.academy.model.dao.Activity;
import com.academy.model.dao.Points;
import com.academy.model.dto.PointsDTO;
import com.academy.repo.AccountRepo;
import com.academy.repo.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public class PointsMapper implements Mapper<Points,PointsDTO> {

    @Autowired
    ActivityRepo activityRepo;

    @Override
    public Points convertToDAO(PointsDTO dto) {
        final Points dao = new Points();
        dao.setId(dto.getId());
        dao.setPairValue(dto.getPairValue());
        if(dto.getActivityId() != null) {
            Activity activity = activityRepo.findOne(dto.getActivityId());
            if(activity != null) {
                dao.setActivity(activity);
            }
        }
        return dao;
    }

    @Override
    public PointsDTO convertToDTO(Points dao) {
        final PointsDTO dto = new PointsDTO();
        dto.setId(dao.getId());
        dto.setPairValue(dao.getPairValue());
        if(dao.getActivity() != null) {
            dto.setActivityId(dao.getActivity().getId());
        }
        return dto;
    }

}
