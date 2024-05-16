package com.example.fork.global.data.dto;

import com.example.fork.global.data.entity.Report;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
public class ReportDto {

    private String id;
    private String text;
    private Integer type;
    private LocalDateTime registerDate;
    private String userId;
    private String reviewId;

    public Report toEntity() {
        return Report.builder()
                .id(id)
                .text(text)
                .type(type)
                .registerDate(registerDate)
                .userId(userId)
                .reviewId(reviewId)
                .build();
    }
}
