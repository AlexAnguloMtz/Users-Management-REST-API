package com.aram.usersmanager.service;

import com.aram.usersmanager.domain.SecurityUser;
import com.aram.usersmanager.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImplementation(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return new SecurityUser(user);
    }

}
