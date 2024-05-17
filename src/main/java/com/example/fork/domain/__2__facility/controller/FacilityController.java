package com.example.fork.domain.__2__facility.controller;

import com.example.fork.domain.__2__facility.service.FacilityService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dto.FacilityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/facility")
public class FacilityController {

    private final FacilityService facilityService;
    private final AuthProvider authProvider;

    @Autowired
    public FacilityController(FacilityService facilityService, AuthProvider authProvider) {
        this.facilityService = facilityService;
        this.authProvider = authProvider;
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addFacility(@RequestHeader Map<String, String> requestHeader,
                                                           @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        //권한체크 : 사업자
        facilityService.addFacility(requestUserId, requestBody);

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

        List<FacilityDto> facilityDtoList = facilityService.getFacilityList(field, sort);

        Map<String, Object> item = new HashMap<>();
        item.put("FacilityList", facilityDtoList);
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

        FacilityDto facilityDto = facilityService.getFacility(facility_id);

        Map<String, Object> item = new HashMap<>();
        item.put("Facility", facilityDto);
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

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        // TODO : CHECK ERROR CODE
        /*
        if (userAuthType != 0) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 0);
            responseBody.put("error_text", "User cannot edit board.");
            //responseBody.put("item", null);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
        */

        facilityService.editFacility(facility_id, requestBody);

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

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        // TODO : CHECK ERROR CODE
        /*
        if (userAuthType != 0) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 0);
            responseBody.put("error_text", "User cannot edit board.");
            //responseBody.put("item", null);
            return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
        }
        */

        facilityService.deleteFacility(facility_id);

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
