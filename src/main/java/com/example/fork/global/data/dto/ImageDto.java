package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Image;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class ImageDto {

    String id;
    String name;
    Float fileByteSize;
    String fileLocation;
    LocalDateTime lastUsedDate;
    LocalDateTime updateDate;

    public Image toEntity() {
        return Image.builder()
                .id(id)
                .name(name)
                .fileByteSize(fileByteSize)
                .fileLocation(fileLocation)
                .lastUsedDate(lastUsedDate)
                .updateDate(updateDate)
                .build();
    }
}
