package com.example.fork.global.data.dao.impl;

import com.example.fork.global.data.dao.StampDao;
import com.example.fork.global.data.dto.ReviewDto;
import com.example.fork.global.data.dto.StampDto;
import com.example.fork.global.data.entity.Review;
import com.example.fork.global.data.entity.Stamp;
import com.example.fork.global.data.repository.ReviewRepository;
import com.example.fork.global.data.repository.StampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StampDaoImpl implements StampDao {

    StampRepository stampRepository;

    @Autowired
    public StampDaoImpl(StampRepository stampRepository) {
        this.stampRepository = stampRepository;
    }

    @Override
    public List<StampDto> getStampList() {
        List<StampDto> stampDtoList = new ArrayList<>();
        List<Stamp> stampList = stampRepository.findAll();
        for (Stamp s : stampList) {
            stampDtoList.add(s.toDto());
        }
        return stampDtoList;
    }

    @Override
    public StampDto getStamp(String stampId) {
        Optional<Stamp> optionalStamp = stampRepository.findById(stampId);
        return optionalStamp.map(Stamp::toDto).orElse(null);
    }

    @Override
    public void addStamp(StampDto stampDto) {
        if(stampRepository.findById(stampDto.getId()).isEmpty()) {
            stampRepository.save(stampDto.toEntity());
        }
    }

    @Override
    public void editStamp(StampDto stampDto) {
        if(stampRepository.findById(stampDto.getId()).isPresent()) {
            stampRepository.save(stampDto.toEntity());
        }
    }

    @Override
    public void deleteStamp(String stampId) {
        Optional<Stamp> optionalStamp = stampRepository.findById(stampId);
        if(optionalStamp.isPresent()) {
            Stamp stamp = optionalStamp.get();
            stampRepository.delete(stamp);
        }
    }
}
