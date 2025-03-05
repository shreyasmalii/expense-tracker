package com.expense.trackig.ExpenseTracking.Repository;


import com.expense.trackig.ExpenseTracking.Modules.Budget;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends MongoRepository<Budget,String> {
}
