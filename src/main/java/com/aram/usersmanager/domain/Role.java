package com.aram.chesslocals.security.domain;

import static java.util.Objects.requireNonNull;

public enum Role {

    ADMIN,
    REGULAR;

    public static Role validate(Role authority) {
        requireNonNull(authority, "User role cannot be null");
        return authority;
    }

}
