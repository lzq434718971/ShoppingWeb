package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HWController {
	
	
    @RequestMapping("/hello")
    public String test() {
        return "logintest";  
    }
    
    
    @PostMapping("/login")
    public String login() {
        return "logintest";
    }

}