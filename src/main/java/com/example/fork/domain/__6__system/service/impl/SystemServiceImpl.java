package com.example.fork.domain.__6__system.service.impl;

import com.example.fork.domain.__6__system.service.SystemService;
import com.example.fork.global.data.dao.ReportDao;
import com.example.fork.global.data.dao.ReviewDao;
import com.example.fork.global.data.dao.UserDao;
import com.example.fork.global.data.dto.ReportDto;
import com.example.fork.global.data.dto.UserDto;
import com.example.fork.global.data.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class SystemServiceImpl implements SystemService {

    private final UserDao userDao;
    private final ReportDao reportDao;

    @Autowired
    public SystemServiceImpl(UserDao userDao,
                             ReportDao reportDao) {
        this.userDao = userDao;
        this.reportDao = reportDao;
    }

    @Override
    public void langExchange(String requestUserId, String targetLanguage) {

        UserDto userDto = userDao.getUser(requestUserId);
        userDto.setDefaultLanguage(targetLanguage);
        userDao.editUser(userDto);
    }

    @Override
    public void addReport(Integer type, String requestUserId, String reviewId, Map<String, Object> requestBody) {

        // System
        if (type == 0) {
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
}
