package com.eureka.capstone.security;

import com.eureka.capstone.exception.notfound.UserNotFoundException;
import com.eureka.capstone.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var OptionalUser = userRepository.findByUsername(username);
        var user = OptionalUser.orElseThrow(() -> new UserNotFoundException("There isn't registered account with entered username,"));

        return OptionalUser.map(UserDetailsImpl::new).get();
    }
}
