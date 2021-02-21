package com.eureka.capstone.mapping;

import com.eureka.capstone.domain.Role;
import com.eureka.capstone.domain.RoleEnum;
import com.eureka.capstone.domain.User;
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
}
