package com.example.fork.domain.__2__facility.service.impl;

import com.example.fork.domain.__2__facility.service.FacilityService;
import com.example.fork.global.data.dao.FacilityDao;
import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.etc.SortingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.awt.geom.Point2D.distance;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityDao facilityDao;

    @Autowired
    public FacilityServiceImpl(FacilityDao facilityDao) {
        this.facilityDao = facilityDao;
    }

    private List<FacilityDto> sortFacility(List<FacilityDto> facilityDtoList, String field, String sort, Float latitude, Float longitude) {
        if (sort.equals("asc")) {
            if (field.equals("distance")) {
                // TODO : DISTANCE SORT
                List<SortingDto> sortingDtoList = new ArrayList<>();
                for (FacilityDto f : facilityDtoList) {
                    double distance = distance((f.getLatitude()), f.getLongitude(), latitude, longitude);
                    SortingDto sortingDto = SortingDto.builder()
                            .id(f.getId())
                            .ref(distance).build();
                    sortingDtoList.add(sortingDto);
                }

                sortingDtoList = sortingDtoList
                        .stream()
                        .sorted(Comparator.comparing(SortingDto::getRef))
                        .collect(Collectors.toList());

                List<FacilityDto> response = new ArrayList<>();
                for (SortingDto s : sortingDtoList) {
                    for (FacilityDto f : facilityDtoList) {
                        if (s.getId().equals(f.getId())) {
                            response.add(f);
                        }
                    }
                }

                return response;
            }
        }

        else {
            if (field.equals("distance")) {
                // TODO : DISTANCE SORT
                // TODO : DISTANCE SORT
                List<SortingDto> sortingDtoList = new ArrayList<>();
                for (FacilityDto f : facilityDtoList) {
                    double distance = distance((f.getLatitude()), f.getLongitude(), latitude, longitude);
                    SortingDto sortingDto = SortingDto.builder()
                            .id(f.getId())
                            .ref(distance).build();
                    sortingDtoList.add(sortingDto);
                }

                sortingDtoList = sortingDtoList
                        .stream()
                        .sorted(Comparator.comparing(SortingDto::getRef).reversed())
                        .collect(Collectors.toList());

                List<FacilityDto> response = new ArrayList<>();
                for (SortingDto s : sortingDtoList) {
                    for (FacilityDto f : facilityDtoList) {
                        if (s.getId().equals(f.getId())) {
                            response.add(f);
                        }
                    }
                }

                return response;
            }
        }

        // TODO : EXCEPTION HANDLING
        return null;
    }

    @Override
    public void addFacility(String requestUserId, Map<String, Object> requestBody) {

        FacilityDto facilityDto = FacilityDto.builder()
                .id(UUID.randomUUID().toString())
                .businessId(requestBody.get("facility_business_id").toString())
                .name(requestBody.get("facility_name").toString())
                .nameEng(requestBody.get("facility_name_eng").toString())
                .description(requestBody.get("facility_description").toString())
                .descriptionEng((requestBody.get("facility_description_eng").toString()))
                .registerDate(LocalDateTime.now())
                .latitude(Float.valueOf(requestBody.get("facility_latitude").toString()))
                .longitude(Float.valueOf(requestBody.get("facility_longitude").toString()))
                .address(requestBody.get("facility_address").toString())
                .tag(Integer.valueOf(requestBody.get("facility_tag").toString()))
                .open(1)
                .maxStamp(Integer.valueOf(requestBody.get("facility_max_stamp").toString()))
                .userId(requestUserId)
                .imageId(null)
                .build();

        facilityDao.addFacility(facilityDto);
    }

    @Override
    public List<FacilityDto> getFacilityList(String field, String sort, Float latitude, Float longitude) {

        List<FacilityDto> responseList = facilityDao.getFacilityList();

        if (sort.equals("asc")) {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(FacilityDto::getRegisterDate))
                        .collect(Collectors.toList());
            }

            else {
                return sortFacility(responseList, field, sort, latitude, longitude);
            }
        }

        else {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(FacilityDto::getRegisterDate).reversed())
                        .collect(Collectors.toList());
            }

            else {
                return sortFacility(responseList, field, sort, latitude, longitude);
            }
        }
        // TODO : EXCEPTION HANDLING
    }

    @Override
    public FacilityDto getFacility(String facilityId) {
        return facilityDao.getFacility(facilityId);
    }

    @Override
    public void editFacility(String facilityId, Map<String, Object> requestBody) {

        FacilityDto facilityDto = facilityDao.getFacility(facilityId);
        facilityDto.setName(requestBody.get("facility_name").toString());
        facilityDto.setNameEng(requestBody.get("facility_name_eng").toString());
        facilityDto.setDescription(requestBody.get("facility_description").toString());
        facilityDto.setDescriptionEng((requestBody.get("facility_description_eng").toString()));
        facilityDto.setLatitude(Float.valueOf(requestBody.get("facility_latitude").toString()));
        facilityDto.setLongitude(Float.valueOf(requestBody.get("facility_longitude").toString()));
        facilityDto.setAddress(requestBody.get("facility_address").toString());
        facilityDto.setTag(Integer.valueOf(requestBody.get("facility_tag").toString()));
        facilityDto.setOpen(Integer.valueOf(requestBody.get("facility_open").toString()));

        facilityDao.editFacility(facilityDto);
    }

    @Override
    public void deleteFacility(String facilityId) {
        facilityDao.deleteFacility(facilityId);
    }
}
