package com.academy.repo;

import com.academy.model.dao.Activity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
public interface ActivityRepo extends CrudRepository<Activity,Long> {

    Iterable<Activity> findBySubpageId(Long subpageId);

}
