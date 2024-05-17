package com.example.fork.domain.__4__stamp.service;

import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.StampDto;

import java.util.List;
import java.util.Map;

public interface StampService {

    void addStamp(String requestUserId, Map<String, Object> requestBody);
    List<StampDto> getStampList(String field, String sort);
    StampDto getStamp(String stampId);
    void editStamp(String stampId, Map<String, Object> requestBody);
    void deleteStamp(String stampId);
}
