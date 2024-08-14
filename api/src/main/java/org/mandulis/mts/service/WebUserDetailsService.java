package org.mandulis.mts.service;

import org.mandulis.mts.dto.response.ErrorMessages;
import org.mandulis.mts.entity.User;
import org.mandulis.mts.repository.UserRepository;
import org.mandulis.mts.security.WebUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WebUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public WebUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessages.USER_NOT_FOUND + username));
        return new WebUserDetails(user);
    }
}
