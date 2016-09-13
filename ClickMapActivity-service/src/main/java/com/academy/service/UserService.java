package com.academy.service;

import com.academy.model.dao.User;
import com.academy.model.dao.UserRole;
import com.academy.model.dto.UserDTO;
import com.academy.repo.UserRepo;
import com.academy.repo.UserRoleRepo;
import com.academy.service.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Palonek on 2016-09-03.
 */
@Service
public class UserService extends AbstractService<User,UserDTO,UserRepo,UserMapper> {

    @Autowired
    UserRoleRepo userRoleRepo;

    public void save(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);

        UserRole userRole = new UserRole();
        userRole.setRole("ROLE_USER");

        user.getUserRole().add(userRole);
        userRole.setUser(user);

        repo.save(user);
        userRoleRepo.save(userRole);
    }

}
