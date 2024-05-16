package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Bookmark;
import com.example.fork.global.data.entity.Facility;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class BookmarkDto {

    private String id;
    private LocalDateTime registerDate;
    private String userId;
    private String facilityId;

    public Bookmark toEntity() {
        return Bookmark.builder()
                .id(id)
                .registerDate(registerDate)
                .facilityId(facilityId)
                .userId(userId)
                .build();
    }
}
