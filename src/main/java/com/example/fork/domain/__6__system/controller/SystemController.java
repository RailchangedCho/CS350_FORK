package com.example.fork.domain.__6__system.controller;

import com.example.fork.domain.__2__facility.service.FacilityService;
import com.example.fork.domain.__6__system.service.SystemService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dao.ReportDao;
import com.example.fork.global.data.dao.UserDao;
import com.example.fork.global.data.dto.ReportDto;
import com.example.fork.global.data.dto.StampDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
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
    @GetMapping("/summary/{facility_id}")
    public ResponseEntity<Map<String, Object>> summaryReview(@PathVariable String facility_id) {

        String result = systemService.summaryReview(facility_id);

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
    public ResponseEntity<Map<String, Object>> reviewReport(@PathVariable String review_id,
                                                            @RequestBody Map<String, Object> requestBody) {

        systemService.addReport(1, null, review_id, requestBody);

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
    public ResponseEntity<Map<String, Object>> translateReview(@RequestParam(required = true, defaultValue = "KOR") @Pattern(regexp = "^(KOR|ENG)$") String target,
                                                               @RequestBody Map<String, Object> requestBody) {

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

    @ResponseBody
    @GetMapping("/report")
    public ResponseEntity<Map<String, Object>> getReportList(@RequestHeader Map<String, String> requestHeader,
                                                             @RequestParam(required = false, defaultValue = "asc") @Pattern(regexp = "^(asc|desc)$") String sort) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        List<ReportDto> reportDtoList = systemService.getReportList("date", sort);

        Map<String, Object> item = new HashMap<>();
        item.put("ReportList", reportDtoList);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/report/{report_id}")
    public ResponseEntity<Map<String, Object>> getReport(@RequestHeader Map<String, String> requestHeader,
                                                        @PathVariable String report_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        ReportDto reportDto = systemService.getReport(report_id);

        Map<String, Object> item = new HashMap<>();
        item.put("Report", reportDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/report/{report_id}")
    public ResponseEntity<Map<String, Object>> deleteReport(@RequestHeader Map<String, String> requestHeader,
                                                            @PathVariable String report_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        systemService.deleteReport(report_id);

        Map<String, Object> item = new HashMap<>();
        // item.put("Report", reportDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
