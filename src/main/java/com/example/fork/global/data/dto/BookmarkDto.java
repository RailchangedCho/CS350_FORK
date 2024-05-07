package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Bookmark;
import com.example.fork.global.data.entity.Facility;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class BookmarkDto {

    private String id;
    private String userId;
    private String facilityId;

    public Bookmark toEntity() {
        return Bookmark.builder()
                .id(id)
                .facilityId(facilityId)
                .userId(userId)
                .build();
    }
}
