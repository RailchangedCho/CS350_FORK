package com.example.fork.global.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    NO_PERMISSION ("PERMIT_PASS"),
    PERMISSION ("PERMIT_DENY");

    private final String permits;

    public String getValue() {
        return this.permits;
    }
}
