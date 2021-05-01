package com.eureka.capstone.controller;

import com.eureka.capstone.domain.user.Group;
import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.dto.UserDto;
import com.eureka.capstone.exception.notunique.FieldsAlreadyExistException;
import com.eureka.capstone.mapping.user.UserMapperDecorator;
import com.eureka.capstone.service.user.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapperDecorator userMapperDecorator;
    private final MessageSource messageSource;

    @GetMapping
    public ModelAndView viewPage(ModelAndView modelAndView) {
        var listUsers = userService.getAllUsers();

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

        var user = userMapperDecorator.toEntity(userDto);

        try {
            userService.createUser(user);
        } catch (FieldsAlreadyExistException e) {
            modelAndView.setViewName(Templates.USERS.getName());

            final var fieldsErrors = e.getFieldsErrors();
            var usernameMessage = messageSource.getMessage("valid.userDto.userName.unique.message", new Object[]{}, locale);
            var emailMessage = messageSource.getMessage("valid.userDto.email.unique.message", new Object[]{}, locale);

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
        var user = userService.getUserByUsername(username);
        var userList = new ArrayList<>();

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
    public String editUser(HttpServletRequest request) {
        var updatedUser = userService.extractUserFromRequest(request);

        userService.updateUserByAdmin(updatedUser);

        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));

        userService.deleteUserById(id);

        return "redirect:/users";
    }

    @PostMapping(value = "/delete-selected")
    public String deleteSelectedUsers(HttpServletRequest request) {
        var ids = request.getParameter("ids").split(",");

        for (String id : ids) {
            var idl = Long.parseLong(id);
            userService.deleteUserById(idl);
        }

        return "redirect:/users";
    }

    @GetMapping("/toUser/{id}")
    public String toUser(@PathVariable Long id) {
        userService.toUser(id);

        return "redirect:/users";
    }

    @GetMapping("/toAdmin/{id}")
    public String toAdmin(@PathVariable Long id) {
        userService.toAdmin(id);

        return "redirect:/users";
    }

    @GetMapping("/group-by-type/{type}")
    public ResponseEntity<List<Group>> getUserById(@PathVariable("type") String type) {
        try {
            var groupList = new ArrayList<Group>();

            switch (type) {
                case "USER":
                    groupList.add(Group.READ_ONLY_ACCESS);
                    break;
                case "DEVELOPER":
                    groupList.addAll(Arrays.asList(
                            Group.ADMINS_DEVELOPMENT,
                            Group.DEVELOPERS,
                            Group.SECURITY_ADMINS));
                    break;
                case "TESTER":
                    groupList.addAll(Arrays.asList(
                            Group.ADMINS_DEVELOPMENT,
                            Group.TESTERS,
                            Group.SECURITY_ADMINS));
                    break;
                case "MANAGER":
                    groupList.addAll(Arrays.asList(
                            Group.READ_ONLY_ACCESS,
                            Group.ADMINS_MANAGEMENT,
                            Group.SECURITY_ADMINS));
                    break;
            }

            return new ResponseEntity<>(groupList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
