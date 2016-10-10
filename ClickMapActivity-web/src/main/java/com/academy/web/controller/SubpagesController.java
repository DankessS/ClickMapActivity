package com.academy.web.controller;

import com.academy.model.dao.Subpage;
import com.academy.model.dto.SubpageDTO;
import com.academy.service.SubpageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daniel Palonek on 2016-10-06.
 */
@RestController
@RequestMapping("/subpages")
public class SubpagesController extends AbstractController<Subpage,SubpageDTO,SubpageService> {

}
