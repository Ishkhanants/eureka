package com.eureka.capstone.domain.login;

import com.eureka.capstone.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Table(name = "LOGIN_DETAILS")
public class LoginDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "ip_address", nullable = false)
    private String ip;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    public LoginDetails(String username, String ip, LocalDateTime dateTime) {
        this.username = username;
        this.ip = ip;
        this.dateTime = dateTime;
    }
}
