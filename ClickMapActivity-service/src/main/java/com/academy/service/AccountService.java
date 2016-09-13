package com.academy.service;

import com.academy.model.dao.Account;
import com.academy.model.dto.AccountDTO;
import com.academy.repo.AccountRepo;
import com.academy.service.mappers.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
@Service
public class AccountService extends AbstractService<Account,AccountDTO,AccountRepo,AccountMapper> {

    @Autowired
    UserService userService;

    public boolean checkIfAccountWithGivenUsernameExists(String username) {
        return !(repo.findAccountByUserName(username) == null);
    }

    public boolean saveAccount(MultiValueMap<String, String> data) {

        if(findAccountIdByUsername(data.getFirst("username")) != -1L) {
            return false;
        }

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserName(data.getFirst("username"));
        accountDTO.setPassword(data.getFirst("password"));
        accountDTO.setFirstName(data.getFirst("firstName"));
        accountDTO.setSurName(data.getFirst("surname"));
        accountDTO.setPhoneNumber(data.getFirst("phone"));
        accountDTO.setEmail(data.getFirst("email"));

        repo.save(mapper.convertToDAO(accountDTO));
        userService.save(data.getFirst("username"), data.getFirst("password"));
        return true;
    }

    public Long findAccountIdByUsername(String username) {
        Account account = repo.findAccountByUserName(username);
        return account == null ? -1L : account.getId();
    }

}
