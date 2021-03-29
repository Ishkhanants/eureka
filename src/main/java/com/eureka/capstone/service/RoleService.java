package com.eureka.capstone.service;

import com.eureka.capstone.domain.user.Role;
import com.eureka.capstone.domain.user.RoleEnum;

public interface RoleService {

    Role getRole(RoleEnum roleName);

}