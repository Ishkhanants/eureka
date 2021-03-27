package com.eureka.capstone.cookies;

import com.eureka.capstone.domain.user.User;
import lombok.NoArgsConstructor;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@NoArgsConstructor
@PropertySource("classpath:security.properties")
public class RememberMeCookieService {
    @Value("${security.secret.key}")
    private String SECRET_KEY;
    private Integer maxAge = 60 * 60 * 24 * 365;

    private String path = "/";


    public Cookie getCookie(User user) {
        Cookie rememberMeCookie = new Cookie("remember-me", createCookieValue(user));
        rememberMeCookie.setMaxAge(maxAge);
        rememberMeCookie.setPath(path);
        return rememberMeCookie;
    }

    private String createCookieValue(User user) {
        final long time = getNexYearMills();
        final String toBeHashed = String.format("%s:%s:%s:%s", user.getUsername(), time,
                user.getPassword(), SECRET_KEY);
        final String hash = DigestUtils.md5Hex(toBeHashed);
        final String toBeEncoded = String.format("%s:%s:%s", user.getUsername(), time, hash);
        return Base64.encodeBase64String(toBeEncoded.getBytes());
    }

    private long getNexYearMills() {
        return LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
