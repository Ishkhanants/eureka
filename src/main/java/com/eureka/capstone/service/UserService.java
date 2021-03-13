package com.eureka.capstone.service;

import com.eureka.capstone.domain.user.User;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getAllAdmins();

    String extractAvatarPicture(User user);

    void updateUser(User user, MultipartHttpServletRequest request);

    User extractUserFromRequest(HttpServletRequest request);

    void deleteUserByUsername(String userName);

    User save(User user);

    void toUser(Long id);

    void toAdmin(Long id);

    void updateUser2(User user);

    void deleteUserById(long id);

}
