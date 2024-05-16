package com.example.fork.domain.__1__auth.service.impl;

import com.example.fork.domain.__1__auth.service.AuthService;
import com.example.fork.domain.__1__auth.service.RedisService;
import com.example.fork.global.data.dao.UserDao;
import com.example.fork.global.data.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final UserDao userDao;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Override
    public void sendEmail(String toEmail, String title, String text) {

        SimpleMailMessage simpleMailMessage = createEmailForm(toEmail, title, text);
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (RuntimeException e) {
            log.debug("MailService.sendEmail exception occur toEmail: {}, " +
                    "title: {}, text: {}", toEmail, title, text);
            throw new RuntimeException();
        }
    }

    private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }

    @Override
    public void sendCodeToEmail(String toEmail) {
        // this.checkDuplicatedEmail(toEmail);
        String title = "FORK : Email Registration";
        String authCode = this.createCode();
        this.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + toEmail, authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    private void checkDuplicatedEmail(String email) {
        UserDto userDto = userDao.getUserByEmail(email);
        if (!(userDto == null)) {
            log.warn("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new RuntimeException();
        }
    }

    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new RuntimeException();
        }
    }

    @Override
    public Boolean verifiedCode(String email, String authCode) {
        //this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        Boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);

        //return EmailVerificationResult.of(authResult);
        return authResult;
    }
}
