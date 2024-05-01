package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, String> {
}
