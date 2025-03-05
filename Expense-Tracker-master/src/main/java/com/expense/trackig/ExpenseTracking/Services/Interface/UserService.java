package com.expense.trackig.ExpenseTracking.Services.Interface;

import com.expense.trackig.ExpenseTracking.Modules.User;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    public User getUserByEmail(String email);
    public User getUserByEmailAndPassword(String email,String password);
    public void saveUser(User user);
    void setCookie(HttpServletResponse response);
    boolean isLoggedIn(String cookie);
}
