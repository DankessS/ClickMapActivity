package com.academy.repo;

import com.academy.model.dao.UserRole;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Daniel Palonek on 2016-09-03.
 */
public interface UserRoleRepo extends CrudRepository<UserRole,Long> {
}
