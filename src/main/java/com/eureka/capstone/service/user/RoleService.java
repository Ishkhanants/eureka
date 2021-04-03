package com.eureka.capstone.service.user;

import com.eureka.capstone.domain.user.Role;
import com.eureka.capstone.domain.user.RoleEnum;

public interface RoleService {

    Role getRole(RoleEnum roleName);

}