package com.expense.trackig.ExpenseTracking.Services.Implementation;

import com.expense.trackig.ExpenseTracking.Modules.User;
import com.expense.trackig.ExpenseTracking.Repository.UserRepository;
import com.expense.trackig.ExpenseTracking.Services.Interface.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    String cookie = "uuwkyySPGcp-Gw0eSXQBMVCEEuirXd3x1ybex0smYF4";
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("expense_cookie",this.cookie);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(1800);       // 30-mins
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public boolean isLoggedIn(String cookie) {
        return cookie.equals(this.cookie);
    }
}
