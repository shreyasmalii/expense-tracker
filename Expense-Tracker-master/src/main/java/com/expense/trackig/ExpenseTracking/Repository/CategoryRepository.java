package com.expense.trackig.ExpenseTracking.Repository;

import com.expense.trackig.ExpenseTracking.Modules.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category,String> {
}
