package com.example.demo.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HWController {
	
	
    @RequestMapping("/hello")
    public String test(HttpServletRequest request,ModelMap model) {
    	Principal principal=request.getUserPrincipal();
    	if(principal != null)
    	{
    		log.info(principal.getName());
        	model.addAttribute("userName",request.getUserPrincipal().getName());
    	}
    	else
    	{
    		model.addAttribute("userName",null);
    	}
        return "testindex";
    }
    
    
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        return "loginPage";
    }
    

}