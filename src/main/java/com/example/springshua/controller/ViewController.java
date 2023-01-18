package com.example.springshua.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/hi")
    public String hello(Model model) {
        model.addAttribute("title", "Hi");
        return "greetings";
    }
}
