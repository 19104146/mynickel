package com.mynickel.model;

import java.util.List;

public class User {
    private Long id;
    private String username;
    private String email;

    private String defaultCurrency;
    private boolean darkMode;
    private List<String> defaultCategories;

    private List<Transaction> transactions;
    private List<FinancialGoal> financialGoals;
}
