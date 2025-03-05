package com.expense.trackig.ExpenseTracking.Services.Implementation;

import com.expense.trackig.ExpenseTracking.Modules.Budget;
import com.expense.trackig.ExpenseTracking.Modules.Category;
import com.expense.trackig.ExpenseTracking.Modules.Expense;
import com.expense.trackig.ExpenseTracking.Modules.User;
import com.expense.trackig.ExpenseTracking.Repository.CategoryRepository;
import com.expense.trackig.ExpenseTracking.Services.Interface.BudgetService;
import com.expense.trackig.ExpenseTracking.Services.Interface.CategoriesService;
import com.expense.trackig.ExpenseTracking.Services.Interface.ExpenseService;
import com.expense.trackig.ExpenseTracking.Services.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoriesService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BudgetService budgetService;
    @Autowired
    ExpenseService expenseService;
    @Autowired
    UserService userService;

    @Override
    public void saveCategory(String email,Category category) {
        User user = userService.getUserByEmail(email);
        user.getCategories().add(category);
        categoryRepository.save(category);
        userService.saveUser(user);
    }
    @Override
    public void removeCategory(String email,String name) {
        User user = userService.getUserByEmail(email);
        List<Category> categories = user.getCategories();
        List<Budget> budgets = user.getBudget();
        List<Expense> expenses = user.getExpenses();

        for (Category c: categories) {
            if(c.getName().equals(name)){
                categories.remove(c);
                break;
            }
        }
        for (int i=budgets.size()-1; i>=0; i--) {
            if(budgets.get(i).getCategory().equals(name)){
                budgetService.removeBudgetById(budgets.get(i).getId());
                budgets.remove(budgets.get(i));
            }
        }
        for (int i=expenses.size()-1; i>=0; i--) {
            if(expenses.get(i).getCategory().equals(name)){
                expenseService.removeExpenseById(expenses.get(i).getId());
                expenses.remove(expenses.get(i));
            }
        }
        user.setBudget(budgets);
        user.setCategories(categories);
        user.setExpenses(expenses);
        userService.saveUser(user);
    }

    @Override
    public List<Category> getAllCategories(String email) {
        return userService.getUserByEmail(email).getCategories();
    }
}
