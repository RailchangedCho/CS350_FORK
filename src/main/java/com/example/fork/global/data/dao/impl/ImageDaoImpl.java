package com.example.fork.global.data.dao.impl;

import com.example.fork.global.data.dao.FacilityDao;
import com.example.fork.global.data.dao.ImageDao;
import com.example.fork.global.data.dto.ImageDto;
import com.example.fork.global.data.entity.Facility;
import com.example.fork.global.data.entity.Image;
import com.example.fork.global.data.entity.Review;
import com.example.fork.global.data.entity.Stamp;
import com.example.fork.global.data.repository.FacilityRepository;
import com.example.fork.global.data.repository.ImageRepository;
import com.example.fork.global.data.repository.ReviewRepository;
import com.example.fork.global.data.repository.StampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ImageDaoImpl implements ImageDao {

    private final ImageRepository imageRepository;
    private final FacilityRepository facilityRepository;
    private final StampRepository stampRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ImageDaoImpl(ImageRepository imageRepository,
                        FacilityRepository facilityRepository,
                        StampRepository stampRepository,
                        ReviewRepository reviewRepository) {
        this.imageRepository = imageRepository;
        this.facilityRepository = facilityRepository;
        this.stampRepository = stampRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public String getPrevImageName(String usage, String usageId) {

        String imageId;

        if (usage.equals("facility")) {
            if (facilityRepository.findById(usageId).get().getImageId() == null) {
                return "";
            }
            else {
                imageId = facilityRepository.findById(usageId).get().getImageId();
            }
        }

        else if (usage.equals("stamp")) {
            if (stampRepository.findById(usageId).get().getImageId() == null) {
                return "";
            }
            else {
                imageId = stampRepository.findById(usageId).get().getImageId();
            }
        }

        else { // review
            if (reviewRepository.findById(usageId).get().getImageId() == null) {
                return "";
            }
            else {
                imageId = reviewRepository.findById(usageId).get().getImageId();
            }
        }

        Image targetImage = imageRepository.findById(imageId).get();
        return targetImage.getName();
    }

    @Override
    public String getPrevImageId(String usage, String usageId) {

        if (usage.equals("facility")) {
            if (facilityRepository.findById(usageId).get().getImageId() == null) {
                return "";
            }
            else {
                return facilityRepository.findById(usageId).get().getImageId();
            }
        }

        else if (usage.equals("stamp")) {
            if (stampRepository.findById(usageId).get().getImageId() == null) {
                return "";
            }
            else {
                return stampRepository.findById(usageId).get().getImageId();
            }
        }

        else { // review
            if (reviewRepository.findById(usageId).get().getImageId() == null) {
                return "";
            }
            else {
                return reviewRepository.findById(usageId).get().getImageId();
            }
        }
    }

    @Override
    public String getFileNameByImageId(String imageId) {
        return imageRepository.findById(imageId).get().getName();
    }

    @Override
    public void deleteByImageId(String imageId) {
        imageRepository.deleteById(imageId);
    }

    @Override
    public void addImage(String usage, String usageId, String generatedImageId, String fileName) {

        ImageDto targetImageDto = ImageDto.builder()
                .id(generatedImageId)
                .name(fileName)
                .fileByteSize(0F)
                .fileLocation("")
                .updateDate(LocalDateTime.now())
                .lastUsedDate(LocalDateTime.now())
                .build();
        imageRepository.save(targetImageDto.toEntity());

        if (usage.equals("facility")) {
            Facility facility = facilityRepository.findById(usageId).get();
            facility.setImageId(generatedImageId);
            facilityRepository.save(facility);
        }

        else if (usage.equals("stamp")) {
            Stamp stamp = stampRepository.findById(usageId).get();
            stamp.setImageId(generatedImageId);
            stampRepository.save(stamp);
        }

        else { // qna
            Review review = reviewRepository.findById(usageId).get();
            review.setImageId(generatedImageId);
            reviewRepository.save(review);
        }
    }

    @Override
    public void deleteFK(String usage, String usageId) {

        if (usage.equals("facility")) {
            Facility facility = facilityRepository.findById(usageId).get();
            facility.setImageId(null);
            facilityRepository.save(facility);
        }

        else if (usage.equals("stamp")) {
            Stamp stamp = stampRepository.findById(usageId).get();
            stamp.setImageId(null);
            stampRepository.save(stamp);
        }

        else { // qna
            Review review = reviewRepository.findById(usageId).get();
            review.setImageId(null);
            reviewRepository.save(review);
        }
    }
}
