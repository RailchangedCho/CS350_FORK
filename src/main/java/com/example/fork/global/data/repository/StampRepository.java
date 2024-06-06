package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StampRepository extends JpaRepository<Stamp, String> {

    List<Stamp> findAllByUserId(String userId);
    List<Stamp> findAllByFacilityId(String facilityId);
}
