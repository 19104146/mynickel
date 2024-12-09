package com.mynickel.controller;

import com.mynickel.model.FinancialGoal;
import com.mynickel.service.FinancialGoalReportService;
import com.mynickel.service.FinancialGoalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goals")
public class FinancialGoalController {

    private final FinancialGoalService financialGoalService;

    public FinancialGoalController(FinancialGoalService financialGoalService) {
        this.financialGoalService = financialGoalService;
    }

    @PostMapping
    public FinancialGoal createGoal(@RequestBody FinancialGoal financialGoal) {
        return financialGoalService.createGoal(financialGoal);
    }

    @GetMapping("/{id}")
    public FinancialGoal getGoal(@PathVariable Long id) {
        return financialGoalService.getGoal(id);
    }

    @PutMapping("/{id}")
    public FinancialGoal updateGoal(@PathVariable Long id, @RequestBody FinancialGoal financialGoal) {
        return financialGoalService.updateGoal(id, financialGoal);
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable Long id) {
        financialGoalService.deleteGoal(id);
    }

    @GetMapping("/report")
    public FinancialGoalReportService.GoalProgressReport getOverallGoalReport() {
        return financialGoalService.generateGoalProgressReport();
    }

    @GetMapping("/{id}/detail-report")
    public FinancialGoalReportService.GoalDetailReport getGoalDetailReport(@PathVariable Long id) {
        return financialGoalService.generateGoalDetailReport(id);
    }
}
