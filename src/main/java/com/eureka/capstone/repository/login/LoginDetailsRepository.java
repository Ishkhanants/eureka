package com.eureka.capstone.repository.login;

import com.eureka.capstone.domain.login.LoginDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDetailsRepository extends JpaRepository<LoginDetails, Long> {}
