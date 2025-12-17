package com.codewithraman.trafficease.controller;

import com.codewithraman.trafficease.model.PoliceUser;
import com.codewithraman.trafficease.repository.PoliceUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private PoliceUserRepository policeUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // login.html
    }

    // Show register page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // register.html
    }

    // Handle register form
    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String password,
                                  Model model) {
        // ✅ Check if user already exists
        PoliceUser existingUser = policeUserRepository.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        // ✅ Save new user
        PoliceUser user = new PoliceUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        policeUserRepository.save(user);

        return "redirect:/login"; // after register → go to login
    }
}
