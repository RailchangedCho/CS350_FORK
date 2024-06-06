package com.example.fork.domain.__6__system.service.impl;

import com.example.fork.domain.__6__system.service.SystemService;
import com.example.fork.global.data.dao.ReportDao;
import com.example.fork.global.data.dao.ReviewDao;
import com.example.fork.global.data.dao.UserDao;
import com.example.fork.global.data.dto.ReportDto;
import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.StampDto;
import com.example.fork.global.data.dto.UserDto;
import com.example.fork.global.data.entity.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService {

    private final UserDao userDao;
    private final ReportDao reportDao;
    private final ReviewDao reviewDao;

    @Autowired
    public SystemServiceImpl(UserDao userDao,
                             ReportDao reportDao,
                             ReviewDao reviewDao) {
        this.userDao = userDao;
        this.reportDao = reportDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public void langExchange(String requestUserId, String targetLanguage) {

        UserDto userDto = userDao.getUser(requestUserId);
        userDto.setDefaultLanguage(targetLanguage);
        userDao.editUser(userDto);
    }

    @Override
    public String summaryReview(String facilityId) {

        List<ReviewDto> reviewDtoList = reviewDao.getReviewListByFacilityId(facilityId);
        String summaryText = "";
        for (ReviewDto r : reviewDtoList) {
            if (r.getText() != null) {
                summaryText = summaryText + ", " + r.getText();
            }
        }

        summaryText = summaryText + ".";
        System.out.println(summaryText);

        // String test = "그건 좀 아닌듯한데요, 나는 피자를 좋아합니다.";

        Map<String, Object> document = new HashMap<>();
        document.put("title", "title");
        document.put("content", summaryText);

        Map<String, Object> option = new HashMap<>();
        option.put("language", "ko");
        option.put("model", "general");
        option.put("tone", 0);
        option.put("summaryCount", 1);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("document", document);
        requestBody.put("option", option);

        System.out.println(requestBody);

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-NCP-APIGW-API-KEY-ID", "gnp968w593");
        headers.add("X-NCP-APIGW-API-KEY", "iALIybfAM3oxEt61p50eqchWK2kgRYufrzZgm8bG");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> naverSummaryRequest = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> summaryResponse;
        try {
            summaryResponse = rt.exchange(
                    "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize",
                    HttpMethod.POST,
                    naverSummaryRequest,
                    String.class
            );
        }
        catch (HttpClientErrorException e) {
            return reviewDtoList.get(0).getText();
        }

        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = new ObjectMapper().readValue(summaryResponse.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            e.getStackTrace();
        }

        return resultMap.get("summary").toString();
    }

    @Override
    public void addReport(Integer type, String requestUserId, String reviewId, Map<String, Object> requestBody) {

        // System
        if (type == 0) {
            System.out.println("system report");
            ReportDto reportDto = ReportDto.builder()
                    .id(UUID.randomUUID().toString())
                    .text(requestBody.get("report_text").toString())
                    .type(0)
                    .registerDate(LocalDateTime.now())
                    .userId(requestUserId)
                    .reviewId(null)
                    .build();

            reportDao.addReport(reportDto);
        }

        // Review
        else {
            System.out.println("review report");
            ReportDto reportDto = ReportDto.builder()
                    .id(UUID.randomUUID().toString())
                    .text(requestBody.get("report_text").toString())
                    .type(1)
                    .registerDate(LocalDateTime.now())
                    .userId(requestUserId)
                    .reviewId(reviewId)
                    .build();

            reportDao.addReport(reportDto);
        }
    }

    @Override
    public String translate(Map<String, Object> requestBody, String target) {

        String content = requestBody.get("content").toString();

        Map<String, Object> httpRequestBody = new HashMap<>();
        if (target.equals("KOR")) {
            httpRequestBody.put("source", "en");
            httpRequestBody.put("target", "ko");
        }
        else {
            httpRequestBody.put("source", "ko");
            httpRequestBody.put("target", "en");
        }
        httpRequestBody.put("text", content);

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-NCP-APIGW-API-KEY-ID", "ywkuqewsba");
        headers.add("X-NCP-APIGW-API-KEY", "7zTloozAUH0yqFk9rEHSzJAsit7pMFPpbcN2QL0E");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> naverSummaryRequest = new HttpEntity<>(httpRequestBody, headers);

        ResponseEntity<String> summaryResponse = rt.exchange(
                "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation",
                HttpMethod.POST,
                naverSummaryRequest,
                String.class
        );

        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = new ObjectMapper().readValue(summaryResponse.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            e.getStackTrace();
        }

        Map<String, Object> result = (Map<String, Object>) resultMap.get("message");
        Map<String, Object> translatedText = (Map<String, Object>) result.get("result");

        return translatedText.get("translatedText").toString();
    }

    @Override
    public List<ReportDto> getReportList(String field, String sort) {

        List<ReportDto> responseList = reportDao.getReportList();

        if (sort.equals("asc")) {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(ReportDto::getRegisterDate))
                        .collect(Collectors.toList());
            }
        }

        else {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(ReportDto::getRegisterDate).reversed())
                        .collect(Collectors.toList());
            }
        }

        // TODO : EXCEPTION HANDLING
        return null;
    }

    @Override
    public ReportDto getReport(String reportId) {
        return reportDao.getReport(reportId);
    }

    @Override
    public void deleteReport(String reportId) {
        reportDao.deleteReport(reportId);
    }
}
