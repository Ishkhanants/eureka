package com.eureka.capstone.controller;

import com.eureka.capstone.security.UserDetailsServiceImpl;
import com.eureka.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LandingController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserService userService;

    @GetMapping("/*")
    public String landing(Principal principal) {
        if (principal != null)
            return "redirect:/products";
        return "login";
    }

}
