package com.mynickel.repository;

import com.mynickel.model.FinancialGoal;
import org.springframework.data.repository.CrudRepository;

public interface FinancialGoalRepository extends CrudRepository<FinancialGoal, Long> {
}
