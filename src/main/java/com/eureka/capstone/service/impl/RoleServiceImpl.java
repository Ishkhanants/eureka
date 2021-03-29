package com.eureka.capstone.service.impl;

import com.eureka.capstone.domain.user.Role;
import com.eureka.capstone.domain.user.RoleEnum;
import com.eureka.capstone.exception.notfound.RoleNotFoundException;
import com.eureka.capstone.repository.RoleRepository;
import com.eureka.capstone.service.RoleService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role getRole(RoleEnum roleName) {
        return repository.getByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
    }
}
