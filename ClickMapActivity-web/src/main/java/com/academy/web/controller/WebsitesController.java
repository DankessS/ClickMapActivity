package com.academy.web.controller;

import com.academy.cache.UserCache;
import com.academy.model.ValueWrapper;
import com.academy.model.dao.Website;
import com.academy.model.dto.WebsiteDTO;
import com.academy.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Daniel Palonek on 2016-09-16.
 */
@RestController
@RequestMapping("/websites")
public class WebsitesController extends AbstractController<Website,WebsiteDTO,WebsiteService> {

    @Autowired
    private UserCache cache;

    @RequestMapping(value = "/getUserWebsites", method = RequestMethod.GET)
    public Iterable<WebsiteDTO> getUserWebsites() {
        return (Iterable<WebsiteDTO>)cache.getUserWebsites();
    }

    @RequestMapping(value = "/add/{websiteUrl:.+}", method = RequestMethod.POST)
    public ValueWrapper<Boolean> addWebsite(@PathVariable final String websiteUrl) {
        return new ValueWrapper<>(service.saveWebsite(websiteUrl));
    }

    @RequestMapping(value ="/delete/{id}", method=RequestMethod.DELETE)
    public ValueWrapper<Boolean> deleteWebsite(@PathVariable final Long id) {
        return new ValueWrapper<>(service.delete(id));
    }

    @RequestMapping(value = "/saveRequestedWebsite", method = RequestMethod.PUT)
    public void saveRequestedWebsite(@RequestBody final WebsiteDTO website) {
        cache.setRequestedWebsite(website);
    }

    @RequestMapping(value = "/getRequestedWebsite", method = RequestMethod.GET)
    public WebsiteDTO getRequestedWebsite() {
        return (WebsiteDTO)cache.getRequestedWebsite();
    }

}
