package com.academy.service;

import com.academy.cache.UserCache;
import com.academy.model.RegisterRequestBody;
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

    @Autowired
    UserCache cache;

    public boolean checkIfAccountWithGivenUsernameExists(String username) {
        return !(repo.findAccountByUserName(username) == null);
    }

    public boolean saveAccount(RegisterRequestBody data) {

        if(findAccountIdByUsername(data.getUsername()) != -1L) {
            return false;
        }

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserName(data.getUsername());
        accountDTO.setPassword(data.getPassword());
        accountDTO.setFirstName(data.getFirstName());
        accountDTO.setSurName(data.getSurname());
        accountDTO.setPhoneNumber(data.getPhone());
        accountDTO.setEmail(data.getEmail());

        repo.save(mapper.convertToDAO(accountDTO));
        userService.save(data.getUsername(), data.getPassword());
        return true;
    }

    public Long findAccountIdByUsername(String username) {
        Account account = repo.findAccountByUserName(username);
        return account == null ? -1L : account.getId();
    }

    public Long getLoggedUserAccountId() {
        return findAccountIdByUsername(cache.getLoggedUsername());
    }

}
