package com.mynickel.service;

import com.mynickel.model.FinancialGoal;
import com.mynickel.repository.FinancialGoalRepository;

public class FinancialGoalService {
    private final FinancialGoalRepository financialGoalRepository;
    private final FinancialGoalReportService reportService;

    public FinancialGoalService(FinancialGoalRepository financialGoalRepository, FinancialGoalReportService reportService) {
        this.financialGoalRepository = financialGoalRepository;
        this.reportService = reportService;
    }

    public FinancialGoal createGoal(FinancialGoal financialGoal) {
        return financialGoalRepository.save(financialGoal);
    }

    public FinancialGoal getGoal(Long id) {
        return financialGoalRepository.findById(id).orElse(null);
    }

    public FinancialGoal updateGoal(Long id, FinancialGoal financialGoal) {
        if (financialGoalRepository.existsById(id)) {
            financialGoal.setId(id);
            return financialGoalRepository.save(financialGoal);
        }
        return null;
    }

    public void deleteGoal(Long id) {
        financialGoalRepository.deleteById(id);
    }
    public FinancialGoalReportService.GoalProgressReport generateGoalProgressReport() {
        return reportService.generateGoalProgressReport();
    }

    public FinancialGoalReportService.GoalDetailReport generateGoalDetailReport(Long id) {
        return reportService.generateGoalDetailReport(id);
    }
}
