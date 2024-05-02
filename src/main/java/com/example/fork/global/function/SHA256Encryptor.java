package com.example.fork.global.function;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256Encryptor {

    public static String encrypt(String rawString) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(rawString.getBytes());
            byte[] hashedString = messageDigest.digest();
            for(byte b : hashedString) {
                stringBuffer.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
