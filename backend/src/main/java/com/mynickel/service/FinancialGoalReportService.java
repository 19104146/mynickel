package com.mynickel.service;

import com.mynickel.model.FinancialGoal;
import com.mynickel.repository.FinancialGoalRepository;
import com.mynickel.enums.GoalStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FinancialGoalReportService {
    private final FinancialGoalRepository financialGoalRepository;

    public FinancialGoalReportService(FinancialGoalRepository financialGoalRepository) {
        this.financialGoalRepository = financialGoalRepository;
    }

    public GoalProgressReport generateGoalProgressReport() {
        List<FinancialGoal> allGoals = (List<FinancialGoal>) financialGoalRepository.findAll();
        return new GoalProgressReport(allGoals);
    }

    public GoalDetailReport generateGoalDetailReport(Long goalId) {
        FinancialGoal goal = financialGoalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        return new GoalDetailReport(goal);
    }

    public static class GoalDetailReport {
        private FinancialGoal goal;
        private BigDecimal progressPercentage;
        private int daysRemaining;
        private boolean isAtRisk;
        private GoalStatus recommendedStatus;

        public GoalDetailReport(FinancialGoal goal) {
            this.goal = goal;
            this.progressPercentage = calculateProgressPercentage();
            this.daysRemaining = calculateDaysRemaining();
            this.isAtRisk = checkGoalRisk();
            this.recommendedStatus = determineRecommendedStatus();
        }

        private BigDecimal calculateProgressPercentage() {
            return goal.getCurrentProgress()
                    .divide(goal.getTargetAmount(), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        private int calculateDaysRemaining() {
            return (int) ChronoUnit.DAYS.between(LocalDate.now(), goal.getDeadline());
        }

        private boolean checkGoalRisk() {
            // Goal is at risk if:
            // 1. Less than 50% progress with less than 25% of time remaining
            // 2. Progress is significantly behind expected progress
            BigDecimal expectedProgress = calculateExpectedProgress();
            boolean timeRisk = daysRemaining < 0 ||
                    (daysRemaining > 0 && daysRemaining < goal.getDeadline().lengthOfYear() * 0.25);
            boolean progressRisk = goal.getCurrentProgress().compareTo(expectedProgress) < 0;

            return timeRisk || progressRisk;
        }

        private GoalStatus determineRecommendedStatus() {
            if (goal.getGoalStatus() == GoalStatus.COMPLETED) {
                return GoalStatus.COMPLETED;
            }

            if (daysRemaining < 0) {
                return GoalStatus.FAILED;
            }

            if (isAtRisk) {
                return GoalStatus.AT_RISK;
            }

            if (progressPercentage.compareTo(BigDecimal.valueOf(100)) >= 0) {
                return GoalStatus.COMPLETED;
            }

            return goal.getCurrentProgress().compareTo(BigDecimal.ZERO) > 0
                    ? GoalStatus.IN_PROGRESS
                    : GoalStatus.IN_PROGRESS;
        }

        private BigDecimal calculateExpectedProgress() {
            long totalDays = ChronoUnit.DAYS.between(goal.getDeadline(), LocalDate.now());
            BigDecimal dailyTarget = goal.getTargetAmount().divide(
                    BigDecimal.valueOf(totalDays), 2, RoundingMode.HALF_UP
            );
            return dailyTarget.multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(LocalDate.now(), goal.getDeadline())));
        }

        // Getters
        public BigDecimal getProgressPercentage() {
            return progressPercentage;
        }

        public int getDaysRemaining() {
            return daysRemaining;
        }

        public boolean isAtRisk() {
            return isAtRisk;
        }

        public GoalStatus getRecommendedStatus() {
            return recommendedStatus;
        }
    }

    public static class GoalProgressReport {
        private List<FinancialGoal> goals;
        private BigDecimal totalTargetAmount;
        private BigDecimal totalCurrentProgress;
        private int completedGoals;
        private int atRiskGoals;
        private int inProgressGoals;
        private int failedGoals;
        private int totalGoals;

        public GoalProgressReport(List<FinancialGoal> goals) {
            this.goals = goals;
            this.totalTargetAmount = calculateTotalTargetAmount();
            this.totalCurrentProgress = calculateTotalCurrentProgress();
            this.completedGoals = countGoalsByStatus(GoalStatus.COMPLETED);
            this.atRiskGoals = countGoalsByStatus(GoalStatus.AT_RISK);
            this.inProgressGoals = countGoalsByStatus(GoalStatus.IN_PROGRESS);
            this.failedGoals = countGoalsByStatus(GoalStatus.FAILED);
            this.totalGoals = goals.size();
        }

        private BigDecimal calculateTotalTargetAmount() {
            return goals.stream()
                    .map(FinancialGoal::getTargetAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        private BigDecimal calculateTotalCurrentProgress() {
            return goals.stream()
                    .map(FinancialGoal::getCurrentProgress)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        private int countGoalsByStatus(GoalStatus status) {
            return (int) goals.stream()
                    .filter(goal -> goal.getGoalStatus() == status)
                    .count();
        }

        // Getters
        public BigDecimal getTotalTargetAmount() {
            return totalTargetAmount;
        }

        public BigDecimal getTotalCurrentProgress() {
            return totalCurrentProgress;
        }

        public int getCompletedGoals() {
            return completedGoals;
        }

        public int getAtRiskGoals() {
            return atRiskGoals;
        }

        public int getInProgressGoals() {
            return inProgressGoals;
        }

        public int getFailedGoals() {
            return failedGoals;
        }

        public int getTotalGoals() {
            return totalGoals;
        }
    }
}