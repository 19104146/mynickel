package com.mynickel.service;

import com.mynickel.model.FinancialReport;
import com.mynickel.repository.FinancialReportRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinancialReportService {
    private final FinancialReportRepository financialReportRepository;

    public FinancialReportService(FinancialReportRepository financialReportRepository) {
        this.financialReportRepository = financialReportRepository;
    }

    public FinancialReport createReport(FinancialReport report) {
        return financialReportRepository.save(report);
    }

    public FinancialReport getReportById(Long id) {
        return financialReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Financial Report not found"));
    }

    public FinancialReportSummary generateReportSummary(LocalDate startDate, LocalDate endDate) {
        List<FinancialReport> reports = financialReportRepository
                .findByStartDateBetween(startDate, endDate);

        return new FinancialReportSummary(reports);
    }

    public static class FinancialReportSummary {
        private final List<FinancialReport> reports;
        private final BigDecimal totalIncome;
        private final BigDecimal totalExpenses;
        private final BigDecimal netBalance;
        private final Map<String, BigDecimal> categorySummary;

        public FinancialReportSummary(List<FinancialReport> reports) {
            this.reports = reports;
            this.totalIncome = calculateTotalIncome();
            this.totalExpenses = calculateTotalExpenses();
            this.netBalance = calculateNetBalance();
            this.categorySummary = generateCategorySummary();
        }

        private BigDecimal calculateTotalIncome() {
            return reports.stream()
                    .map(FinancialReport::getTotalIncome)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        private BigDecimal calculateTotalExpenses() {
            return reports.stream()
                    .map(FinancialReport::getTotalExpenses)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        private BigDecimal calculateNetBalance() {
            return totalIncome.subtract(totalExpenses);
        }

        private Map<String, BigDecimal> generateCategorySummary() {
            return reports.stream()
                    .flatMap(report -> report.getCategoryBreakdown().entrySet().stream())
                    .collect(Collectors.groupingBy(
                            Map.Entry::getKey,
                            Collectors.reducing(
                                    BigDecimal.ZERO,
                                    Map.Entry::getValue,
                                    BigDecimal::add
                            )
                    ));
        }

        public BigDecimal getTotalIncome() {
            return totalIncome;
        }

        public BigDecimal getTotalExpenses() {
            return totalExpenses;
        }

        public BigDecimal getNetBalance() {
            return netBalance;
        }

        public Map<String, BigDecimal> getCategorySummary() {
            return categorySummary;
        }
    }
}