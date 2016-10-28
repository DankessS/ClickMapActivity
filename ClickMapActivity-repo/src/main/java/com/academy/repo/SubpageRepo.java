package com.academy.repo;

import com.academy.model.dao.Subpage;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
public interface SubpageRepo extends CrudRepository<Subpage, Long> {

    Iterable<Subpage> getByWebsiteId(Long websiteId);

    void deleteByNameAndWebsiteId(String name, Long websiteId);

    Subpage findByNameAndWebsiteId(String name, Long websiteId);

}
