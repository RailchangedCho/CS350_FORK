package com.example.fork.global.data.dao.impl;

import com.example.fork.global.data.dao.ReportDao;
import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.ReportDto;
import com.example.fork.global.data.entity.Facility;
import com.example.fork.global.data.entity.Report;
import com.example.fork.global.data.repository.FacilityRepository;
import com.example.fork.global.data.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportDaoImpl implements ReportDao {

    ReportRepository reportRepository;

    @Autowired
    public ReportDaoImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportDto> getReportList() {
        List<ReportDto> reportDtoList = new ArrayList<>();
        List<Report> reportList = reportRepository.findAll();
        for (Report r : reportList) {
            reportDtoList.add(r.toDto());
        }
        return reportDtoList;
    }

    @Override
    public List<ReportDto> getReportListByReview(String reviewId) {
        List<ReportDto> reportDtoList = new ArrayList<>();
        List<Report> reportList = reportRepository.findAllByReviewId(reviewId);
        for (Report r : reportList) {
            reportDtoList.add(r.toDto());
        }
        return reportDtoList;
    }

    @Override
    public ReportDto getReport(String reportId) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        return optionalReport.map(Report::toDto).orElse(null);
    }

    @Override
    public void addReport(ReportDto reportDto) {
        if(reportRepository.findById(reportDto.getId()).isEmpty()) {
            reportRepository.save(reportDto.toEntity());
        }
    }

    @Override
    public void editReport(ReportDto reportDto) {
        if(reportRepository.findById(reportDto.getId()).isPresent()) {
            reportRepository.save(reportDto.toEntity());
        }
    }

    @Override
    public void deleteReport(String reportId) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if(optionalReport.isPresent()) {
            Report report = optionalReport.get();
            reportRepository.delete(report);
        }
    }
}
