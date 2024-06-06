package com.example.fork.domain.__2__facility.service;

import com.example.fork.global.data.dto.FacilityDto;

import java.util.List;
import java.util.Map;

public interface FacilityService {

    String addFacility(String requestUserId, Map<String, Object> requestBody);
    List<FacilityDto> getFacilityList(String field, String sort, Float latitude, Float longitude);
    FacilityDto getFacility(String facilityId);
    FacilityDto getFacilityByUserId(String userId);
    void editFacility(String facilityId, Map<String, Object> requestBody);
    void editFacilityOwner(String facilityId, String userId);
    void deleteFacility(String facilityId);
    Double getAverageReviewScore(String facilityId);
    Integer getTotalReviewNumber(String facilityId);
}
