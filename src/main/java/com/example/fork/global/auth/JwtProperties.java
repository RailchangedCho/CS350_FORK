package com.example.fork.global.auth;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigInteger;

public interface JwtProperties {

    String SECRET = "RANDOMSECRET1234"; //(2)
    Long EXPIRATION_TIME =  864000000000L; //(3)
    String TOKEN_PREFIX = "Bearer "; //(4)
    String HEADER_STRING = "Authorization"; //(5)
}
