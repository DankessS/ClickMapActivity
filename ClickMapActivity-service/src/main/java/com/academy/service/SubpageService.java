package com.academy.service;

import com.academy.model.dao.Subpage;
import com.academy.model.dto.SubpageDTO;
import com.academy.repo.SubpageRepo;
import com.academy.service.AbstractService;
import com.academy.service.mappers.SubpageMapper;
import org.springframework.stereotype.Service;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
public class SubpageService extends AbstractService<Subpage,SubpageDTO,SubpageRepo,SubpageMapper> {
}
