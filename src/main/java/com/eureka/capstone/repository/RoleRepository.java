package com.eureka.capstone.repository;

import com.eureka.capstone.domain.Role;
import com.eureka.capstone.domain.RoleEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getByRoleName(RoleEnum roleEnum);
}
