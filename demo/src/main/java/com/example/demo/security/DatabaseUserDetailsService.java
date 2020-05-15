package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepo.findById(username);
        User user = userOpt.orElseThrow(() -> new UsernameNotFoundException(username));

        return new DemoPrincipal(username, user.getName(), Arrays.asList(user.getRole().name()));
    }
}
