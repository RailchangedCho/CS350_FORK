package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Review;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class ReviewDto {

    private String id;
    private String text;
    private Integer score;
    private Integer hashtag;
    private LocalDateTime registerDate;
    private String userId;
    private String facilityId;
    private String imageId;

    public Review toEntity() {
        return Review.builder()
                .id(id)
                .text(text)
                .score(score)
                .hashtag(hashtag)
                .registerDate(registerDate)
                .userId(userId)
                .facilityId(facilityId)
                .imageId(imageId)
                .build();
    }
}
