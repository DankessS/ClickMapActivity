package com.academy.repo;

import com.academy.model.dao.Website;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
public interface WebsiteRepo extends CrudRepository<Website, Long> {
}
