package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HWController {

    @RequestMapping("/hello")
    public String test() {
        return "push test";  
    }

}