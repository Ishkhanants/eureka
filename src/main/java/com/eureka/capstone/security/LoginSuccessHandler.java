package com.eureka.capstone.security;

import com.eureka.capstone.domain.login.LoginDetails;
import com.eureka.capstone.domain.login.LoginFormatter;
import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.service.login.LoginDetailsService;

import com.eureka.capstone.service.user.UserService;
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
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        var userDetails = (UserDetails) authentication.getPrincipal();
        var user = userService.getUserByUsername(userDetails.getUsername());

        saveLoginDetails(httpServletRequest, user);
    }

    private void saveLoginDetails(HttpServletRequest request, User user){
        var details = new LoginDetails(user, request.getRemoteAddr(), LocalDateTime.now());

        loginDetailsService.save(details);
        logLoginDetails(details);
    }

    private void logLoginDetails(LoginDetails details){
        try {
            var fileHandler = new FileHandler("sign-in.log", true);

            fileHandler.setFormatter(new LoginFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.info(String.format("Login #%d with following credentials\nUsername: %s\nIP Address: %s\nDatetime: %s\n",
                    details.getId(), details.getUser().getUsername(), details.getIp(), details.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            fileHandler.close();

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
