package com.example.fork.domain.__2__facility.controller;

import com.example.fork.domain.__2__facility.service.FacilityService;
import com.example.fork.domain.__3__alert.service.AlertService;
import com.example.fork.domain.__7__user.service.UserService;
import com.example.fork.domain.__9__bookmark.service.BookmarkService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dto.BookmarkDto;
import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.UserDto;
import com.example.fork.global.data.dto.etc.LeaderboardDto;
import com.example.fork.global.function.FacilityTagParser;
import com.example.fork.global.function.HashTagParser;
import io.vertx.json.schema.common.dsl.BooleanKeyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/facility")
public class FacilityController {

    private final FacilityService facilityService;
    private final AuthProvider authProvider;
    private final AlertService alertService;
    private final BookmarkService bookmarkService;
    private final UserService userService;

    @Autowired
    public FacilityController(FacilityService facilityService,
                              AuthProvider authProvider,
                              AlertService alertService,
                              BookmarkService bookmarkService,
                              UserService userService) {
        this.facilityService = facilityService;
        this.authProvider = authProvider;
        this.alertService = alertService;
        this.bookmarkService = bookmarkService;
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addFacility(@RequestHeader Map<String, String> requestHeader,
                                                           @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        //권한체크 : 사업자
        String facilityId = facilityService.addFacility(requestUserId, requestBody);

        Map<String, Object> item = new HashMap<>();
        item.put("facilityId", facilityId);
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

        List<FacilityDto> facilityDtoList = facilityService.getFacilityList(field, sort, latitude, longitude);

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
    @GetMapping("/owner/get")
    public ResponseEntity<Map<String, Object>> getFacilityForOwner(@RequestHeader Map<String, String> requestHeader) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        FacilityDto facilityDto = facilityService.getFacilityByUserId(requestUserId);

        if (facilityDto == null) {
            Map<String, Object> item = new HashMap<>();
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 0);
            responseBody.put("error_text", "no such facility");
            responseBody.put("item", item);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        List<String> facilityTagList = new ArrayList<>();
        if (facilityDto.getTag() != null) {
            facilityTagList = FacilityTagParser.parseFacilityTag(facilityDto.getTag());
        }

        Map<String, Object> item = new HashMap<>();
        item.put("Facility", facilityDto);
        item.put("FacilityTags", facilityTagList);
        item.put("AverageReviewScore", facilityService.getAverageReviewScore(facilityDto.getId()));
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

        if (facilityDto == null) {
            Map<String, Object> item = new HashMap<>();
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("error_code", 0);
            responseBody.put("error_text", "no such facility");
            responseBody.put("item", item);

            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }

        List<String> facilityTagList = new ArrayList<>();
        if (facilityDto.getTag() != null) {
            facilityTagList = FacilityTagParser.parseFacilityTag(facilityDto.getTag());
        }

        Map<String, Object> item = new HashMap<>();
        item.put("Facility", facilityDto);
        item.put("FacilityTags", facilityTagList);
        item.put("AverageReviewScore", facilityService.getAverageReviewScore(facility_id));
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

        List<UserDto> userDtoList = getUserWhoBookmarkFacility(facility_id);
        for (UserDto u : userDtoList) {
            String targetUserId = u.getId();
            Map<String, Object> alertContent = new HashMap<>();
            alertContent.put("title", "가게 정보 업데이트 알림");
            alertContent.put("body", u.getName() + " 의 정보가 변경되었습니다.");
            alertService.pushAlert(targetUserId, alertContent);
        }

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/{facility_id}/{user_id}")
    public ResponseEntity<Map<String, Object>> editFacilityOwner(@PathVariable String facility_id,
                                                                 @PathVariable String user_id) {

        facilityService.editFacilityOwner(facility_id, user_id);

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
    public ResponseEntity<Map<String, Object>> getLeaderBoard() {

        List<FacilityDto> facilityDtoList = facilityService.getFacilityList("date", "asc", 0f, 0f);
        List<LeaderboardDto> leaderboardDtoList = new ArrayList<>();
        for (FacilityDto f : facilityDtoList) {
            Double avg_score = facilityService.getAverageReviewScore(f.getId());
            Integer reviewNum = facilityService.getTotalReviewNumber(f.getId());
            leaderboardDtoList.add(f.toLeaderboardDto(avg_score, reviewNum));
        }

        List<LeaderboardDto> sorted = leaderboardDtoList
                .stream()
                .sorted(Comparator.comparing(LeaderboardDto::getAvg_score).reversed())
                .collect(Collectors.toList());
        List<LeaderboardDto> finalLeaderboard = new ArrayList<>();
        for (LeaderboardDto l : sorted) {
            if (l.getReviewNum() >= 3) {
                finalLeaderboard.add(l);
            }
            if (finalLeaderboard.size() >=3) {
                break;
            }
        }

        Map<String, Object> item = new HashMap<>();
        item.put("Leaderboard", finalLeaderboard);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private List<UserDto> getUserWhoBookmarkFacility(String facilityId) {

        List<UserDto> userDtoList = new ArrayList<>();
        List<BookmarkDto> bookmarkDtoList = bookmarkService.getBookmarkListByFacilityId("date", "asc", facilityId);
        for (BookmarkDto b : bookmarkDtoList) {
            userDtoList.add(userService.getUser(b.getUserId()));
        }
        return userDtoList;
    }
}
