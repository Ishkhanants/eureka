package com.eureka.capstone.mapping;

import com.eureka.capstone.domain.user.Role;
import com.eureka.capstone.domain.user.RoleEnum;
import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.dto.UserDto;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class UserMapperDecorator extends UserMapper {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = userMapper.toEntity(dto);
        Role role = roleService.getRole(RoleEnum.USER_ROLE);

        user.addRole(role);
        user.setProfileAvatar(dto.getProfileAvatar());

        return user;
    }

//    @Override
//    public UserDto toDto(User user){
//        UserDto userDto = userMapper.toDto(user);
//        Role role = roleService.getRole(RoleEnum.USER_ROLE);
//
//        userDto.
//    }
}
