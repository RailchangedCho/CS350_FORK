package com.example.fork.global.data.dao.impl;

import com.example.fork.global.data.dao.ReviewDao;
import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.entity.Facility;
import com.example.fork.global.data.entity.Review;
import com.example.fork.global.data.repository.FacilityRepository;
import com.example.fork.global.data.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewDaoImpl implements ReviewDao {

    ReviewRepository reviewRepository;

    @Autowired
    public ReviewDaoImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewDto> getReviewList() {
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findAll();
        for (Review r : reviewList) {
            reviewDtoList.add(r.toDto());
        }
        return reviewDtoList;
    }

    @Override
    public ReviewDto getReview(String reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        return optionalReview.map(Review::toDto).orElse(null);
    }

    @Override
    public void addReview(ReviewDto reviewDto) {
        if(reviewRepository.findById(reviewDto.getId()).isEmpty()) {
            reviewRepository.save(reviewDto.toEntity());
        }
    }

    @Override
    public void editReview(ReviewDto reviewDto) {
        if(reviewRepository.findById(reviewDto.getId()).isPresent()) {
            reviewRepository.save(reviewDto.toEntity());
        }
    }

    @Override
    public void deleteReview(String reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if(optionalReview.isPresent()) {
            Review review = optionalReview.get();
            reviewRepository.delete(review);
        }
    }
}
