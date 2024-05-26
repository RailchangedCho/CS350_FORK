package com.example.fork.domain.__6__system.controller;

import com.example.fork.domain.__2__facility.service.FacilityService;
import com.example.fork.domain.__6__system.service.SystemService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/system")
public class SystemController {

    private final SystemService systemService;
    private final AuthProvider authProvider;

    @Autowired
    public SystemController(SystemService systemService,
                            AuthProvider authProvider) {
        this.systemService = systemService;
        this.authProvider = authProvider;
    }

    @ResponseBody
    @GetMapping("/language")
    public ResponseEntity<Map<String, Object>> langExchange(@RequestHeader Map<String, String> requestHeader,
                                                            @RequestParam(required = true, defaultValue = "KOR") @Pattern(regexp = "^(KOR|ENG)$") String target) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        systemService.langExchange(requestUserId, target);

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
    @GetMapping("/summary/{review_id}")
    public ResponseEntity<Map<String, Object>> summaryReview(@RequestHeader Map<String, String> requestHeader,
                                                             @PathVariable String review_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        String result = systemService.summaryReview(review_id);

        Map<String, Object> item = new HashMap<>();
        item.put("Summary", result);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/report")
    public ResponseEntity<Map<String, Object>> bugReport(@RequestHeader Map<String, String> requestHeader,
                                                         @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        systemService.addReport(0, requestUserId, null, requestBody);

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
    @PostMapping("/report/{review_id}")
    public ResponseEntity<Map<String, Object>> reviewReport(@RequestHeader Map<String, String> requestHeader,
                                                            @PathVariable String review_id,
                                                            @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        systemService.addReport(1, requestUserId, review_id, requestBody);

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
    @PostMapping("/translate")
    public ResponseEntity<Map<String, Object>> translateReview(@RequestHeader Map<String, String> requestHeader,
                                                               @RequestParam(required = true, defaultValue = "KOR") @Pattern(regexp = "^(KOR|ENG)$") String target,
                                                               @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        String result = systemService.translate(requestBody, target);

        Map<String, Object> item = new HashMap<>();
        item.put("Translation", result);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
