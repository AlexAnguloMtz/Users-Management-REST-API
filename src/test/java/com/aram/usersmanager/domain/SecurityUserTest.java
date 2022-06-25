package com.aram.usersmanager.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static com.aram.usersmanager.common.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SecurityUserTest {

    @Test
    void givenUser_whenCreatingSecurityUser_thenBothRolesMustMatch() {
        //Given
        User user = userWithRole(VALID_ROLE);

        //When
        SecurityUser securityUser = new SecurityUser(user);

        //Then
        Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
        GrantedAuthority expectedAuthority = new SimpleGrantedAuthority(VALID_ROLE.toString());
        assertTrue(authorities.contains(expectedAuthority));
    }

    @Test
    void givenNullUser_whenCreatingSecurityUser_thenThrowsException() {
        User nullUser = null;
        assertThrows(NullPointerException.class, () -> new SecurityUser(nullUser));
    }

    private User userWithRole(Role role) {
        return new User(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, role);
    }

}
