package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Facility;
import com.example.fork.global.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, String> {

    Optional<Facility> findByUserId(String userId);
}
