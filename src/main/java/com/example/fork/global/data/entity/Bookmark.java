package com.example.fork.global.data.entity;

import com.example.fork.global.data.dto.BookmarkDto;
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
@Table(name = "bookmarks")
public class Bookmark {

    @Id
    @Column(name = "bookmark_id")
    String id;

    @Getter
    @Column(name = "bookmark_register_date")
    LocalDateTime registerDate;

    @Getter
    @Column(name = "fk_users_id")
    String userId;

    @Getter
    @Column(name = "fk_facilities_id")
    String facilityId;

    public BookmarkDto toDto() {
        return BookmarkDto.builder()
                .id(id)
                .registerDate(registerDate)
                .facilityId(facilityId)
                .userId(userId)
                .build();
    }
}
