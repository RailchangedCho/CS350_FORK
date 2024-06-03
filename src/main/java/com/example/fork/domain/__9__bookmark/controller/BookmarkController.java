package com.example.fork.domain.__9__bookmark.controller;

import com.example.fork.domain.__5__review.service.ReviewService;
import com.example.fork.domain.__9__bookmark.service.BookmarkService;
import com.example.fork.global.auth.AuthProvider;
import com.example.fork.global.data.dto.BookmarkDto;
import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.function.HashTagParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final AuthProvider authProvider;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService, AuthProvider authProvider) {
        this.bookmarkService = bookmarkService;
        this.authProvider = authProvider;
    }

    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> addBookmark(@RequestHeader Map<String, String> requestHeader,
                                                           @RequestBody Map<String, Object> requestBody) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        bookmarkService.addBookmark(requestUserId, requestBody);

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
    public ResponseEntity<Map<String, Object>> getBookmarkList(@RequestHeader Map<String, String> requestHeader,
                                                               @RequestParam(required = false, defaultValue = "date") @Pattern(regexp = "^(date)$") String field,
                                                               @RequestParam(required = false, defaultValue = "asc") @Pattern(regexp = "^(asc|desc)$") String sort) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        List<BookmarkDto> bookmarkDtoList = bookmarkService.getBookmarkList(field, sort, requestUserId);

        Map<String, Object> item = new HashMap<>();
        item.put("BookmarkList", bookmarkDtoList);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{bookmark_id}")
    public ResponseEntity<Map<String, Object>> getBookmark(@RequestHeader Map<String, String> requestHeader,
                                                           @PathVariable String bookmark_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        BookmarkDto bookmarkDto = bookmarkService.getBookmark(bookmark_id);

        Map<String, Object> item = new HashMap<>();
        item.put("Bookmark", bookmarkDto);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("error_code", 0);
        responseBody.put("error_text", "no error");
        responseBody.put("item", item);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{bookmark_id}")
    public ResponseEntity<Map<String, Object>> deleteBookmark(@RequestHeader Map<String, String> requestHeader,
                                                              @PathVariable String bookmark_id) {

        String JwtTokenString = requestHeader.get("authorization");
        String requestUserId = authProvider.getUserInfoByAccessToken(JwtTokenString).get("id");
        Integer userAuthType = authProvider.getUserAuthType(requestUserId);

        bookmarkService.deleteBookmark(bookmark_id);

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
