package com.eureka.capstone.controller;

import com.eureka.capstone.cookies.RememberMeCookieService;
import com.eureka.capstone.domain.Product;
import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.dto.UserDto;
import com.eureka.capstone.exception.notunique.FieldsAlreadyExistException;
import com.eureka.capstone.mapping.UserMapperDecorator;
import com.eureka.capstone.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@PropertySource("classpath:values.properties")
public class UserController {

    private final UserService userService;
    private final UserMapperDecorator userMapperDecorator;
    private final RememberMeCookieService rememberMeCookieService;
    private final MessageSource messageSource;

    @Value("${security.secret.key}")
    private String SECRET_KEY;

    @GetMapping
    public ModelAndView viewPage(ModelAndView modelAndView) {
        List<User> listUsers = userService.getAllUsers();
        modelAndView.setViewName(Templates.USERS.getName());
        modelAndView.addObject("listUsers", listUsers);
        modelAndView.addObject("user", new UserDto());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createUser(@ModelAttribute("user") @Validated UserDto userDto,
                                     BindingResult result,
                                     ModelAndView modelAndView,
                                     Locale locale) {
        if (result.hasErrors()) {
            modelAndView.setViewName(Templates.USERS.getName());
            modelAndView.addObject("fieldErrors", result.getFieldErrors());
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }

        User user = userMapperDecorator.toEntity(userDto);

        try {
            userService.createUser(user);
        } catch (FieldsAlreadyExistException e) {
            modelAndView.setViewName(Templates.USERS.getName());

            final Map<String, Boolean> fieldsErrors = e.getFieldsErrors();
            String usernameMessage = messageSource.getMessage("valid.userDto.userName.unique.message", new Object[]{}, locale);
            String emailMessage = messageSource.getMessage("valid.userDto.email.unique.message", new Object[]{}, locale);

            if (fieldsErrors.get("username")) modelAndView.addObject("usernameError", usernameMessage);
            if (fieldsErrors.get("email")) modelAndView.addObject("emailError", emailMessage);

            modelAndView.setStatus(HttpStatus.BAD_REQUEST);

            return modelAndView;
        }

        modelAndView.setViewName("redirect:/users");

        return modelAndView;
    }

    @GetMapping("/{username}")
    public ModelAndView findByUserName(ModelAndView modelAndView, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        modelAndView.setViewName(Templates.USERS.getName());
        modelAndView.addObject("listUsers", userList);
        return modelAndView;
    }

    @GetMapping(value = "/edit/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public String editUser(HttpServletRequest request){
        var updatedUser = userService.extractUserFromRequest(request);
        userService.updateUser2(updatedUser);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(HttpServletRequest request){
        long id = Long.parseLong(request.getParameter("id"));
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @PostMapping(value = "/delete-selected")
    public String deleteSelectedUsers(HttpServletRequest request) {
        var ids = request.getParameter("ids").split(",");

        for (String id: ids) {
            long idl = Long.parseLong(id);
            userService.deleteUserById(idl);
        }

        return "redirect:/users";
    }

    @GetMapping("/toUser/{id}")
    public ModelAndView toUser(@PathVariable Long id, ModelAndView modelAndView) {
        userService.toUser(id);
        return viewPage(modelAndView);
    }

    @GetMapping("/toAdmin/{id}")
    public ModelAndView toAdmin(@PathVariable Long id, ModelAndView modelAndView) {
        userService.toAdmin(id);
        return viewPage(modelAndView);
    }
}
