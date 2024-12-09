package com.mynickel.controller;

import com.mynickel.model.FinancialReport;
import com.mynickel.service.FinancialReportService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
public class FinancialReportController {
    private final FinancialReportService financialReportService;

    public FinancialReportController(FinancialReportService financialReportService) {
        this.financialReportService = financialReportService;
    }

    @PostMapping
    public FinancialReport createReport(@RequestBody FinancialReport report) {
        return financialReportService.createReport(report);
    }

    @GetMapping("/{id}")
    public FinancialReport getReport(@PathVariable Long id) {
        return financialReportService.getReportById(id);
    }

    @GetMapping("/summary")
    public FinancialReportService.FinancialReportSummary getReportSummary(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return financialReportService.generateReportSummary(startDate, endDate);
    }
}