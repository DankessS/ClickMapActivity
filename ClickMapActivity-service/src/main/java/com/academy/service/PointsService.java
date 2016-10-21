package com.academy.service;

import com.academy.model.dao.Points;
import com.academy.model.dto.PointsDTO;
import com.academy.repo.PointsRepo;
import com.academy.service.mappers.PointsMapper;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
public class PointsService extends AbstractService<Points,PointsDTO,PointsRepo,PointsMapper> {
}
