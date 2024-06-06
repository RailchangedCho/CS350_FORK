package com.example.fork.domain.__6__system.service;

import com.example.fork.global.data.dto.ReportDto;

import java.util.List;
import java.util.Map;

public interface SystemService {

    void langExchange(String requestUserId, String targetLanguage);
    String summaryReview(String facilityId);
    void addReport(Integer type, String requestUserId, String reviewId, Map<String, Object> requestBody);
    String translate(Map<String, Object> requestBody, String target);
    List<ReportDto> getReportList(String field, String sort);
    ReportDto getReport(String reportId);
}
