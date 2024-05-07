package com.example.fork.global.data.entity;

import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.ReportDto;
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
@Table(name = "reports")
public class Report {

    @Id
    @Column(name = "report_id")
    String id;

    @Getter
    @Column(name = "report_text")
    String text;

    @Getter
    @Column(name = "report_type")
    Integer type;

    @Getter
    @Column(name = "fk_users_id")
    String userId;

    @Getter
    @Column(name = "fk_reviews_id")
    String reviewId;

    public ReportDto toDto() {
        return ReportDto.builder()
                .id(id)
                .text(text)
                .type(type)
                .userId(userId)
                .reviewId(reviewId)
                .build();
    }
}
