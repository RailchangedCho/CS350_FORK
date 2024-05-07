package com.example.fork.domain.__2__facility.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/facility")
public class FacilityController {

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addFacility(@RequestHeader Map<String, String> requestHeader,
                                                           @RequestBody Map<String, Object> requestBody) {

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
    public ResponseEntity<Map<String, Object>> getFacilityList(@RequestParam(required = false, defaultValue = "distance") @Pattern(regexp = "^(distance)$") String field,
                                                               @RequestParam(required = false, defaultValue = "asc") @Pattern(regexp = "^(asc|desc)$") String sort,
                                                               @RequestParam(required = false, defaultValue = "0") Float latitude,
                                                               @RequestParam(required = false, defaultValue = "0") Float longitude,
                                                               @RequestParam(required = false, defaultValue = "KOR") @Pattern(regexp = "^(KOR|ENG)$") String language) {

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
    @GetMapping("/{facility_id}")
    public ResponseEntity<Map<String, Object>> getFacility(@PathVariable String facility_id,
                                                           @RequestParam(required = false, defaultValue = "KOR") @Pattern(regexp = "^(KOR|ENG)$") String language) {

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
    @PutMapping("/{facility_id}")
    public ResponseEntity<Map<String, Object>> editFacility(@RequestHeader Map<String, String> requestHeader,
                                                            @PathVariable String facility_id,
                                                            @RequestBody Map<String, Object> requestBody) {

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
    @DeleteMapping("/{facility_id}")
    public ResponseEntity<Map<String, Object>> deleteFacility(@RequestHeader Map<String, String> requestHeader,
                                                              @PathVariable String facility_id) {

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
    @DeleteMapping("/leaderboard")
    public ResponseEntity<Map<String, Object>> getLeaderBoard(@RequestHeader Map<String, String> requestHeader) {

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
