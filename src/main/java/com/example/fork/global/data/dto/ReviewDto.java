package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Review;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class ReviewDto {

    String id;
    String text;
    Integer score;
    String userId;
    String facilityId;
    String imageId;

    public Review toEntity() {
        return Review.builder()
                .id(id)
                .text(text)
                .score(score)
                .userId(userId)
                .facilityId(facilityId)
                .imageId(imageId)
                .build();
    }
}
