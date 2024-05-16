package com.example.fork.domain.__1__auth.service;

import com.example.fork.global.data.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

public interface AuthService {

    void sendEmail(String toEmail, String title, String text);
    void sendCodeToEmail(String toEmail);
    Boolean verifiedCode(String email, String authCode);
}
