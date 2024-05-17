package com.example.fork.global.data.dao;

import com.example.fork.global.data.dto.FacilityDto;
import com.example.fork.global.data.dto.ReportDto;

import java.util.List;

public interface ReportDao {

    List<ReportDto> getReportList();
    ReportDto getReport(String reportId);
    void addReport(ReportDto reportDto);
    void editReport(ReportDto reportDto);
    void deleteReport(String reportId);
}
