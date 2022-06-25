package com.aram.chesslocals.security.domain;

import com.aram.chesslocals.security.domain.email.Email;
import com.aram.chesslocals.security.domain.password.Password;
import com.aram.chesslocals.security.domain.username.Username;
import lombok.*;

import javax.persistence.*;

@Entity(name = "user_info")
@Table(name = "user_info")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Embedded
    @Column(name = "username")
    private Username username;

    @Embedded
    @Column(name = "email")
    private Email email;

    @Embedded
    @Column(name = "password")
    private Password password;

    @Getter
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Role authority;

    @Builder
    public User(String username, String password, String email, Role authority) {
        this.username = new Username(username);
        this.password = new Password(password);
        this.email = new Email(email);
        this.authority = Role.validate(authority);
    }

    public String getUsername() {
        return username.getUsername();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }


}
