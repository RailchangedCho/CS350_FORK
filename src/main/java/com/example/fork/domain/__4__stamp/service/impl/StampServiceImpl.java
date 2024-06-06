package com.example.fork.domain.__4__stamp.service.impl;

import com.example.fork.domain.__4__stamp.service.StampService;
import com.example.fork.global.data.dao.FacilityDao;
import com.example.fork.global.data.dao.StampDao;
import com.example.fork.global.data.dao.UserDao;
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
    private final UserDao userDao;

    @Autowired
    public StampServiceImpl(StampDao stampDao,
                            FacilityDao facilityDao,
                            UserDao userDao) {
        this.stampDao = stampDao;
        this.facilityDao = facilityDao;
        this.userDao = userDao;
    }

    private String findIdByName(String name) {
        return userDao.getUserByName(name).getId();
    }

    private String findNameById(String id) {
        return userDao.getUser(id).getName();
    }

    @Override
    public void addStamp(String requestUserId, Map<String, Object> requestBody) {

        String targetFacilityId = facilityDao.getFacilityByUserId(requestUserId).getId();

        StampDto stampDto = StampDto.builder()
                .id(UUID.randomUUID().toString())
                .num(1)
                .registerDate(LocalDateTime.now())
                .imageId(facilityDao.getFacilityByUserId(requestUserId).getImageId())
                .userId(findIdByName(requestBody.get("user_name").toString()))
                .facilityId(targetFacilityId)
                .build();

        stampDao.addStamp(stampDto);
    }

    @Override
    public List<StampDto> getStampList(String field, String sort) {

        List<StampDto> responseList = stampDao.getStampList();
        for (StampDto s : responseList) {
            s.setUserId(findNameById(s.getUserId()));
        }

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
    public List<StampDto> getStampListForUser(String field, String sort, String userId) {

        List<StampDto> responseList = stampDao.getStampListByUserId(userId);
        for (StampDto s : responseList) {
            s.setUserId(findNameById(s.getUserId()));
        }

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
    public List<StampDto> getStampListForFacility(String field, String sort, String facilityId) {

        List<StampDto> responseList = stampDao.getStampListByFacilityId(facilityId);
        for (StampDto s : responseList) {
            s.setUserId(findNameById(s.getUserId()));
        }

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
        StampDto stampDto = stampDao.getStamp(stampId);
        stampDto.setUserId(findNameById(stampDto.getUserId()));
        return stampDto;
    }

    @Override
    public void editStamp(String stampId, Map<String, Object> requestBody) {

        StampDto stampDto = stampDao.getStamp(stampId);
        stampDto.setNum(Integer.valueOf(requestBody.get("stamp_num").toString()));
        //stampDto.setImageId(requestBody.get("fk_images_id").toString());

        stampDao.editStamp(stampDto);
    }

    @Override
    public void deleteStamp(String stampId) {
        stampDao.deleteStamp(stampId);
    }


    @Override
    public Boolean stampAlreadyExist(String userId) {
        if (stampDao.getStampListByUserId(userId) != null) {
            return true;
        }
        else {
             return false;
        }
    }
}
