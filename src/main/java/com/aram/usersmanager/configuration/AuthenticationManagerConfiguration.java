package com.aram.usersmanager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
class AuthenticationManagerConfiguration {

    @Autowired
    void configureAuthenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder,
                                        AuthenticationProvider authenticationProvider) {

        authenticationManagerBuilder.authenticationProvider(authenticationProvider);

    }

}
