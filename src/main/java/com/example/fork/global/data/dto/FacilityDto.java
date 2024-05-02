package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Facility;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class FacilityDto {

    String id;
    String businessId;
    String name;
    String nameEng;
    String description;
    String descriptionEng;
    String tag;
    Integer open;
    Integer maxStamp;
    String userId;

    public Facility toEntity() {
        return Facility.builder()
                .id(id)
                .businessId(businessId)
                .name(name)
                .nameEng(nameEng)
                .description(description)
                .descriptionEng(descriptionEng)
                .tag(tag)
                .open(open)
                .maxStamp(maxStamp)
                .userId(userId)
                .build();
    }
}
