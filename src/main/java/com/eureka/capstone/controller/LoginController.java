package com.eureka.capstone.controller;

import com.eureka.capstone.service.user.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/*")
    public String landing(Principal principal) {
        if (principal != null) {
            return "redirect:/products";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Principal principal) {
        if (principal != null) {
            return "redirect:/products";
        }

        Cookie rememberMeCookie = WebUtils.getCookie(request, "remember-me");

        return (rememberMeCookie != null) ? "redirect:/products" : Templates.LOGIN.getName();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            var username = auth.getName();
            var user = userService.getUserByUsername(username);

            if (user.getToken() != null) {
                user.setToken(null);
                userService.save(user);
            }

            var rememberMe = WebUtils.getCookie(request, "remember-me");
            deleteCookieIfExists(rememberMe, response);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }

    private void deleteCookieIfExists(Cookie cookie, HttpServletResponse response) {
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

}
