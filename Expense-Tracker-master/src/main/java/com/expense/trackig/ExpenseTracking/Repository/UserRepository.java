package com.expense.trackig.ExpenseTracking.Repository;

import com.expense.trackig.ExpenseTracking.Modules.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email,String password);
}