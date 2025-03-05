package com.expense.trackig.ExpenseTracking.Services.Implementation;

import com.expense.trackig.ExpenseTracking.Modules.Expense;
import com.expense.trackig.ExpenseTracking.Modules.User;
import com.expense.trackig.ExpenseTracking.Repository.ExpenseRepository;
import com.expense.trackig.ExpenseTracking.Services.Interface.ExpenseService;
import com.expense.trackig.ExpenseTracking.Services.Interface.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    UserService userService;

    @Getter
    final private List<String> months = new ArrayList<>(Arrays.asList(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    ));
    @Getter
    final private List<String> years = new ArrayList<>(Arrays.asList("2023","2024","2025","2026","2027","2028"));

    @Override
    public void saveExpense(String email,Expense expense) {
        User user = userService.getUserByEmail(email);
        Expense expense1 = expenseRepository.save(expense);
        user.getExpenses().add(expense1);
        userService.saveUser(user);
    }

    @Override
    public void removeExpense(String email,String id) {
        User user = userService.getUserByEmail(email);
        List<Expense> expenses = user.getExpenses();
        for (Expense c: expenses) {
            if(c.getId().equals(id)){
                expenses.remove(c);
                break;
            }
        }
        user.setExpenses(expenses);
        expenseRepository.deleteById(id);
        userService.saveUser(user);
    }

    @Override
    public void removeExpenseById(String id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public List<Expense> getAllExpenses(String email,String month, int year, String category) {
        User user = userService.getUserByEmail(email);
        List<Expense> monthExpenses = new ArrayList<>();

        List<Expense> expenses = user.getExpenses();
        for (Expense e: expenses) {
            if(e.getDate().getMonth().toString().equals(month) && e.getCategory().equals(category) && e.getDate().getYear() == year){
                monthExpenses.add(e);
            }
        }
        return monthExpenses;

//        LocalDate startDate = LocalDate.of(year, Month.valueOf(month), 1);
//        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
//        return expenseRepository.findByCategoryAndDateBetween(category, startDate, endDate);
    }
}
