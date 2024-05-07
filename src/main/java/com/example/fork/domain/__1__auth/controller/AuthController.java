package com.example.fork.domain.__1__auth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.fork.global.auth.JwtProperties;
import com.example.fork.global.auth.Permission;
import com.example.fork.global.auth.SHA256;
import com.example.fork.global.auth.Type;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

/*
    @Autowired
    public AuthController() {
    }
*/

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
                .withClaim("type", userDto.getType().getValue())
                .withClaim("token", "USER_TOKEN")
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return userJwtToken;
    }

//    @GetMapping("/admin")
//    @ApiOperation(value = "[임시] 관리자 토큰 발급", notes = "[임시] 관리자 토큰 발급")
//    public String adminLogin() {
//
//        Random random = new Random();
//        EndUserDto endUserDto = EndUserDto.builder()
//                .id(UUID.randomUUID().toString())
//                .name("admin" + random.nextInt())
//                .email(null)
//                .phone(null)
//                .status_code(ActiveFlag.ACTIVE)
//                .login_type(LoginType.ADMIN)
//                .role(Role.FRANCHISE_ADMIN)
//                .register_date(LocalDateTime.now())
//                .position(null)
//                .franchise(null)
//                .nickname(null)
//                .uuid(null)
//                .payCard_amount(null)
//                .build();
//
//        endUserDao.addEndUser(endUserDto);
//
//        return createAdminJwtToken(endUserDto);
//    }

    @ResponseBody
    @PostMapping("/kaist")
    //@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Map<String, Object>> userRegisterKaist(@RequestBody Map<String, Object> requestBody) {

        String email = requestBody.get("email").toString();
        /*
        if (!(endUserDao.getEndUserDetailByEmail(email) == null)) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "이미 가입된 이메일입니다.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        */

        UserDto userDto = UserDto.builder()
                .id(UUID.randomUUID().toString())
                .password(SHA256Encryptor.encrypt(requestBody.get("password").toString()))
                .email(requestBody.get("email").toString())
                .type(Type.KAIST)
                .permission(Permission.NO_PERMISSION)
                .attributes(requestBody.get("email").toString())
                .build();

        // endUserDao.addEndUser(endUserDto);

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

        String email = requestBody.get("email").toString();
        /*
        if (!(endUserDao.getEndUserDetailByEmail(email) == null)) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "이미 가입된 이메일입니다.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        */

        UserDto userDto = UserDto.builder()
                .id(UUID.randomUUID().toString())
                .password(SHA256Encryptor.encrypt(requestBody.get("password").toString()))
                .email(requestBody.get("email").toString())
                .type(Type.FACILITY)
                .permission(Permission.NO_PERMISSION)
                .attributes(requestBody.get("email").toString())
                .build();

        // endUserDao.addEndUser(endUserDto);

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

        // emailService.sendCodeToEmail(email);

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

        //Boolean response = emailService.verifiedCode(email, authCode);

        //if (response) {
        //    EndUserDto endUserDto = endUserDao.getEndUserDetailByEmail(email);
        //    endUserDto.setIs_authenticated(true);
        //}

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
    public ResponseEntity<Map<String, Object>> verifyBusinessCode(@RequestParam("code") String authCode) {

        //Boolean response = emailService.verifiedCode(email, authCode);

        //if (response) {
        //    EndUserDto endUserDto = endUserDao.getEndUserDetailByEmail(email);
        //    endUserDto.setIs_authenticated(true);
        //}

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

        String email = requestBody.get("email").toString();
        String password = requestBody.get("password").toString();

        if (email == null) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "아이디를 입력하세요");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        /*
        EndUserDto endUserDto = endUserDao.getEndUserDetailByEmail(email);

        if (endUserDto == null) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "존재하지 않는 유저입니다.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        if (!endUserDto.getIs_authenticated()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "이메일 인증을 먼저 진행해 주세요.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        if (!Sha256Encryptor.encrypt(password).equals(endUserDto.getPassword())) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 1);
            responseBody.put("error_text", "비밀번호가 일치하지 않습니다.");

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        String userJwtToken = createUserJwtToken(endUserDto);
        authProvider.saveToken(userJwtToken);
        */
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
    @GetMapping("/signout")
    public ResponseEntity<Map<String, Object>> userSignOut(@RequestHeader Map<String, String> requestHeader) {

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
