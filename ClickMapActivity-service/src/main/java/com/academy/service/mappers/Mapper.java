package com.academy.service.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Daniel Palonek on 2016-08-18.
 */
public interface Mapper<DAO,DTO> {

    DAO convertToDAO(DTO dto);

    DTO convertToDTO(DAO dao);

    default Iterable<DAO> convertToDAO(Iterable<DTO> dto) {
        return Stream.of(dto).map(m -> (DAO)convertToDAO(m)).collect(Collectors.toList());
    }

    default Iterable<DTO> convertToDTO(Iterable<DAO> dao) {
        Collection<DTO> dtos = new ArrayList<>();
        dao.forEach(d -> dtos.add(convertToDTO(d)));
        return dtos;
    }
}
