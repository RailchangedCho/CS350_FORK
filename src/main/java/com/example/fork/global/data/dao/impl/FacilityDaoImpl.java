package com.example.fork.global.data.dao.impl;

import com.example.fork.global.data.dao.FacilityDao;
import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.entity.Facility;
import com.example.fork.global.data.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityDaoImpl implements FacilityDao {

    FacilityRepository facilityRepository;

    @Autowired
    public FacilityDaoImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<FacilityDto> getFacilityList() {
        List<FacilityDto> facilityDtoList = new ArrayList<>();
        List<Facility> facilityList = facilityRepository.findAll();
        for (Facility f : facilityList) {
            facilityDtoList.add(f.toDto());
        }
        return facilityDtoList;
    }

    @Override
    public FacilityDto getFacility(String facilityId) {
        Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);
        return optionalFacility.map(Facility::toDto).orElse(null);
    }

    @Override
    public void addFacility(FacilityDto facilityDto) {
        if(facilityRepository.findById(facilityDto.getId()).isEmpty()) {
            facilityRepository.save(facilityDto.toEntity());
        }
    }

    @Override
    public void editFacility(FacilityDto facilityDto) {
        if(facilityRepository.findById(facilityDto.getId()).isPresent()) {
            facilityRepository.save(facilityDto.toEntity());
        }
    }

    @Override
    public void deleteFacility(String facilityId) {
        Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);
        if(optionalFacility.isPresent()) {
            Facility facility = optionalFacility.get();
            facilityRepository.delete(facility);
        }
    }
}
