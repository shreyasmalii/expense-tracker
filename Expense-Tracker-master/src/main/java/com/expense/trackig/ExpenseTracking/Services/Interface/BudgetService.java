package com.expense.trackig.ExpenseTracking.Services.Interface;

import com.expense.trackig.ExpenseTracking.Modules.Budget;

public interface BudgetService {
    public void saveBudget(String email, Budget budget);
    public double getAllocatedBudget(String email,String month, int year, String category);
    public void removeBudget(String email, Budget budget);
    public void removeBudgetById(String id);
}
