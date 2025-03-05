package com.expense.trackig.ExpenseTracking.Services.Interface;

import com.expense.trackig.ExpenseTracking.Modules.Category;

import java.util.List;

public interface CategoriesService {
    public void saveCategory(String email,Category category);
    public void removeCategory(String email,String name);
    public List<Category> getAllCategories(String email);
}
