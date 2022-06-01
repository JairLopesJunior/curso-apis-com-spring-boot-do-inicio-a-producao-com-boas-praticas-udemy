package com.jairlopes.bookstoremanager.service;

import com.jairlopes.bookstoremanager.dto.AuthenticatedUser;
import com.jairlopes.bookstoremanager.entity.User;
import com.jairlopes.bookstoremanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username %s", username)));
        return new AuthenticatedUser(
                user.getUsername(),
                user.getPassword(),
                user.getRole().getDescription()
        );
    }
}
