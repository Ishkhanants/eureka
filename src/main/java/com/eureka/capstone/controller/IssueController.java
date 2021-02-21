package com.eureka.capstone.controller;

import com.eureka.capstone.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

public class IssueController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping("/myIssues")
    public String myIssues(Model model, Principal principal) {
        model.addAttribute("currentUser", principal.getName());
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.ADMIN_ROLE.name())))
//            return Templates.HOMEPAGE_ADMIN.getName();
        return Templates.PRODUCTS.getName();
    }
}
