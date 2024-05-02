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
    @PostMapping("")
    //@RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Map<String, Object>> kaistUserRegister(@RequestBody Map<String, Object> requestBody) {

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
}
