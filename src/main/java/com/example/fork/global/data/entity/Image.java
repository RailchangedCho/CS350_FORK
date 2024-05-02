package com.example.fork.global.data.entity;

import com.example.fork.global.data.dto.ImageDto;
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
@Table(name = "images")
public class Image {

    @Id
    @Column(name = "image_id")
    String id;

    @Getter
    @Column(name = "image_name")
    String name;

    @Getter
    @Column(name = "image_file_byte_size")
    Float fileByteSize;

    @Getter
    @Column(name = "image_file_location")
    String fileLocation;

    @Getter
    @Column(name = "image_last_used_date")
    LocalDateTime lastUsedDate;

    @Getter
    @Column(name = "image_update_date")
    LocalDateTime updateDate;

    public ImageDto toDto() {
        return ImageDto.builder()
                .id(id)
                .name(name)
                .fileByteSize(fileByteSize)
                .fileLocation(fileLocation)
                .lastUsedDate(lastUsedDate)
                .updateDate(updateDate)
                .build();
    }
}
