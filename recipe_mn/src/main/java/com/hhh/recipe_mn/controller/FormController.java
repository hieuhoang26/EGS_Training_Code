package com.hhh.recipe_mn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {
    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/login.html";
    }
}
