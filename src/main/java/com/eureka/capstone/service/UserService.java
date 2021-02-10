package com.eureka.capstone.service;

import com.eureka.capstone.domain.User;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    void deleteUserByUsername(String userName);

    User save(User user);

    void toUser(Long id);

    void toAdmin(Long id);
}
