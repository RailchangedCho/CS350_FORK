package com.example.fork.domain.__5__review.service;

import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.StampDto;

import java.util.List;
import java.util.Map;

public interface ReviewService {

    void addReview(String requestUserId, Map<String, Object> requestBody);
    List<ReviewDto> getReviewList(String field, String sort);
    ReviewDto getReview(String reviewId);
    void editReview(String reviewId, Map<String, Object> requestBody);
    void deleteReview(String reviewId);
}
