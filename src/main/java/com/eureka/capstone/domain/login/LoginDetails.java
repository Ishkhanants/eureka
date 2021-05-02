package com.eureka.capstone.domain.login;

import com.eureka.capstone.domain.user.User;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ip_address", nullable = false)
    private String ip;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    public LoginDetails(User user, String ip, LocalDateTime dateTime) {
        this.user = user;
        this.ip = ip;
        this.dateTime = dateTime;
    }

}
