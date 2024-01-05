package com.Microblogs.mapper;

import com.Microblogs.controller.User.UserDto;
import com.Microblogs.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "password", source = "dto.password")
    User userDTOtoUser(UserDto dto);

    @Mapping(target = "username", source = "entity.username")
    @Mapping(target = "email", source = "entity.email")
    @Mapping(target = "password", source = "entity.password")
    UserDto userToUserDTO(User entity);
}
