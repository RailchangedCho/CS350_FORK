package com.example.fork.global.data.dao;

import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.ReviewDto;

import java.util.List;

public interface ReviewDao {

    List<ReviewDto> getReviewList();
    ReviewDto getReview(String reviewId);
    void addReview(ReviewDto reviewDto);
    void editReview(ReviewDto reviewDto);
    void deleteReview(String reviewId);
}
