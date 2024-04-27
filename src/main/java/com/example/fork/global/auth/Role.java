package com.example.fork.global.auth;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    FRANCHISE_ADMIN ("ROLE_ADMIN"),
    ENDUSER ("ROLE_USER");

    private final String roles;

    public String getValue() {
        return this.roles;
    }
}
