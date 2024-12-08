package com.mynickel.model;

import com.mynickel.enums.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinancialGoal {
    private Long id;
    private String title;
    private BigDecimal targetAmount;
    private LocalDate deadline;
    private String category;
    private BigDecimal currentProgress;
    private GoalStatus goalStatus;

    public static class Milestone {
        private Long id;
        private String description;
        private BigDecimal targetAmount;
        private boolean isCompleted;
        private LocalDate completedDate;
    }
}
