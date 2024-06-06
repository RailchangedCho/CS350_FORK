package com.example.fork.global.data.dao;

import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.StampDto;

import java.util.List;

public interface StampDao {

    List<StampDto> getStampList();
    List<StampDto> getStampListByUserId(String userId);
    List<StampDto> getStampListByFacilityId(String facilityId);
    StampDto getStamp(String stampId);
    void addStamp(StampDto stampDto);
    void editStamp(StampDto stampDto);
    void deleteStamp(String stampId);
}
