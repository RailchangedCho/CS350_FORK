package com.example.fork.global.auth;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Type {

    KAIST ("ROLE_KAIST"),
    FACILITY ("ROLE_FACILITY"),
    ADMIN("ROLE_ADMIN");

    private final String types;

    public String getValue() {
        return this.types;
    }
}
