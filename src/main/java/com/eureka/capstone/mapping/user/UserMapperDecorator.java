package com.eureka.capstone.mapping.user;

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
        var user = userMapper.toEntity(dto);
        var role = roleService.getRole(RoleEnum.USER_ROLE);

        user.addRole(role);
        user.setProfileAvatar(dto.getProfileAvatar());

        return user;
    }

}
