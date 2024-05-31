package com.example.fork.global.data.entity;

import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.ReviewDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "facilities")
public class Facility {

    @Id
    @Column(name = "facility_id")
    String id;

    @Getter
    @Column(name = "facliity_business_id")
    String businessId;

    @Getter
    @Column(name = "facility_name")
    String name;

    @Getter
    @Column(name = "facility_name_eng")
    String nameEng;

    @Getter
    @Column(name = "facility_description")
    String description;

    @Getter
    @Column(name = "facility_description_eng")
    String descriptionEng;

    @Getter
    @Column(name = "facility_register_date")
    LocalDateTime registerDate;

    @Getter
    @Column(name = "facility_latitude")
    Float latitude;

    @Getter
    @Column(name = "facility_longitude")
    Float longitude;

    @Getter
    @Column(name = "facility_address")
    String address;

    @Getter
    @Column(name = "facility_tag")
    Integer tag;

    @Getter
    @Column(name = "facility_open")
    Integer open;

    @Getter
    @Column(name = "facility_max_stamp")
    Integer maxStamp;

    @Getter
    @Column(name = "fk_users_id")
    String userId;

    @Getter
    @Column(name = "fk_images_id")
    String imageId;

    public FacilityDto toDto() {
        return FacilityDto.builder()
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
}
