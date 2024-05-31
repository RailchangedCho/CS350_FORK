package com.example.fork.domain.__7__user.controller;

import com.example.fork.domain.__2__facility.service.FacilityService;
import com.example.fork.domain.__7__user.service.UserService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.UserDto;
import com.example.fork.global.function.FacilityTagParser;
import com.example.fork.global.function.HashTagParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthProvider authProvider;

    @Autowired
    public UserController(UserService userService, AuthProvider authProvider) {
        this.userService = userService;
        this.authProvider = authProvider;
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addUser(@RequestHeader Map<String, String> requestHeader,
                                                       @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        userService.addUser(requestUserId, requestBody);

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
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getUserList(@RequestHeader Map<String, String> requestHeader,
                                                           @RequestParam(required = false, defaultValue = "asc") @Pattern(regexp = "^(asc|desc)$") String sort) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        List<UserDto> userDtoList = userService.getUserList("date", sort, requestUserId);

        Map<String, Object> item = new HashMap<>();
        item.put("UserList", userDtoList);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{user_id}")
    public ResponseEntity<Map<String, Object>> getUser(@RequestHeader Map<String, String> requestHeader,
                                                       @PathVariable String user_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        UserDto userDto = userService.getUser(user_id);

        List<String> facilityTagList = new ArrayList<>();
        if (userDto.getAttributes() != null) {
            facilityTagList = FacilityTagParser.parseFacilityTag(userDto.getAttributes());
        }

        Map<String, Object> item = new HashMap<>();
        item.put("User", userDto);
        item.put("FacilityTags", facilityTagList);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/{user_id}")
    public ResponseEntity<Map<String, Object>> editUser(@RequestHeader Map<String, String> requestHeader,
                                                        @PathVariable String user_id,
                                                        @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        userService.editUser(user_id, requestBody);

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
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestHeader Map<String, String> requestHeader,
                                                           @PathVariable String user_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        userService.deleteUser(user_id);

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
