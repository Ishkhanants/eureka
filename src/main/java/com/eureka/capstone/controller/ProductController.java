package com.eureka.capstone.controller;

import com.eureka.capstone.domain.Product;
import com.eureka.capstone.security.UserDetailsServiceImpl;
import com.eureka.capstone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
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
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public String addProduct(@ModelAttribute("product") Product product){
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public String editProduct(HttpServletRequest request){
        var updatedProduct = productService.extractProductFromRequest(request);
        productService.updateProduct(updatedProduct);
        return "redirect:/products";
    }

    @PostMapping("/delete")
    public String deleteProduct(HttpServletRequest request){
        long id = Long.parseLong(request.getParameter("id"));
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @PostMapping(value = "/delete-selected")
    public String deleteSelectedProducts(HttpServletRequest request) {
        var ids = request.getParameter("ids").split(",");

        var i = 0;
        for (String id: ids) {
            long idl = Long.parseLong(id);
            productService.deleteProductById(idl);
        }

        return "redirect:/products";
    }
}
