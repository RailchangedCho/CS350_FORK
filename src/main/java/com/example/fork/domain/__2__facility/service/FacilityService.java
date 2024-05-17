package com.example.fork.domain.__2__facility.service;

import com.example.fork.global.data.dto.FacilityDto;

import java.util.List;
import java.util.Map;

public interface FacilityService {

    void addFacility(String requestUserId, Map<String, Object> requestBody);
    List<FacilityDto> getFacilityList(String field, String sort);
    FacilityDto getFacility(String facilityId);
    void editFacility(String facilityId, Map<String, Object> requestBody);
    void deleteFacility(String facilityId);
}
