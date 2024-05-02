package com.example.fork.global.data.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name = "fk_user_id")
    String userId;

    @Getter
    @Column(name = "fk_facility_id")
    String facilityId;
}
