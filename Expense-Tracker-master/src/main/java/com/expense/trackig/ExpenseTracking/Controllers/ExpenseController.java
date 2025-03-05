package com.expense.trackig.ExpenseTracking.Controllers;

import com.expense.trackig.ExpenseTracking.Modules.Expense;
import com.expense.trackig.ExpenseTracking.Services.Interface.BudgetService;
import com.expense.trackig.ExpenseTracking.Services.Interface.ExpenseService;
import com.expense.trackig.ExpenseTracking.Services.Interface.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@Controller
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private UserService userService;

    @GetMapping("/{category}/{email}")
    public String showExpensePage(@PathVariable("email") String email, HttpSession session,@PathVariable("category") String category,Model model){
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";
        model.addAttribute("email",email);
        model.addAttribute("selectedMonth","November");
        model.addAttribute("months",expenseService.getMonths());
        model.addAttribute("years",expenseService.getYears());
        model.addAttribute("selectedYear","2024");
        model.addAttribute("allocatedBudget","0.0");
        model.addAttribute("remainingBudget","0.0");
        model.addAttribute("category",category);
        return "expense_page";
    }

    @GetMapping("/getExpenses")
    public String viewCategory(
            @RequestParam("category") String category,
            @RequestParam("month") String month,
            @RequestParam("year") int year,
            @RequestParam("email") String email,
            HttpSession session,
            Model model) {
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";

        List<Expense> expenses = expenseService.getAllExpenses(email,Month.valueOf(month.toUpperCase()).toString(),year,category);
        double allocatedBudget = budgetService.getAllocatedBudget(email,month,year,category);

        double totalSpent = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double remainingBudget = allocatedBudget - totalSpent;

        month = month.charAt(0)+month.substring(1).toLowerCase();

        model.addAttribute("months",expenseService.getMonths());
        model.addAttribute("years",expenseService.getYears());
        model.addAttribute("category", category);
        model.addAttribute("allocatedBudget", allocatedBudget);
        model.addAttribute("remainingBudget", remainingBudget);
        model.addAttribute("expenses", expenses);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", Integer.toString(year));
        model.addAttribute("email", email);

        return "expense_page";
    }

    @GetMapping("/addExpenseForm/{category}/{email}")
    public String showExpenseForm(@PathVariable("email") String email, HttpSession session,@PathVariable("category") String category,Model model) {
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";
        model.addAttribute("category",category);
        model.addAttribute("email",email);
        return "add_expense";
    }

    @PostMapping("/addExpense/{email}")
    public String addExpense(
            @PathVariable("email") String email,
            HttpSession session ,@ModelAttribute Expense expense
    ) {
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";

        double allocatedBudget = budgetService.getAllocatedBudget(email,expense.getDate().getMonth().toString(),expense.getDate().getYear(),expense.getCategory());
        if(allocatedBudget == 0){
            return "redirect:/expense/budgetNotAllocatedError";
        }

        List<Expense> expenses = expenseService.getAllExpenses(email,expense.getDate().getMonth().toString().toUpperCase(),expense.getDate().getYear(),expense.getCategory());
        double totalSpent = expenses.stream().mapToDouble(Expense::getAmount).sum();
        if(allocatedBudget - (totalSpent+expense.getAmount()) < 0){
            return "redirect:/expense/insufficientBudget";
        }
        try {
            expenseService.saveExpense(email,expense);
        }catch (Exception e){
            return "add_expense";
        }
        return "redirect:/expense/getExpenses?email="+email+"&category="+expense.getCategory()+"&month="+expense.getDate().getMonth()+"&year="+expense.getDate().getYear()+"";
    }
    @GetMapping("/remove/{id}/{email}")
    @ResponseBody
    public String remove(@PathVariable("email") String email,@PathVariable("id") String id, HttpSession session){
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";
        expenseService.removeExpense(email,id);
        return "Expense deleted";
    }

    @GetMapping("/budgetNotAllocatedError")
    @ResponseBody
    public String budgetNotAllocatedError(){
        return "budget not allocated for selected month";
    }
    @GetMapping("/insufficientBudget")
    @ResponseBody
    public String insufficientBudget(){
        return "insufficient budget for month";
    }
}
