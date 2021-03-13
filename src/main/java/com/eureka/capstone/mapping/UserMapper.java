package com.eureka.capstone.mapping;

import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.dto.UserDto;
import com.eureka.capstone.service.RoleService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@DecoratedWith(com.eureka.capstone.mapping.UserMapperDecorator.class)
public abstract class UserMapper {

    @Autowired
    RoleService roleService;

    @Mapping(source = "fullName", target = "fullName")
    abstract User toEntity(UserDto dto);

//    @Mapping(source = "fullName", target = "fullName")
//    abstract UserDto toDto(User user);

}
