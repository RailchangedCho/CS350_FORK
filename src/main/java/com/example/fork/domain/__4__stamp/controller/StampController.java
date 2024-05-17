package com.example.fork.domain.__4__stamp.controller;

import com.example.fork.domain.__2__facility.service.FacilityService;
import com.example.fork.domain.__4__stamp.service.StampService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dto.FacilityDto;
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
@RequestMapping("/api/v1/stamp")
public class StampController {

    private final StampService stampService;
    private final AuthProvider authProvider;

    @Autowired
    public StampController(StampService stampService, AuthProvider authProvider) {
        this.stampService = stampService;
        this.authProvider = authProvider;
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addStamp(@RequestHeader Map<String, String> requestHeader,
                                                        @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        stampService.addStamp(requestUserId, requestBody);

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
    public ResponseEntity<Map<String, Object>> getStampList(@RequestHeader Map<String, String> requestHeader,
                                                            @RequestParam(required = false, defaultValue = "asc") @Pattern(regexp = "^(asc|desc)$") String sort,
                                                            @RequestParam(required = false, defaultValue = "all") String facility_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        List<StampDto> stampDtoList = stampService.getStampList("date", sort);

        Map<String, Object> item = new HashMap<>();
        item.put("StampList", stampDtoList);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{stamp_id}")
    public ResponseEntity<Map<String, Object>> getStamp(@RequestHeader Map<String, String> requestHeader,
                                                        @PathVariable String stamp_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        StampDto stampDto = stampService.getStamp(stamp_id);

        Map<String, Object> item = new HashMap<>();
        item.put("Stamp", stampDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/{stamp_id}")
    public ResponseEntity<Map<String, Object>> editStamp(@RequestHeader Map<String, String> requestHeader,
                                                         @PathVariable String stamp_id,
                                                         @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        stampService.editStamp(stamp_id, requestBody);

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
    @DeleteMapping("/{stamp_id}")
    public ResponseEntity<Map<String, Object>> deleteStamp(@RequestHeader Map<String, String> requestHeader,
                                                         @PathVariable String stamp_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        stampService.deleteStamp(stamp_id);

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
