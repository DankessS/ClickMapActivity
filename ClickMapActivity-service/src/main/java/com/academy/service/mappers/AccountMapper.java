package com.academy.service.mappers;

import com.academy.model.dao.Account;
import com.academy.model.dto.AccountDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
@Component
public class AccountMapper implements Mapper<Account, AccountDTO> {

    @Override
    public Account convertToDAO(AccountDTO dto) {
        final Account dao = new Account();
        dao.setPassword(dto.getPassword());
        dao.setUserName(dto.getUserName());
        dao.setEmail(dto.getEmail());
        dao.setId(dto.getId());
        dao.setPhoneNumber(dto.getPhoneNumber());
        dao.setSurName(dto.getSurName());
        dao.setFirstName(dto.getFirstName());
        return dao;
    }

    @Override
    public AccountDTO convertToDTO(Account dao) {
        final AccountDTO dto = new AccountDTO();
        dto.setFirstName(dao.getFirstName());
        dto.setSurName(dao.getSurName());
        dto.setPhoneNumber(dao.getPhoneNumber());
        dto.setId(dao.getId());
        dto.setEmail(dao.getEmail());
        dto.setPassword(dao.getPassword());
        dto.setUserName(dao.getUserName());
        return dto;
    }

}
