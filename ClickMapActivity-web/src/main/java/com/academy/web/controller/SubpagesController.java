package com.academy.web.controller;

import com.academy.cache.UserCache;
import com.academy.model.ValueWrapper;
import com.academy.model.dao.Subpage;
import com.academy.model.dto.SubpageDTO;
import com.academy.model.dto.WebsiteDTO;
import com.academy.service.SubpageService;
import com.academy.service.tools.ImageConverter;
import com.academy.web.config.RedirectUrls;
import com.hazelcast.nio.IOUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Daniel Palonek on 2016-10-06.
 */
@RestController
@RequestMapping("/subpages")
public class SubpagesController extends AbstractController<Subpage, SubpageDTO, SubpageService> {

    private static final Logger LOGGER = LogManager.getLogger(SubpagesController.class);

    @Autowired
    UserCache cache;

    @RequestMapping(value = "/add", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public ModelAndView addSubpage(@RequestParam("file") MultipartFile file,
                                   @RequestParam("subpageName") String name,
                                   RedirectAttributes redAttr) {
        return new ModelAndView(service.saveSubpage(name, file, redAttr) ?
                "redirect:/user/#/subpages/{websiteUrl}"
                : RedirectUrls.ERROR_COULD_NOT_UPLOAD_SUBPAGE);
    }

    @RequestMapping(value = "/getByWebsiteId", method = RequestMethod.GET)
    public Iterable<SubpageDTO> getByWebsiteId() {
        final WebsiteDTO website = (WebsiteDTO) cache.getRequestedWebsite();
        return cache.getWebsiteSubpages(website.getId());
    }

    @RequestMapping(value = "/images/{name:.+}", method = RequestMethod.GET)
    public void getSubpageImage(@PathVariable("name") String name,
                                HttpServletResponse response) {
        service.getImage(name,response);
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
    public ValueWrapper<Boolean> deleteSubpage(@PathVariable("name") String name) {
        return new ValueWrapper<>(service.delete(name));
    }

    @RequestMapping(value = "/checkIfExists/{name}", method = RequestMethod.GET)
    public ValueWrapper<Boolean> checkIfSubpageExists(@PathVariable("name") String name) {
        return new ValueWrapper<>(service.checkIfExists(name));
    }

}
