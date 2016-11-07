package com.academy.service;

import com.academy.model.dao.Points;
import com.academy.model.dto.PointsDTO;
import com.academy.repo.PointsRepo;
import com.academy.service.mappers.PointsMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
public class PointsService extends AbstractService<Points,PointsDTO,PointsRepo,PointsMapper> {

    private static final Logger LOGGER = LogManager.getLogger(PointsService.class);

    public void addPointsCouple(Long activityId, String pointsCouple) {
        PointsDTO pointsDTO = new PointsDTO();
        pointsDTO.setActivityId(activityId);
        pointsDTO.setPairValue(pointsCouple);
        repo.save(mapper.convertToDAO(pointsDTO));
    }

    public Iterable<PointsDTO> getByActivityId(Long activityId) {
        return mapper.convertToDTO(repo.findByActivityId(activityId));
    }

}
