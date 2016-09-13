package com.academy.web.controller;

import com.academy.model.ValueWrapper;
import com.academy.model.dao.Account;
import com.academy.model.dto.AccountDTO;
import com.academy.service.AccountService;
import com.academy.web.config.RedirectUrls;
import com.academy.web.config.cache.CacheConstants;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Daniel Palonek on 2016-08-04.
 */
@RestController
@RequestMapping("/account")
public class AccountController extends AbstractController<Account,AccountDTO,AccountService>{

    @Autowired
    HazelcastInstance hazelcastInstance;

    @RequestMapping(value = "/logged/name")
    public ValueWrapper<String> getLoggedUsername() {
        return new ValueWrapper(hazelcastInstance.getUserContext().getOrDefault(CacheConstants.LOGGED_USERNAME,null));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public ModelAndView register(@RequestBody final MultiValueMap<String,String> data) {
        return new ModelAndView(service.saveAccount(data) ? "redirect:" : RedirectUrls.ERROR_USER_EXIST);
    }

    @RequestMapping(value = "/exist/{username}", method = RequestMethod.GET)
    public ValueWrapper<Boolean> checkIfUserAlreadyExist(@PathVariable("username") String username) {
        Boolean ret = service.checkIfAccountWithGivenUsernameExists(username);
        return new ValueWrapper<>(ret);
    }
}
