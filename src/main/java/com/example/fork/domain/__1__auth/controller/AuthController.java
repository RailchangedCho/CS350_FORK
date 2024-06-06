package com.example.fork.domain.__1__auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.fork.domain.__1__auth.service.AuthService;
import com.example.fork.global.auth.*;
import com.example.fork.global.data.dao.UserDao;
import com.example.fork.global.data.dto.UserDto;
import com.example.fork.global.function.SHA256Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserDao userDao;
    private final AuthProvider authProvider;

    @Autowired
    public AuthController(AuthProvider authProvider,
                          AuthService authService,
                          UserDao userDao) {
        this.authProvider = authProvider;
        this.authService = authService;
        this.userDao = userDao;
    }

    private String createAdminJwtToken(UserDto userDto) {

        String adminJwtToken = JWT.create()
                .withSubject(userDto.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userDto.getId())
                .withClaim("email", userDto.getEmail())
                .withClaim("type", userDto.getType().getValue())
                .withClaim("token", "MASTER_TOKEN")
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return adminJwtToken;
    }

    private String createUserJwtToken(UserDto userDto) {

        String userJwtToken = JWT.create()
                .withSubject(userDto.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", userDto.getId())
                .withClaim("email", userDto.getEmail())
                .withClaim("role", userDto.getType().getValue())
                .withClaim("token", "USER_TOKEN")
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return userJwtToken;
    }

    private Boolean nameDuplicateCheck(String name) {

        if (!(userDao.getUserByName(name) == null)) {
            return false; // error
        }
        return true;
    }

    @GetMapping("/admin")
    public String adminLogin() {

        Random random = new Random();
        UserDto userDto = UserDto.builder()
                .id(UUID.randomUUID().toString())
                .name("admin" + random.nextInt())
                .password(SHA256Encryptor.encrypt("1234"))
                .email("admin@gmail.com")
                .deviceId(null)
                .status(true)
                .defaultLanguage("KOR")
                .registerDate(LocalDateTime.now())
                .type(Type.ADMIN)
                .isAuthenticated(true)
                .permission(Permission.PERMISSION)
                .attributes(null)
                .build();

        return createAdminJwtToken(userDto);
    }

    @ResponseBody
    @PostMapping("/kaist")
    //@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Map<String, Object>> userRegisterKaist(@RequestBody Map<String, Object> requestBody) {

        String email = requestBody.get("user_email").toString();
        if (!(userDao.getUserByEmail(email) == null)) {
            UserDto userDto = userDao.getUserByEmail(email);
            if (!userDto.getIsAuthenticated()) {
                String name = requestBody.get("user_name").toString();
                if (!nameDuplicateCheck(name)) {
                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("success", false);
                    responseBody.put("error_code", 1);
                    responseBody.put("error_text", "중복되는 id 입니다.");

                    return new ResponseEntity<>(responseBody, HttpStatus.OK);
                }
                userDto.setName(name);
                userDto.setPassword(SHA256Encryptor.encrypt(requestBody.get("user_password").toString()));
                userDao.editUser(userDto);
            }
            else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("success", false);
                responseBody.put("error_code", 1);
                responseBody.put("error_text", "이미 가입된 이메일입니다.");

                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }
        }

        String name = requestBody.get("user_name").toString();
        if (!nameDuplicateCheck(name)) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "중복되는 id 입니다.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        UserDto userDto = UserDto.builder()
                .id(UUID.randomUUID().toString())
                .name(requestBody.get("user_name").toString())
                .password(SHA256Encryptor.encrypt(requestBody.get("user_password").toString()))
                .email(requestBody.get("user_email").toString())
                .deviceId(null)
                .status(true)
                .defaultLanguage("KOR")
                .registerDate(LocalDateTime.now())
                .type(Type.KAIST)
                .isAuthenticated(false)
                .permission(Permission.PERMISSION)
                .attributes(null)
                .build();

        userDao.addUser(userDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        //responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/facility")
    //@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Map<String, Object>> userRegisterFacility(@RequestBody Map<String, Object> requestBody) {

        String email = requestBody.get("user_email").toString();
        if (!(userDao.getUserByEmail(email) == null)) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "이미 가입된 이메일입니다.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        UserDto userDto = UserDto.builder()
                .id(UUID.randomUUID().toString())
                .name(requestBody.get("user_name").toString())
                .password(SHA256Encryptor.encrypt(requestBody.get("user_password").toString()))
                .email(requestBody.get("user_email").toString())
                .deviceId(null)
                .status(true)
                .defaultLanguage("KOR")
                .registerDate(LocalDateTime.now())
                .type(Type.FACILITY)
                .isAuthenticated(false)
                .permission(Permission.PERMISSION)
                .attributes(null)
                .build();

        userDao.addUser(userDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        //responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/email")
    public ResponseEntity<Map<String, Object>> sendCode(@RequestParam("email") @Valid @Email String email) {

        authService.sendCodeToEmail(email);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        //responseBody.put("item", item);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/verify/email")
    public ResponseEntity<Map<String, Object>> verifyEmail(@RequestParam("email") @Valid @Email String email,
                                                           @RequestParam("code") String authCode) {

        Boolean response = authService.verifiedCode(email, authCode);

        if (response) {
            UserDto userDto = userDao.getUserByEmail(email);
            userDto.setIsAuthenticated(true);
            userDao.editUser(userDto);
        }

        Map<String, Object> item = new HashMap<>();
        //item.put("Result", response);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/verify/business")
    public ResponseEntity<Map<String, Object>> verifyBusinessCode(@RequestParam("name") String name,
                                                                  @RequestParam("code") String code,
                                                                  @RequestParam("start_date") String start_date) {

        UserDto userDto = userDao.getUserByName(name);
        userDto.setIsAuthenticated(true);
        userDao.editUser(userDto);

        Map<String, Object> item = new HashMap<>();
        //item.put("Result", response);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> userLogin(@RequestBody Map<String, Object> requestBody) {

        String name = requestBody.get("user_name").toString();
        String password = requestBody.get("user_password").toString();

        if (name == null) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "아이디를 입력하세요");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        UserDto userDto = userDao.getUserByName(name);

        if (userDto == null) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "존재하지 않는 유저입니다.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        if (!userDto.getIsAuthenticated()) {

            if (userDto.getType() == Type.KAIST) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("success", false);
                responseBody.put("error_code", 1);
                responseBody.put("error_text", "이메일 인증을 먼저 진행해 주세요.");

                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }

            else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("success", false);
                responseBody.put("error_code", 1);
                responseBody.put("error_text", "사업자 인증을 먼저 진행해 주세요.");

                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }
        }

        if (!SHA256Encryptor.encrypt(password).equals(userDto.getPassword())) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "비밀번호가 일치하지 않습니다.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        String userJwtToken = createUserJwtToken(userDto);
        authProvider.saveToken(userJwtToken);

        Map<String, Object> item = new HashMap<>();
        item.put("OAuthToken", userJwtToken);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/signout")
    public ResponseEntity<Map<String, Object>> userSignOut(@RequestHeader Map<String, String> requestHeader) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        String requestUserToken = authProvider.getUserInfoByAccessToken(JwtTokenString).get("token");

        userDao.deleteUser(requestUserId);

        Map<String, Object> item = new HashMap<>();
        //item.put("OAuthToken", userJwtToken);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/logout")
    public ResponseEntity<Map<String, Object>> userLogout(@RequestHeader Map<String, String> requestHeader) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        String requestUserToken = authProvider.getUserInfoByAccessToken(JwtTokenString).get("token");

        authProvider.expireToken(requestUserToken);

        Map<String, Object> item = new HashMap<>();
        //item.put("OAuthToken", userJwtToken);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
