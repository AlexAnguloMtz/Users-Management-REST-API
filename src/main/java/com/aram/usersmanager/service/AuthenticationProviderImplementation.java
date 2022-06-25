package com.aram.usersmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
final class AuthenticationProviderImplementation implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    AuthenticationProviderImplementation(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        validatePassword(password(authentication), userDetails);
        return mapToAuthentication(userDetails);
    }

    private String password(Authentication authentication) {
        return authentication.getCredentials().toString();
    }

    private Authentication mapToAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken
                (userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    private void validatePassword(String rawPassword, UserDetails userDetails) {
        String encodedPassword = userDetails.getPassword();
        if(!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Incorrect password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
