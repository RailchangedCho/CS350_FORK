package com.example.fork.global.data.dto;

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
}
