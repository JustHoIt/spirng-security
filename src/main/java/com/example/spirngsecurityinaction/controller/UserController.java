package com.example.spirngsecurityinaction.controller;


import com.example.spirngsecurityinaction.dto.SignUpDTO;
import com.example.spirngsecurityinaction.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signUp")
    public String signUpPage() {

        return "signUp";
    }


    @PostMapping("/signUpProc")
    public String signUpProcess(SignUpDTO signUpDTO) {

        log.info(signUpDTO.getUsername() + " | " + signUpDTO.getPassword());
        boolean signUpYn = userService.signUpProcess(signUpDTO);
        log.info(String.valueOf(signUpYn));

        return "redirect:/login";
    }
}
