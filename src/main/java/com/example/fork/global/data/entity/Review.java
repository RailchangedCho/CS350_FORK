package com.example.fork.global.data.entity;

import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.UserDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "review_id")
    String id;

    @Getter
    @Column(name = "review_text")
    String text;

    @Getter
    @Column(name = "review_score")
    Integer score;

    @Getter
    @Column(name = "review_hashtag")
    String hashtag;

    @Getter
    @Column(name = "fk_users_id")
    String userId;

    @Getter
    @Column(name = "fk_facilities_id")
    String facilityId;

    @Getter
    @Column(name = "fk_images_id")
    String imageId;

    public ReviewDto toDto() {
        return ReviewDto.builder()
                .id(id)
                .text(text)
                .score(score)
                .hashtag(hashtag)
                .userId(userId)
                .facilityId(facilityId)
                .imageId(imageId)
                .build();
    }
}
