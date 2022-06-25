package com.aram.usersmanager.domain.username;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor(force = true)
public final class Username {

    private static final int MINIMUM_LENGTH = 10;
    private static final int MAXIMUM_LENGTH = 25;
    private final String username;

    public Username(String username) {
        this.username = validate(username);
    }

    public static String validate(String username) {
        if(username == null) {
            throw new InvalidUsernameLengthException(MINIMUM_LENGTH, MAXIMUM_LENGTH);
        }
        if(!hasValidLength(username)) {
            throw new InvalidUsernameLengthException(MINIMUM_LENGTH, MAXIMUM_LENGTH);
        }
        if(!hasOnlyValidCharacters(username)) {
            throw new InvalidCharactersInUsernameException();
        }
        return username;
    }

    private static boolean hasOnlyValidCharacters(String username) {
        return username.matches("^\\w*$");
    }

    private static boolean hasValidLength(String username) {
        return !isTooShort(username) && !isTooLong(username);
    }

    private static boolean isTooShort(String username) {
        return username.length() < MINIMUM_LENGTH;
    }

    private static boolean isTooLong(String username) {
        return username.length() > MAXIMUM_LENGTH;
    }



}
