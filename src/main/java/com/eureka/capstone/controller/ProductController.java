package com.eureka.capstone.controller;

import com.eureka.capstone.domain.product.Product;
import com.eureka.capstone.domain.product.ReleaseVersion;
import com.eureka.capstone.domain.product.SubSystem;
import com.eureka.capstone.domain.user.Group;
import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.domain.user.UserType;
import com.eureka.capstone.security.UserDetailsServiceImpl;
import com.eureka.capstone.service.ProductService;
import com.eureka.capstone.service.ReleaseVersionService;
import com.eureka.capstone.service.SubsystemService;
import com.eureka.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;
    private final ProductService productService;
    private final SubsystemService subsystemService;
    private final ReleaseVersionService releaseVersionService;

    @GetMapping
    public ModelAndView success(ModelAndView modelAndView) {
        List<Product> listProducts = productService.getAllProducts();
        var owners = userService.getAllUsers().stream()
                .filter(u -> u.getUserType().equals(UserType.MANAGER))
                .collect(Collectors.toList());
        modelAndView.setViewName(Templates.PRODUCTS.getName());
        modelAndView.addObject("listProducts", listProducts);
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("owners", owners);
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
        var owner = userService.getUserById(Long.parseLong(request.getParameter("edit-owner")));
        updatedProduct.setOwner(owner);
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

        for (String id: ids) {
            long idl = Long.parseLong(id);
            productService.deleteProductById(idl);
        }

        return "redirect:/products";
    }

    @GetMapping("/{id}/release-versions")
    public ModelAndView getReleaseVersions(@PathVariable("id") long id, ModelAndView modelAndView){
        var listReleaseVersions = releaseVersionService.getReleaseVersionsByProductId(id);
        var releaseVersion = new ReleaseVersion();
        releaseVersion.setProduct(productService.getProductById(id));
        modelAndView.setViewName(Templates.RELEASE_VERSIONS.getName());
        modelAndView.addObject("listReleaseVersions", listReleaseVersions);
        modelAndView.addObject("releaseVersion", releaseVersion);
        return modelAndView;
    }

    @PostMapping("/release-versions/create")
    public String addReleaseVersion(@ModelAttribute("releaseVersion") ReleaseVersion releaseVersion, HttpServletRequest request){
        var productId = Long.parseLong(request.getParameter("product-id-to-add"));
        releaseVersion.setProduct(productService.getProductById(productId));
        releaseVersionService.createReleaseVersion(releaseVersion);
        return "redirect:/products/" + productId + "/release-versions";
    }

    @GetMapping(value = "/release-versions/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReleaseVersion> getReleaseVersionById(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(releaseVersionService.getReleaseVersionById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/release-versions-by-product/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReleaseVersion>> getReleaseVersionsByProductId(@PathVariable("id") long id){
        try {
            return new ResponseEntity<>(releaseVersionService.getReleaseVersionsByProductId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/release-versions/edit")
    public String editReleaseVersion(HttpServletRequest request){
        var productId = Long.parseLong(request.getParameter("product-id-to-edit"));
        var updatedReleaseVersion = releaseVersionService.extractReleaseFromRequest(request);
        updatedReleaseVersion.setProduct(productService.getProductById(productId));
        releaseVersionService.updateReleaseVersion(updatedReleaseVersion);
        return "redirect:/products/" + productId + "/release-versions";
    }

    @PostMapping("/release-versions/delete")
    public String deleteReleaseVersion(HttpServletRequest request){
        var productId = Long.parseLong(request.getParameter("product-id-to-delete"));
        var id = Long.parseLong(request.getParameter("id"));
        releaseVersionService.deleteReleaseVersionById(id);
        return "redirect:/products/" + productId + "/release-versions";
    }

    @PostMapping("/release-versions/delete-selected")
    public String deleteSelectedReleaseVersions(HttpServletRequest request) {
        var productId = Long.parseLong(request.getParameter("product-ids-to-delete"));
        var ids = request.getParameter("ids").split(",");

        for (String id: ids) {
            long idl = Long.parseLong(id);
            releaseVersionService.deleteReleaseVersionById(idl);
        }

        return "redirect:/products/" + productId + "/release-versions";
    }

    @GetMapping("/{id}/subsystems")
    public ModelAndView getSubsystems(@PathVariable("id") long id, ModelAndView modelAndView){
        var subSystems = subsystemService.getSubsystemsByProductId(id);
        var subSystem = new SubSystem();
        subSystem.setProduct(productService.getProductById(id));
        modelAndView.setViewName(Templates.SUBSYSTEMS.getName());
        modelAndView.addObject("listSubSystems", subSystems);
        modelAndView.addObject("subSystem", subSystem);
        return modelAndView;
    }

    @PostMapping("/subsystems/create")
    public String addSubsystem(@ModelAttribute("subSystem") SubSystem subSystem, HttpServletRequest request){
        var productId = Long.parseLong(request.getParameter("product-id-to-add"));
        subSystem.setProduct(productService.getProductById(productId));
        subsystemService.createSubSystem(subSystem);
        return "redirect:/products/" + productId + "/subsystems";
    }

    @GetMapping(value = "/subsystems/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubSystem> getSubsystemById(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(subsystemService.getSubSystemById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/subsystems-by-product/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubSystem>> getSubsystemsByProductId(@PathVariable("id") long id){
        try {
            return new ResponseEntity<>(subsystemService.getSubsystemsByProductId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/subsystems/edit")
    public String editSubsystem(HttpServletRequest request){
        var productId = Long.parseLong(request.getParameter("product-id-to-edit"));
        var updatedSubSystem = subsystemService.extractSubSystemFromRequest(request);
        updatedSubSystem.setProduct(productService.getProductById(productId));
        subsystemService.updateSubSystem(updatedSubSystem);
        return "redirect:/products/" + productId + "/subsystems";
    }

    @PostMapping("/subsystems/delete")
    public String deleteSubsystem(HttpServletRequest request){
        var productId = Long.parseLong(request.getParameter("product-id-to-delete"));
        var id = Long.parseLong(request.getParameter("id"));
        subsystemService.deleteSubSystemById(id);
        return "redirect:/products/" + productId + "/subsystems";
    }

    @PostMapping("/subsystems/delete-selected")
    public String deleteSelectedSubsystems(HttpServletRequest request) {
        var productId = Long.parseLong(request.getParameter("product-ids-to-delete"));
        var ids = request.getParameter("ids").split(",");

        for (String id: ids) {
            long idl = Long.parseLong(id);
            subsystemService.deleteSubSystemById(idl);
        }

        return "redirect:/products/" + productId + "/subsystems";
    }


    @GetMapping(value = "/owner-by-product/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getOwnerByProductId(@PathVariable("id") long id){
        try {
            return new ResponseEntity<>(productService.getOwnerByProductId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
