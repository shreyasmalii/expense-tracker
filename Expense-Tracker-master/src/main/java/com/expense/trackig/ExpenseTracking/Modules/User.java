package com.expense.trackig.ExpenseTracking.Modules;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Setter
 @Getter
 @NoArgsConstructor
 @AllArgsConstructor
 @Document(collection = "user")
public class User {
    @Id
    private String email;
    private String password;
    @DBRef
    private List<Budget> budget;
    @DBRef
    private List<Expense> expenses;
    @DBRef
    private List<Category> categories;
 }
