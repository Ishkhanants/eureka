package com.eureka.capstone.exception.notfound;


import com.eureka.capstone.domain.user.RoleEnum;

public class RoleNotFoundException extends NotFoundException {
    public RoleNotFoundException() {
        super("Role not found!");
    }

    public RoleNotFoundException(RoleEnum roleName) {
        super(String.format("Role not found: %s", roleName.toString()));
    }
}
