package com.mynickel.repository;

import com.mynickel.model.FinancialReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialReportRepository extends CrudRepository<FinancialReport, Long> {
    List<FinancialReport> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    List<FinancialReport> findByCategory(String category);
}