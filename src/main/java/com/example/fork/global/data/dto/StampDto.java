package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Stamp;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class StampDto {

    private String id;
    private Integer num;
    private String userId;
    private String facilityId;

    public Stamp toEntity() {
        return Stamp.builder()
                .id(id)
                .num(num)
                .userId(userId)
                .facilityId(facilityId)
                .build();
    }
}
