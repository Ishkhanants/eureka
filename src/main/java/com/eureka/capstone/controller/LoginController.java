package com.eureka.capstone.controller;

import com.eureka.capstone.domain.login.LoginDetails;
import com.eureka.capstone.domain.login.LoginFormatter;
import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.service.LoginDetailsService;
import com.eureka.capstone.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final LoginDetailsService loginDetailsService;
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @GetMapping("/*")
    public String landing(HttpServletRequest request, Principal principal) {
        if (principal != null) {
            saveLoginDetails(request, principal);
            return "redirect:/products";
        }

        return "login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Principal principal) {
        if (principal != null) {
//            saveLoginDetails(request, principal);
            return "redirect:/products";
        }

        Cookie rememberMeCookie = WebUtils.getCookie(request, "remember-me");

        return (rememberMeCookie != null) ? "redirect:/products" : Templates.LOGIN.getName();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            String username = auth.getName();
            User user = userService.getUserByUsername(username);

            if (user.getToken() != null) {
                user.setToken(null);
                userService.save(user);
            }

            Cookie rememberMe = WebUtils.getCookie(request, "remember-me");
            deleteCookieIfExists(rememberMe, response);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }

    private void saveLoginDetails(HttpServletRequest request, Principal principal){
        var details = new LoginDetails(principal.getName(), request.getRemoteAddr(), LocalDateTime.now());
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

    private void deleteCookieIfExists(Cookie cookie, HttpServletResponse response) {
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

}
