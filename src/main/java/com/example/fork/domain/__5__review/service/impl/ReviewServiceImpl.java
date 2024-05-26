package com.example.fork.domain.__5__review.service.impl;

import com.example.fork.domain.__5__review.service.ReviewService;
import com.example.fork.global.data.dao.FacilityDao;
import com.example.fork.global.data.dao.ReviewDao;
import com.example.fork.global.data.dao.StampDao;
import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.StampDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public void addReview(String requestUserId, Map<String, Object> requestBody) {

        ReviewDto reviewDto = ReviewDto.builder()
                .id(UUID.randomUUID().toString())
                .text(requestBody.get("review_text").toString())
                .score(Integer.valueOf(requestBody.get("review_score").toString()))
                .hashtag(Integer.valueOf(requestBody.get("review_hashtag").toString()))
                .registerDate(LocalDateTime.now())
                .userId(requestUserId)
                .facilityId(requestBody.get("facility_id").toString())
                .imageId(null)
                .build();

        reviewDao.addReview(reviewDto);
    }

    @Override
    public List<ReviewDto> getReviewList(String field, String sort, String facilityId) {

        List<ReviewDto> responseList = new ArrayList<>();
        if (facilityId.equals("None")) {
            responseList = reviewDao.getReviewList();
        }
        else {
            responseList = reviewDao.getReviewListByFacilityId(facilityId);
        }

        if (field.equals("image")) {
            for (ReviewDto r : responseList) {
                if (r.getImageId() == null) {
                    responseList.remove(r);
                }
            }
        }

        if (sort.equals("asc")) {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(ReviewDto::getRegisterDate))
                        .collect(Collectors.toList());
            }
        }

        else {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(ReviewDto::getRegisterDate).reversed())
                        .collect(Collectors.toList());
            }
        }

        // TODO : EXCEPTION HANDLING
        return null;
    }

    @Override
    public ReviewDto getReview(String reviewId) {
        return reviewDao.getReview(reviewId);
    }

    @Override
    public void editReview(String reviewId, Map<String, Object> requestBody){

        ReviewDto reviewDto = reviewDao.getReview(reviewId);
        reviewDto.setText(requestBody.get("review_text").toString());
        reviewDto.setScore(Integer.valueOf(requestBody.get("review_score").toString()));
        reviewDto.setHashtag(Integer.valueOf(requestBody.get("review_hashtag").toString()));
        //reviewDto.setImageId((requestBody.get("facility_description_eng").toString()));

        reviewDao.editReview(reviewDto);
    }

    @Override
    public void deleteReview(String reviewId) {
        reviewDao.deleteReview(reviewId);
    }
}
