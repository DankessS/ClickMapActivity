package com.academy.service;

import com.academy.model.dao.Activity;
import com.academy.model.dto.ActivityDTO;
import com.academy.repo.ActivityRepo;
import com.academy.service.mappers.ActivityMapper;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
public class ActivityService extends AbstractService<Activity,ActivityDTO,ActivityRepo,ActivityMapper> {

}
