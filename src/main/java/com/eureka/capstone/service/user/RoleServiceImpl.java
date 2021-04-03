package com.eureka.capstone.service.user;

import com.eureka.capstone.domain.user.Role;
import com.eureka.capstone.domain.user.RoleEnum;
import com.eureka.capstone.exception.notfound.RoleNotFoundException;
import com.eureka.capstone.repository.user.RoleRepository;

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
