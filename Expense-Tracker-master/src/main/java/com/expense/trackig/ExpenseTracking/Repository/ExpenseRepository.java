package com.expense.trackig.ExpenseTracking.Repository;

import com.expense.trackig.ExpenseTracking.Modules.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
}
