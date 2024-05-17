package com.example.fork.domain.__5__review.controller;

import com.example.fork.domain.__2__facility.service.FacilityService;
import com.example.fork.domain.__5__review.service.ReviewService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthProvider authProvider;

    @Autowired
    public ReviewController(ReviewService reviewService, AuthProvider authProvider) {
        this.reviewService = reviewService;
        this.authProvider = authProvider;
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addReview(@RequestHeader Map<String, String> requestHeader,
                                                         @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        reviewService.addReview(requestUserId, requestBody);

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
    public ResponseEntity<Map<String, Object>> getReviewList(@RequestHeader Map<String, String> requestHeader,
                                                             @RequestParam(required = false, defaultValue = "all") @Pattern(regexp = "^(all|image|hashtag)$") String field,
                                                             @RequestParam(required = false, defaultValue = "asc") @Pattern(regexp = "^(asc|desc)$") String sort,
                                                             @RequestParam(required = false, defaultValue = "all") String facility_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        List<ReviewDto> reviewDtoList = reviewService.getReviewList(field, sort);

        Map<String, Object> item = new HashMap<>();
        item.put("ReviewList", reviewDtoList);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{review_id}")
    public ResponseEntity<Map<String, Object>> getReview(@RequestHeader Map<String, String> requestHeader,
                                                         @PathVariable String review_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        ReviewDto reviewDto = reviewService.getReview(review_id);

        Map<String, Object> item = new HashMap<>();
        item.put("Review", reviewDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping("/{review_id}")
    public ResponseEntity<Map<String, Object>> editReview(@RequestHeader Map<String, String> requestHeader,
                                                          @PathVariable String review_id,
                                                          @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        reviewService.editReview(review_id, requestBody);

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
    @DeleteMapping("/{review_id}")
    public ResponseEntity<Map<String, Object>> deleteReview(@RequestHeader Map<String, String> requestHeader,
                                                            @PathVariable String review_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        reviewService.deleteReview(review_id);

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
