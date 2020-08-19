package com.example.calculator.controller;

import com.example.calculator.services.SocialUserService;
import com.example.calculator.services.UserService;
import com.example.calculator.services.imp.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {

    private final UserService userService;
    private final SocialUserService socialUserService;

    @GetMapping
    public String get(Principal p, Model model) {

        if (p instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken user = (OAuth2AuthenticationToken) p;
            model.addAttribute("user", socialUserService.findByEmailAndRegID(user.getPrincipal().getAttribute("email"),
                    user.getAuthorizedClientRegistrationId()));
        } else {
            model.addAttribute("user", userService.findByEmail(p.getName()).orElse(null));
        }
        model.addAttribute("name", UserServiceImp.getUserNameFromPrincipal(p));
        return "history";
    }
}
