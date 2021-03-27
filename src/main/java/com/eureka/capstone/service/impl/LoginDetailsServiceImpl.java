package com.eureka.capstone.service.impl;

import com.eureka.capstone.domain.login.LoginDetails;
import com.eureka.capstone.repository.LoginDetailsRepository;
import com.eureka.capstone.service.LoginDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginDetailsServiceImpl implements LoginDetailsService {

    private final LoginDetailsRepository loginDetailsRepository;

    @Override
    public LoginDetails save(LoginDetails loginDetails) {
        return loginDetailsRepository.save(loginDetails);
    }

}
