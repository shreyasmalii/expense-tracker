package com.expense.trackig.ExpenseTracking.Controllers;
import com.expense.trackig.ExpenseTracking.Modules.User;
import com.expense.trackig.ExpenseTracking.Services.Interface.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public String login(@ModelAttribute User u, HttpSession session){
       User user = userService.getUserByEmailAndPassword(u.getEmail(),u.getPassword());
       if(user == null){
           return "redirect:/public/login";
       }
        session.setAttribute(u.getEmail(),"e-mail");
        return "redirect:/category/dashboard/"+u.getEmail();
    }
    @PostMapping("/sign-in")
    public String sign_in(@RequestParam String email, @RequestParam String password, Model model) {
        if (userService.getUserByEmail(email) != null) {
            model.addAttribute("error", "Email already in use");
            return "sign_in";
        }
        User newUser = new User(email, password, new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
        userService.saveUser(newUser);
        return "redirect:/public/login";
    }
    @GetMapping("/logout/{email}")
    public String logout(@PathVariable("email") String email, HttpSession session){
        session.removeAttribute(email);
        return "login";
    }
}


