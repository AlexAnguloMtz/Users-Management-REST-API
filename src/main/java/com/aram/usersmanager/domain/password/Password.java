package com.aram.usersmanager.domain.password;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Getter
public final class Password {

    /*
     *   Regex explanation:
     *      - At least 8 characters
     *      - At least 1 lowercase letter
     *      - At least 1 uppercase letter
     *      - At least 1 number
     *      - At least 1 special character
     */
    private static final String VALID_PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private final String password;

    public Password(String password) {
        this.password = validate(password);
    }

    public static String validate(String password) {
        if (!Password.isValid(password)) {
            throw new PasswordFormatException();
        }
        return password;
    }

    private static boolean isValid(String password) {
        if(password == null) {
            return false;
        }
        if(!password.matches(VALID_PASSWORD_REGEX)) {
            return false;
        }
        return true;
    }

}
