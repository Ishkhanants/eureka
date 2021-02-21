package com.eureka.capstone.controller;

import com.eureka.capstone.cookies.RememberMeCookieService;
import com.eureka.capstone.domain.User;
import com.eureka.capstone.dto.UserDto;
import com.eureka.capstone.exception.notunique.FieldsAlreadyExistException;
import com.eureka.capstone.mapping.UserMapperDecorator;
import com.eureka.capstone.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

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

    @PostMapping("/register")
    public ModelAndView createUser(@ModelAttribute("user") @Validated UserDto userDto,
                                     BindingResult result,
                                     ModelAndView modelAndView,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
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

        try {
            request.login(userDto.getUsername(), userDto.getPassword());
            attachCookieToResponse(user, response);
        } catch (ServletException e) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/products");

        return modelAndView;
    }

    private void attachCookieToResponse(User user, HttpServletResponse response) {
        Cookie rememberMeCookie = rememberMeCookieService.getCookie(user);
        response.addCookie(rememberMeCookie);
    }

//    @GetMapping("/")
//    public ModelAndView productsPage(ModelAndView modelAndView, Principal principal) {
//        if (principal != null) {
//            return new ModelAndView("redirect:/products");
//        }
//
//        modelAndView.setViewName(Templates.USERS.getName());
//        modelAndView.addObject("user", new UserDto());
//        modelAndView.setStatus(HttpStatus.OK);
//
//        return modelAndView;
//    }

    @GetMapping
    public ModelAndView viewPage(ModelAndView modelAndView) {
        List<User> listEmployees = userService.getAllUsers();
        modelAndView.setViewName(Templates.USERS.getName());
        modelAndView.addObject("listUsers", listEmployees);
        return modelAndView;
    }

    @GetMapping("/{username}")
    public ModelAndView findByUserName(ModelAndView modelAndView, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        List userList = new ArrayList();
        userList.add(user);
        modelAndView.setViewName(Templates.USERS.getName());
        modelAndView.addObject("listUsers", userList);
        return modelAndView;
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
