package com.aram.chesslocals.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toCollection;

public final class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser (User user) {
        this.user = Objects.requireNonNull
            (user, format("Can't create %s instance because %s received in constructor is null",
                   getClass().getName(), user.getClass().getName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.ofNullable(user)
                .map(User::getAuthority)
                .map(Role::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(toCollection(LinkedHashSet::new));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
