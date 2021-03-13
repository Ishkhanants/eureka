package com.eureka.capstone.dto;

import com.eureka.capstone.domain.user.Group;
import com.eureka.capstone.domain.user.UserType;
import com.eureka.capstone.validation.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@MatchingPassword
@PropertySource("classpath:ValidationMessages.properties")
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getProfileAvatar() {
        return profileAvatar;
    }

    public void setProfileAvatar(byte[] profileAvatar) {
        this.profileAvatar = profileAvatar;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

