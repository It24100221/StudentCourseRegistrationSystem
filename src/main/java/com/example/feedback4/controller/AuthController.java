package com.example.feedback4.controller;

import com.example.feedback4.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login"; // Assumes you have a login.html Thymeleaf template
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email, Model model) {
        boolean success = authService.login(email);
        if (success) {
            return new RedirectView("/profile"); // Redirect to profile page after login
        } else {
            model.addAttribute("error", "Invalid email");
            return new RedirectView("/login");
        }
    }

    @GetMapping("/logout")
    public RedirectView logout(@RequestParam String email) {
        authService.logout(email);
        return new RedirectView("/home");
    }
}