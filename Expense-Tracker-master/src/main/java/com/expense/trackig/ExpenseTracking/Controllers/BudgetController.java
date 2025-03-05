package com.expense.trackig.ExpenseTracking.Controllers;

import com.expense.trackig.ExpenseTracking.Modules.Budget;
import com.expense.trackig.ExpenseTracking.Services.Implementation.BudgetServiceImpl;

import com.expense.trackig.ExpenseTracking.Services.Interface.BudgetService;
import com.expense.trackig.ExpenseTracking.Services.Interface.ExpenseService;
import com.expense.trackig.ExpenseTracking.Services.Interface.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

@Controller
@RequestMapping("/budget")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserService userService;

    @GetMapping("/add-budget/{category}/{email}")
    public String showBudgetForm(@PathVariable("email") String email, HttpSession session, @PathVariable("category") String category, Model model) {
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";
        model.addAttribute("months",expenseService.getMonths());
        model.addAttribute("years",expenseService.getYears());
        model.addAttribute("category",category);
        model.addAttribute("email",email);
        return "add_budget";
    }

    @PostMapping("/add-budget/{email}")
    public String saveBudget(@PathVariable("email") String email,HttpSession session,@ModelAttribute Budget budget) {
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";
        try{
            if(budgetService.getAllocatedBudget(email,budget.getMonth(), budget.getYear(), budget.getCategory()) != 0){
                budgetService.removeBudget(email,budget);
            }
            budgetService.saveBudget(email,budget);
        }catch (Exception e){
            System.out.println(e);
            return "redirect:/budget/add-budget/"+budget.getCategory()+"/"+email;
        }
        return "redirect:/expense/getExpenses?email="+email+"&category="+budget.getCategory()+"&month="+ budget.getMonth()+"&year="+budget.getYear()+"";
    }
}
