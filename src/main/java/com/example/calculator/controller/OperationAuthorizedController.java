package com.example.calculator.controller;

import com.example.calculator.algorithm.MyAlgorithm;
import com.example.calculator.model.Operation;
import com.example.calculator.model.XUserDetails;
import com.example.calculator.services.SocialUserService;
import com.example.calculator.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;


@Log4j2
@Controller
@RequestMapping("/operation_auth")
@RequiredArgsConstructor
public class OperationAuthorizedController extends MyAlgorithm {

    private final UserService userService;
    private final SocialUserService socialUserService;


    @GetMapping
    public String get(Principal p, Model model) {
        model.addAttribute("operations_auth", new Operation());

        if (p instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken user = (OAuth2AuthenticationToken) p;
            socialUserService.addUserSocial(user);
            model.addAttribute("name", user.getPrincipal().getAttribute("name"));
        } else {
            UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) p;
            XUserDetails xUserDetails = (XUserDetails) user.getPrincipal();
            model.addAttribute("name", xUserDetails.getFullName());
        }
        return "operation_auth";
    }

    @PostMapping
    public String handle_post(@Valid @ModelAttribute("operations_auth") Operation operation,
                              Model model,
                              Principal p) {

            try {
                String result = eval(operation.getOperation());
                log.info(result);
                operation.setResult(result);
                log.info(operation.getResult());

                if (p instanceof OAuth2AuthenticationToken) {
                    OAuth2AuthenticationToken user = (OAuth2AuthenticationToken) p;
                    log.info("p instanceof OAuth2AuthenticationToken");
                    socialUserService.addOperation(user.getPrincipal().getAttribute("email"), user.getAuthorizedClientRegistrationId(), operation);
                } else {
                    log.info("p not instanceof OAuth2AuthenticationToken");

                    userService.addOperation(p.getName(), operation);
                }

            } catch (Exception e) {
                model.addAttribute("error", "Please choose correct operations");
                return "operation_auth";
            }

        return "operation_auth";
    }


    @ExceptionHandler({Exception.class, NullPointerException.class})
    public String handleErr(RedirectAttributes ra, Exception ex) {

        if (ex.getClass().getSimpleName().equals("NullPointerException")) {
            log.info("User Not Found Exception");
            return "error";
        } else {
            ra.addFlashAttribute("msg", "Please choose correct operations");
            return "redirect:/operation_auth";
        }
    }
}
