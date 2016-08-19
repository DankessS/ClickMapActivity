package com.academy.service;

import com.academy.service.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
abstract public class AbstractService<DAO,DTO, R extends CrudRepository<DAO,Long>, M extends Mapper<DAO,DTO>>  {

    @Autowired
    R repo;

    @Autowired
    M mapper;

    public DTO findOne(Long id) {
        DAO dao = repo.findOne(id);
        if(dao != null) {
            return mapper.convertToDTO(dao);
        }
        return null;
    }

    public Iterable<DTO> findAll() {
        return mapper.convertToDTO(repo.findAll());
    }

    public void save(DTO dto) {
        repo.save(mapper.convertToDAO(dto));
    }

    public void save(Iterable<DTO> dtos) {
        repo.save(mapper.convertToDAO(dtos));
    }

    public void delete(DTO dto) {
        repo.delete(mapper.convertToDAO(dto));
    }

    public void delete(Iterable<DTO> dtos) {
        repo.delete(mapper.convertToDAO(dtos));
    }

    public M getMapper() {
        return mapper;
    }

}
