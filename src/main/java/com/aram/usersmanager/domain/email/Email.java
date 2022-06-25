package com.aram.chesslocals.security.domain.email;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Objects;
@Embeddable
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class Email {
    private static final String EMAIL_REGEX =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
            "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[" +
            "\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z" +
            "0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9" +
            "]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z" +
            "0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\" +
            "x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Getter
    private final String email;

    public Email(String email) {
        this.email = validate(email);
    }

    public static String validate(String email) {
        if(email == null) {
            throw new InvalidEmailException();
        }
        if(!email.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException();
        }
        return email;
    }

}
