package com.example.calculator.controller;

import com.example.calculator.model.User;
import com.example.calculator.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String handle_get(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String handle_post(@Valid @ModelAttribute("user") User user,
                              BindingResult result,
                              RedirectAttributes ra) {

        if (result.hasErrors()) return "registration";

        Optional<User> existing = userService.findByEmail(user.getEmail());

        if (existing.isPresent()) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) return "registration";

        userService.save(user);

        ra.addFlashAttribute("success", "Registration is successful, please log in to continue");
        log.info("Successfully registered");

        return "redirect:/login";
    }


}
