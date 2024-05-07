package com.example.fork.global.data.repository;

import com.example.fork.global.data.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, String> {
}
