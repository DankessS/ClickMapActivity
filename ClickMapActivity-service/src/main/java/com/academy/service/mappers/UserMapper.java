package com.academy.service.mappers;

import com.academy.model.dao.User;
import com.academy.model.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel Palonek on 2016-09-09.
 */
@Component
public class UserMapper implements Mapper<User,UserDTO> {

    @Override
    public User convertToDAO(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public UserDTO convertToDTO(User dao) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(dao.getUsername());
        userDTO.setPassword(dao.getPassword());
        return userDTO;
    }
}
