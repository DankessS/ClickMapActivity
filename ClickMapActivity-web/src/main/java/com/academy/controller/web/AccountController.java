package com.academy.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Palonek on 2016-08-04.
 */
@RestController
public class AccountController {

    @RequestMapping("/index")
    public String index() {
        return "Test Spring Boot";
    }
}
