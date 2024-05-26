package com.example.fork.global.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    PERMISSION ("PERMIT_PASS"),
    NO_PERMISSION ("PERMIT_DENY");

    private final String permits;

    public String getValue() {
        return this.permits;
    }
}
