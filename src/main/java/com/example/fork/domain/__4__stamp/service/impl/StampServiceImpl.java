package com.example.fork.domain.__4__stamp.service.impl;

import com.example.fork.domain.__4__stamp.service.StampService;
import com.example.fork.global.data.dao.FacilityDao;
import com.example.fork.global.data.dao.StampDao;
import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.StampDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StampServiceImpl implements StampService {

    private final StampDao stampDao;
    private final FacilityDao facilityDao;

    @Autowired
    public StampServiceImpl(StampDao stampDao,
                            FacilityDao facilityDao) {
        this.stampDao = stampDao;
        this.facilityDao = facilityDao;
    }

    @Override
    public void addStamp(String requestUserId, Map<String, Object> requestBody) {

        String targetFacilityId = facilityDao.getFacilityByUserId(requestUserId).getId();

        StampDto stampDto = StampDto.builder()
                .id(UUID.randomUUID().toString())
                .num(0)
                .registerDate(LocalDateTime.now())
                .imageId(null)
                .userId(requestUserId)
                .facilityId(targetFacilityId)
                .build();

        stampDao.addStamp(stampDto);
    }

    @Override
    public List<StampDto> getStampList(String field, String sort) {

        List<StampDto> responseList = stampDao.getStampList();

        if (sort.equals("asc")) {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(StampDto::getRegisterDate))
                        .collect(Collectors.toList());
            }
        }

        else {
            if (field.equals("date")) {
                return responseList
                        .stream()
                        .sorted(Comparator.comparing(StampDto::getRegisterDate).reversed())
                        .collect(Collectors.toList());
            }
        }

        // TODO : EXCEPTION HANDLING
        return null;
    }

    @Override
    public StampDto getStamp(String stampId) {
        return stampDao.getStamp(stampId);
    }

    @Override
    public void editStamp(String stampId, Map<String, Object> requestBody) {

        StampDto stampDto = stampDao.getStamp(stampId);
        stampDto.setNum(Integer.valueOf(requestBody.get("stamp_num").toString()));
        stampDto.setImageId(requestBody.get("fk_images_id").toString());

        stampDao.editStamp(stampDto);
    }

    @Override
    public void deleteStamp(String stampId) {
        stampDao.deleteStamp(stampId);
    }
}
