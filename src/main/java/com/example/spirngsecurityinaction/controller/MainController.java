package com.example.spirngsecurityinaction.controller;


import com.example.spirngsecurityinaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String mainPage(Model model) {
        String id = userService.getUserId();
        String role = userService.getUserRole();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }
}
