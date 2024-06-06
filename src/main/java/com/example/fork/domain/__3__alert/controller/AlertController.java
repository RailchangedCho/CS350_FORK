package com.example.fork.domain.__3__alert.controller;

import com.example.fork.domain.__3__alert.service.AlertService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dao.UserDao;
import com.example.fork.global.data.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alert")
public class AlertController {

    private final AlertService alertService;
    private final AuthProvider authProvider;
    private final UserDao userDao;

    @Autowired
    public AlertController(AlertService alertService,
                           AuthProvider authProvider,
                           UserDao userDao) {
        this.alertService = alertService;
        this.authProvider = authProvider;
        this.userDao = userDao;
    }

    @ResponseBody
    @PostMapping("/device")
    public ResponseEntity<Map<String, Object>> saveDeviceId(@RequestHeader Map<String, String> requestHeader,
                                                            @RequestParam("code") String code) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        UserDto userDto = userDao.getUser(requestUserId);
        userDto.setDeviceId(code);
        userDao.editUser(userDto);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        //responseBody.put("item", item);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> sendAlert(@RequestHeader Map<String, String> requestHeader,
                                                         @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        String targetUserId = requestBody.get("user_id").toString();
        Map<String, Object> alertContent = (Map<String, Object>) requestBody.get("notification");
        Boolean successflag = alertService.pushAlert(targetUserId, alertContent);

        if (!successflag) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 0);
            responseBody.put("error_text", "error occurs during alert sending.");
            //responseBody.put("item", null);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        //responseBody.put("item", null);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
