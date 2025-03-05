package com.expense.trackig.ExpenseTracking.Controllers;

import com.expense.trackig.ExpenseTracking.Modules.Category;
import com.expense.trackig.ExpenseTracking.Services.Implementation.CategoryServiceImpl;
import com.expense.trackig.ExpenseTracking.Services.Interface.CategoriesService;
import com.expense.trackig.ExpenseTracking.Services.Interface.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoriesService categoryService;
    @Autowired
    UserService userService;

    @GetMapping("/dashboard/{email}")
    public String dashboard(@PathVariable("email") String email, Model model, HttpSession session){
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";
        model.addAttribute("categories",categoryService.getAllCategories(email));
        model.addAttribute("email",email);
        return "dashboard";
    }

    @PostMapping("/add/{email}")
    public String addCategory(@PathVariable("email") String email,HttpSession session ,@ModelAttribute Category category){
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";
        try {
            categoryService.saveCategory(email,category);
        }catch (Exception e){
            return "redirect:/category/category-not-added";
        }
        return "redirect:/category/dashboard/"+email;
    }
    @GetMapping("/remove/{email}/{name}")
    public String removeCategory(@PathVariable("email") String email,HttpSession session ,@PathVariable("name") String name){
        if(session.getAttribute(email) == null || !session.getAttribute(email).equals("e-mail"))
            return "redirect:/public/login";
        try {
            categoryService.removeCategory(email,name);
        }catch (Exception e){
            return "redirect:/category/category-not-removed";
        }
        return "redirect:/category/dashboard/"+email;
    }

    @GetMapping("/category-not-added")
    @ResponseBody
    public String categoryNotAdded(){
        return "Failed to add new Category";
    }

    @GetMapping("/category-not-removed")
    @ResponseBody
    public String categoryNotRemoved(){
        return "Failed to remove Category";
    }

}
