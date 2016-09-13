package com.academy.repo;

import com.academy.model.dao.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Daniel Palonek on 2016-09-03.
 */
public interface UserRepo extends CrudRepository<User,Long> {
    User findByUsername(String name);
}
