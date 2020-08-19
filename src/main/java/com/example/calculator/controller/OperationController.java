package com.example.calculator.controller;

import com.example.calculator.algorithm.MyAlgorithm;
import com.example.calculator.model.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

@Log4j2
@Controller
@RequestMapping("/operation")
@RequiredArgsConstructor
public class OperationController extends MyAlgorithm {

    private final HashMap<String, Integer> counts;

    @GetMapping
    public  String handle_get(HttpServletRequest request, Model model) {
        model.addAttribute("operations", new Operation());
        String session = request.getSession().toString();
        Integer count = counts.get(session);
        log.info("handle_get() count:  " + count);
        if (count == null) counts.put(request.getSession().toString(), 0);
        else counts.put(session,count);
        return "operation";
    }

    @PostMapping
    public String handle_post(
                              HttpServletRequest request,
                              @Valid @ModelAttribute("operations") Operation operation,
                              Model model) {

        String session = request.getSession().toString();
        Integer count = counts.get(session);
        log.info("-----------------------------------------");
        counts.forEach((k,v) -> System.out.println("Key: " + k + "  | Value: " + v));
        log.info("-----------------------------------------");
        log.info("Remote address:" + session);
        log.info("-----------------------------------------");
        log.info("Count: " + count);
        counts.put(session, count+1);

        if (count >= 3) {
            model.addAttribute("error", "You can't be able to run  more than 3 operations. Please Log in");
        } else {
            try {
                String result = eval(operation.getOperation());
                log.info(result);
                operation.setResult(result);
            } catch (Exception e) {
                model.addAttribute("warning", "Please choose correct operations");
                return "operation";
            }
        }


        return "operation";
    }

}
