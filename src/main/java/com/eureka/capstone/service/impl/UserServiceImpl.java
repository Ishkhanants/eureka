package com.eureka.capstone.service.impl;

import com.eureka.capstone.domain.Product;
import com.eureka.capstone.domain.user.Group;
import com.eureka.capstone.domain.user.RoleEnum;
import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.domain.user.UserType;
import com.eureka.capstone.exception.notfound.UserNotFoundException;
import com.eureka.capstone.exception.notunique.FieldsAlreadyExistException;
import com.eureka.capstone.repository.UserRepository;
import com.eureka.capstone.security.UserDetailsImpl;
import com.eureka.capstone.service.RoleService;
import com.eureka.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleService roleService;

    @Qualifier("sessionRegistry")
    private final SessionRegistry sessionRegistry;
    private PasswordEncoder encoder;

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public User createUser(User user) {
        boolean usernameExists = repository.existsByUsername(user.getUsername());
        boolean emailExists = repository.existsByEmail(user.getEmail());
        if (usernameExists || emailExists) {
            Map<String, Boolean> fieldErrors = new HashMap<>();
            fieldErrors.put("username", usernameExists);
            fieldErrors.put("email", emailExists);
            throw new FieldsAlreadyExistException(fieldErrors);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public User getUserById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id));
    }

    @Override
    public User getUserByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException(username));
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(email));
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public List<User> getAllAdmins() {
        return repository.findAll().stream().filter(User::isAdmin).collect(Collectors.toList());
    }

    @Override
    public String extractAvatarPicture(User user) {
        if (user.getProfileAvatar() == null) return null;
        byte[] encode = Base64.getEncoder().encode(user.getProfileAvatar());
        return new String(encode, StandardCharsets.UTF_8);
    }


    @Override
    public void updateUser(User user, MultipartHttpServletRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> possibleUser = repository.findByUsername(user.getUsername());
        if (possibleUser.isPresent()) {
            User user1 = possibleUser.get();
            user1.setFullName(user.getFullName());
            user1.setPhone(user.getPhone());
            if (user.getPassword().length() != 0) user1.setPassword(encoder.encode(user.getPassword()));
            if (user.getProfileAvatar() != null) user1.setProfileAvatar(user.getProfileAvatar());
            else if (request.getParameter("deletedAvatar").equals("deleted")) {
                user1.setProfileAvatar(null);
            }
            repository.save(user1);
        }
    }

    @Override
    public User extractUserFromRequest(HttpServletRequest request) {
        var updatedUser = new User();
        updatedUser.setId(Long.parseLong(request.getParameter("id")));
        updatedUser.setUsername(request.getParameter("edit-username"));
        updatedUser.setFullName(request.getParameter("edit-fullName"));
        updatedUser.setPhone(request.getParameter("edit-phone"));
        updatedUser.setGroup(Group.valueOf(request.getParameter("edit-group")));
        updatedUser.setUserType(UserType.valueOf(request.getParameter("edit-userType")));
        updatedUser.setEmail(request.getParameter("edit-email"));
        return updatedUser;
    }

    @Override
    public void deleteUserByUsername(String userName) {
        repository.deleteByUsername(userName);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void toUser(Long id) {
        User user = getUserById(id);
        user.setRoles(user.getRoles().stream()
                .filter(r -> !(r.getRoleName().name().equals("ADMIN_ROLE")) && !(r.getRoleName().name().equals("USER_ROLE")))
                .collect(Collectors.toList()));
        user.addRole(roleService.getRole(RoleEnum.USER_ROLE));
        repository.save(user);
        logoutUser(id);
    }

    @Override
    public void toAdmin(Long id) {
        User user = getUserById(id);
        user.setRoles(user.getRoles().stream()
                .filter(r -> !(r.getRoleName().name().equals("ADMIN_ROLE")) && !(r.getRoleName().name().equals("USER_ROLE")))
                .collect(Collectors.toList()));
        user.addRole(roleService.getRole(RoleEnum.ADMIN_ROLE));
        repository.save(user);
        logoutUser(id);
    }

    @Override
    public void updateUser2(User updatedUser) {
        var user = getUserById(updatedUser.getId());

        user.setUsername(updatedUser.getUsername());
        user.setFullName(updatedUser.getFullName());
        user.setPhone(updatedUser.getPhone());
        user.setEmail(updatedUser.getEmail());
        user.setGroup(updatedUser.getGroup());
        user.setUserType(updatedUser.getUserType());

        repository.save(user);
    }

    @Override
    public void deleteUserById(long id) {
        repository.deleteById(id);
    }


    public void logoutUser(Long id) {
        User user = getUserById(id);
        UserDetails userD = new UserDetailsImpl(user);

        List<Object> principals = sessionRegistry.getAllPrincipals();

        for (Object principal : principals) {
            if (principal.equals(userD)) {
                List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
                for (SessionInformation sessionInformation : sessionInformations) {
                    sessionInformation.expireNow();
                }
            }
        }
    }

}

