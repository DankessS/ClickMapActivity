package com.academy.web.controller;

import com.academy.cache.UserCache;
import com.academy.model.RegisterRequestBody;
import com.academy.model.ValueWrapper;
import com.academy.model.dao.Account;
import com.academy.model.dto.AccountDTO;
import com.academy.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * Created by Daniel Palonek on 2016-08-04.
 */
@RestController
@RequestMapping("/account")
public class AccountController extends AbstractController<Account,AccountDTO,AccountService>{

    @Autowired
    UserCache cache;

    @RequestMapping(value = "/logged/name")
    public ValueWrapper<String> getLoggedUsername() {
        return new ValueWrapper(cache.getLoggedUsername());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ValueWrapper<Boolean> register(@RequestBody RegisterRequestBody data) throws ParseException {
        return new ValueWrapper<>(service.saveAccount(data));
    }

    @RequestMapping(value = "/exist/{username}", method = RequestMethod.GET)
    public ValueWrapper<Boolean> checkIfUserAlreadyExist(@PathVariable("username") String username) {
        return new ValueWrapper<>(service.checkIfAccountWithGivenUsernameExists(username));
    }
}
