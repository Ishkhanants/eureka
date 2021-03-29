package com.eureka.capstone.dto;

import com.eureka.capstone.domain.user.Group;
import com.eureka.capstone.domain.user.UserType;
import com.eureka.capstone.validation.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.context.annotation.PropertySource;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MatchingPassword
@PropertySource("classpath:validation.properties")
public class UserDto {

    @NotNull(message = "not.null.username")
    @ValidUsername
    private String username;

    @NotNull(message = "not.null.password")
    @ValidPassword
    private String password;

    @NotNull(message = "not.null.confirm.password")
    private String confirmPassword;

    @NotNull
    private UserType userType;

    @NotNull
    private Group group;

    @NotNull(message = "not.null.full.name")
    @ValidFullName
    private String fullName;

    @NotNull(message = "not.null.email")
    @ValidEmail
    private String email;

    @NotNull(message = "not.null.phone")
    @ValidCellPhone
    private String phone;

    @Lob
    private byte[] profileAvatar;

}

