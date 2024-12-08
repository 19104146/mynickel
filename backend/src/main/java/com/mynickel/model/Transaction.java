package com.mynickel.model;

import com.mynickel.enums.RecurrenceFrequency;
import com.mynickel.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private String notes;

    private TransactionType type;
    private RecurrenceFrequency recurrenceFrequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime nextOccurrence;
}
