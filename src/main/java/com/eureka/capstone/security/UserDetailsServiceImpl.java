package com.eureka.capstone.security;

import com.eureka.capstone.domain.User;
import com.eureka.capstone.exception.notfound.UserNotFoundException;
import com.eureka.capstone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> OptionalUser = userRepository.findByUsername(username);
        User user = OptionalUser.orElseThrow(() -> new UserNotFoundException("There isn't registered account with entered username,"));
        return OptionalUser.map(UserDetailsImpl::new).get();
    }
}
