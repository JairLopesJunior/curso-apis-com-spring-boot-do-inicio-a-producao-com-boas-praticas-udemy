package com.jairlopes.bookstoremanager.mapper;

import com.jairlopes.bookstoremanager.dto.UserDTO;
import com.jairlopes.bookstoremanager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO userDTO);

    UserDTO toDTO(User user);
}
