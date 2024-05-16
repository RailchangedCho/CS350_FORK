package com.example.fork.global.data.entity;

import com.example.fork.global.data.dto.StampDto;
import com.example.fork.global.data.dto.UserDto;
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
@Table(name = "stamps")
public class Stamp {

    @Id
    @Column(name = "stamp_id")
    String id;

    @Getter
    @Column(name = "stamp_num")
    Integer num;

    @Getter
    @Column(name = "stamp_register_date")
    LocalDateTime registerDate;

    @Getter
    @Column(name = "fk_images_id")
    String imageId;

    @Getter
    @Column(name = "fk_users_id")
    String userId;

    @Getter
    @Column(name = "fk_facilities_id")
    String facilityId;

    public StampDto toDto() {
        return StampDto.builder()
                .id(id)
                .num(num)
                .registerDate(registerDate)
                .imageId(imageId)
                .userId(userId)
                .facilityId(facilityId)
                .build();
    }
}
