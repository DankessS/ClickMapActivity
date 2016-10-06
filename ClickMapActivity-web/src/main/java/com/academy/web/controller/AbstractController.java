package com.academy.web.controller;

import com.academy.service.AbstractService;
import com.academy.service.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Daniel Palonek on 2016-09-08.
 */
abstract public class AbstractController<DAO, DTO, S extends AbstractService<DAO, DTO, ? extends CrudRepository<DAO,Long>, ? extends Mapper<DAO, DTO>>> {

    @Autowired
    S service;

    @RequestMapping("/{id}")
    public DTO findOne(@PathVariable int id, HttpServletRequest request) {
        return service.findOne(new Long(id));
    }

    @RequestMapping("/all")
    public Iterable<DTO> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody DTO modelToSave) {
        service.save(modelToSave);
    }

    @RequestMapping(value = "/save/all", method = RequestMethod.POST)
    public void save(@RequestBody Iterable<DTO> modelsToSave) {
        service.save(modelsToSave);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(@RequestBody DTO model, @PathVariable int id) {
        service.delete(model);
    }

    @RequestMapping(value = "/delete/all", method = RequestMethod.POST)
    public void delete(@RequestBody Iterable<DTO> modelsToDelete) {
        service.delete(modelsToDelete);
    }

}
