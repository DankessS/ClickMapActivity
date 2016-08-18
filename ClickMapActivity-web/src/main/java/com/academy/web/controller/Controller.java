package com.academy.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
@RestController
public class Controller {

    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("public/index");
    }
}
