package com.eureka.capstone.service.impl;

import com.eureka.capstone.controller.Templates;
import com.eureka.capstone.domain.user.Group;
import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.domain.user.UserType;
import com.eureka.capstone.dto.UserDto;
import com.eureka.capstone.service.EditProfileService;
import com.eureka.capstone.service.UserService;
import com.eureka.capstone.util.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class EditProfileServiceImpl implements EditProfileService {

    private final UserService userService;

    @Override
    public ModelAndView getModelWithUserAttributes(User user) {
        ModelAndView model = new ModelAndView(Templates.EDIT_PROFILE.getName());

        model.addObject("profileAvatar", userService.extractAvatarPicture(user));
        model.addObject("user", user);

        return model;
    }

    @Override
    public UserDto extractUserDtoFromRequest(MultipartHttpServletRequest request) {
        UserDto userDto = new UserDto();
        userDto.setFullName(request.getParameter("fullName"));
        userDto.setEmail(request.getParameter("email-editable"));
        userDto.setUsername(request.getParameter("userName"));
        userDto.setPhone(request.getParameter("phone"));
        userDto.setPassword(request.getParameter("password"));
        userDto.setConfirmPassword(request.getParameter("confirmPassword"));
        userDto.setGroup(Group.valueOf(request.getParameter("group")));
        userDto.setUserType(UserType.valueOf(request.getParameter("userType")));
        try {
            MultipartFile profileFile = request.getFile("profileAvatar");
            assert profileFile != null;
            if (profileFile.getBytes().length != 0)
                userDto.setProfileAvatar(ImageService.compress(ImageService.extractImageFromFile(profileFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDto;
    }
}
