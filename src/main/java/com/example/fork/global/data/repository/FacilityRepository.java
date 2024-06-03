package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Facility;
import com.example.fork.global.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {

    Optional<Facility> findByUserId(String userId);
}
