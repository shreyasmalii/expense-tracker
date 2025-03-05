package com.expense.trackig.ExpenseTracking.Services.Interface;

import com.expense.trackig.ExpenseTracking.Modules.Expense;

import java.util.List;

public interface ExpenseService {
    public void saveExpense(String email,Expense expense);
    public void removeExpense(String email,String id);
    public void removeExpenseById(String id);
    public List<Expense> getAllExpenses(String email,String month, int year, String category);
    public List<String> getMonths();
    public List<String> getYears();
}
