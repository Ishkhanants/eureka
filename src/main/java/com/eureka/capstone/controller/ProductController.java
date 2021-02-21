package com.eureka.capstone.controller;

import com.eureka.capstone.domain.Product;
import com.eureka.capstone.security.UserDetailsServiceImpl;
import com.eureka.capstone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private final ProductService productService;

    @GetMapping
    public ModelAndView success(Model model, Principal principal, HttpSession session, ModelAndView modelAndView) {
        model.addAttribute("currentUser", principal.getName());
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
//        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(RoleEnum.ADMIN_ROLE.name())))
//            return Templates.HOMEPAGE_ADMIN.getName();
        List<Product> listProducts = productService.getAllProducts();
        modelAndView.setViewName(Templates.PRODUCTS.getName());
        modelAndView.addObject("listProducts", listProducts);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProduct(){
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity addProduct(){
        return null;
    }
}
