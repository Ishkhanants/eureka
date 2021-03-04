package com.eureka.capstone.domain.user;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    public Role(RoleEnum roleName) {
        this.roleName = roleName;
    }

    public RoleEnum getRoleName() {
        return roleName;
    }
}
