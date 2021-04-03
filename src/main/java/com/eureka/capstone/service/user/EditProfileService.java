package com.eureka.capstone.service.user;

import com.eureka.capstone.domain.user.User;
import com.eureka.capstone.dto.UserDto;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface EditProfileService {

    ModelAndView getModelWithUserAttributes(User user);

    UserDto extractUserDtoFromRequest(MultipartHttpServletRequest request);

}
