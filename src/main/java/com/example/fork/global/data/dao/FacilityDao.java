package com.example.fork.global.data.dao;

import com.example.fork.global.data.dto.FacilityDto;

import java.util.List;

public interface FacilityDao {

    List<FacilityDto> getFacilityList();
    FacilityDto getFacility(String facilityId);
    FacilityDto getFacilityByUserId(String userId);
    void addFacility(FacilityDto facilityDto);
    void editFacility(FacilityDto facilityDto);
    void deleteFacility(String facilityId);
}
