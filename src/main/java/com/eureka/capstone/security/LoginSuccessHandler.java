package com.eureka.capstone.security;

import com.eureka.capstone.domain.login.LoginDetails;
import com.eureka.capstone.domain.login.LoginFormatter;
import com.eureka.capstone.service.login.LoginDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@Component("customSuccessHandler")
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOGGER = Logger.getLogger(LoginSuccessHandler.class.getName());
    private final LoginDetailsService loginDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        var userDetails = (UserDetails) authentication.getPrincipal();
        var username = userDetails.getUsername();

        saveLoginDetails(httpServletRequest, username);
    }

    private void saveLoginDetails(HttpServletRequest request, String username){
        var details = new LoginDetails(username, request.getRemoteAddr(), LocalDateTime.now());

        loginDetailsService.save(details);
        logLoginDetails(details);
    }

    private void logLoginDetails(LoginDetails details){
        try {
            var fileHandler = new FileHandler("sign-in.log", true);

            fileHandler.setFormatter(new LoginFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.info(String.format("Login #%d with following credentials\nUsername: %s\nIP Address: %s\nDatetime: %s\n",
                    details.getId(), details.getUsername(), details.getIp(), details.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            fileHandler.close();

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
