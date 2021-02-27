package com.eureka.capstone.controller;

import com.eureka.capstone.domain.Product;
import com.eureka.capstone.security.UserDetailsServiceImpl;
import com.eureka.capstone.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
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

//    @GetMapping("/create")
//    public ModelAndView createProduct(ModelAndView modelAndView){
//        modelAndView.addObject("product", new Product());
//        return modelAndView;
//    }

    @PostMapping("/create")
    public String addProduct(@ModelAttribute("product") Product product){
        productService.createProduct(product);
        return "redirect:/products";
    }

//    @PutMapping("/edit/{id}")
//    public String editProduct(@PathVariable("id") Long id){
//
//        return "redirect:/products";
//    }

    @GetMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        try {
            System.out.println("GOOD");
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("BAD");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/edit")
//    public String editProduct(@ModelAttribute("product") Product updatedProduct){
//        productService.updateProduct(updatedProduct);
//        return "redirect:/products";
//    }

    @PostMapping("/edit")
    public String editProduct(HttpServletRequest request){
        var updatedProduct = productService.extractProductFromRequest(request);
        productService.updateProduct(updatedProduct);
        return "redirect:/products";
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request){
        long id = Long.parseLong(request.getParameter("id"));
        productService.deleteProductById(id);
        return "redirect:/products";
    }

//    @DeleteMapping("/delete/{id}")
//    public String deleteProduct(@PathVariable("id") String id){
//        long idd = Long.parseLong(id);
//        productService.deleteProductById(idd);
//        return "redirect:/products";
//    }
}
