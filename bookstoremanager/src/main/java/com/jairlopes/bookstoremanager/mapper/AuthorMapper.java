package com.jairlopes.bookstoremanager.mapper;

import com.jairlopes.bookstoremanager.dto.AuthorDTO;
import com.jairlopes.bookstoremanager.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModelo(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}
