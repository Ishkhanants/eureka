package com.eureka.capstone.domain.user;

import com.eureka.capstone.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Table(name = "USER")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "`group`")
    private Group group;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] profileAvatar;

    @Column(name = "token")
    @EqualsAndHashCode.Exclude
    private String token;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Builder
    public User(Long id, Timestamp createdDate, Timestamp lastModifiedDate,
                String username, String fullName, String email, String password,
                String phone, UserType userType, Group group) {
        super(id, createdDate, lastModifiedDate);
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userType = userType;
        this.group = group;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public boolean isAdmin() {
        return roles.stream().map(Role::getRoleName).anyMatch(RoleEnum.ADMIN_ROLE::equals);
    }

}
