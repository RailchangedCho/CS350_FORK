package com.example.fork.domain.__6__system.service;

import java.util.Map;

public interface SystemService {

    void langExchange(String requestUserId, String targetLanguage);
    String summaryReview(String reviewId);
    void addReport(Integer type, String requestUserId, String reviewId, Map<String, Object> requestBody);
    String translate(Map<String, Object> requestBody, String target);
}
