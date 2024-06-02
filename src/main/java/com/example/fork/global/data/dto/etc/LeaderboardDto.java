package com.example.fork.global.data.dto.etc;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class LeaderboardDto {

    String name;
    String nameEng;
    String description;
    String descriptionEng;
    String address;
    Double avg_score;
    String imageId;
    Integer reviewNum;
}
