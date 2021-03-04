package com.eureka.capstone.domain;

import com.eureka.capstone.domain.user.Role;
import com.eureka.capstone.domain.user.RoleEnum;
import com.eureka.capstone.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    User user = new User();
    Role role1 = new Role(RoleEnum.USER_ROLE);
    Role role2 = new Role(RoleEnum.ADMIN_ROLE);

    @Test
    void addRole() {
        user.addRole(role1);
        assertTrue(user.getRoles().contains(role1));
        assertFalse(user.getRoles().contains(role2));
    }
}