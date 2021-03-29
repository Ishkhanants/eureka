package com.eureka.capstone.repository;

import com.eureka.capstone.domain.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Override
    <S extends User> S save(S s);

    Optional<User> findByEmailEndingWith(String email);

    void deleteByUsername(String userName);

}