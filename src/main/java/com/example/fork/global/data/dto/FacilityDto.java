package com.example.fork.global.data.dto;

import com.example.fork.global.data.dto.etc.LeaderboardDto;
import com.example.fork.global.data.entity.Facility;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class FacilityDto {

    private String id;
    private String businessId;
    private String name;
    private String nameEng;
    private String description;
    private String descriptionEng;
    private LocalDateTime registerDate;
    private Float latitude;
    private Float longitude;
    private String address;
    private Integer tag;
    private Integer open;
    private Integer maxStamp;
    private String userId;
    private String imageId;

    public Facility toEntity() {
        return Facility.builder()
                .id(id)
                .businessId(businessId)
                .name(name)
                .nameEng(nameEng)
                .description(description)
                .descriptionEng(descriptionEng)
                .registerDate(registerDate)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .tag(tag)
                .open(open)
                .maxStamp(maxStamp)
                .userId(userId)
                .imageId(imageId)
                .build();
    }

    public LeaderboardDto toLeaderboardDto(Double avg_score, Integer reviewNum) {
        return LeaderboardDto.builder()
                .name(name)
                .nameEng(nameEng)
                .description(description)
                .descriptionEng(descriptionEng)
                .address(address)
                .avg_score(avg_score)
                .imageId(imageId)
                .reviewNum(reviewNum)
                .build();
    }
}
