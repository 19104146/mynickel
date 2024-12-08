package com.mynickel.model;

import com.mynickel.enums.RecurrenceFrequency;
import com.mynickel.enums.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
