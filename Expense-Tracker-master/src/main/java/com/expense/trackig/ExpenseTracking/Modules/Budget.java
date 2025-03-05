package com.expense.trackig.ExpenseTracking.Modules;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "budget")
public class Budget {
    @Id
    private String id;
    private String category;
    private String month;
    private int year;
    private double allocatedBudget;
}
