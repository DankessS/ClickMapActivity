package com.academy.repo;

import com.academy.model.dao.Points;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
public interface PointsRepo extends CrudRepository<Points,Long> {

    Iterable<Points> findByActivityId(Long activityId);

}
