package com.mynickel.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FinancialReport {
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;

    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal netBalance;

    private List<Transaction> transactions;
    private Map<String, BigDecimal> categoryBreakdown;

    public Map<String, BigDecimal> generateCategoryBreakdown() {
        // Add logic to break down expenses by category
        return categoryBreakdown;
    }
}
