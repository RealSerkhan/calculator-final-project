package com.example.calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @GetMapping("login")
    public String home() {
        return "login";
    }

    @GetMapping("login-error")
    public RedirectView postLogin(RedirectAttributes ra) {
        ra.addFlashAttribute("loginError", "Login credentials are incorrect, please try again");
        return new RedirectView("/login");
    }

    @GetMapping("error")
    public String error() {
        return "error";
    }

    @GetMapping
    public String main() {
        return "main";
    }

    @GetMapping("logout")
    public String logout() {
        return "main";
    }


}
