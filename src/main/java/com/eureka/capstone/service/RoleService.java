package com.eureka.capstone.service;

import com.eureka.capstone.domain.Role;
import com.eureka.capstone.domain.RoleEnum;

public interface RoleService {
    Role getRole(RoleEnum roleName);
}