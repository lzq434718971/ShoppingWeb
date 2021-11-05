package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.datastruct.Goods;
import com.example.demo.dao.interf.GoodsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HWController {
	@Autowired
	GoodsRepository goodsr;
	@Autowired
    private ObjectMapper objectMapper;
	
	private List<Goods> getPageList(int page,int pagesize,String orderProperty,Boolean isDesc){
    	int lower=pagesize*page;
    	int upper=lower+pagesize;
    	return goodsr.getLimitGoodsInfoInOrder(lower, upper, orderProperty, isDesc);
    }
	
    @GetMapping("/hello")
    public String test(HttpServletRequest request,ModelMap model,@RequestParam Map<String,Object> params) throws JsonProcessingException {
    	Principal principal=request.getUserPrincipal();
    	if(principal != null)
    	{
        	model.addAttribute("userName",request.getUserPrincipal().getName());
    	}
    	else
    	{
    		model.addAttribute("userName",null);
    	}
    	
    	if(!params.containsKey("page"))
    	{
    		List<Goods> list=getPageList(0,10,"price",false);
    		String obj=objectMapper.writeValueAsString(list);
    		model.addAttribute("goodsList", obj);
    	}
    	else
    	{
    		List<Goods> list=getPageList(Integer.parseInt(params.get("page").toString()),10,"price",false);
    		String obj=objectMapper.writeValueAsString(list);
    		model.addAttribute("goodsList",obj);
    	}
        return "index";
    }
    
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        return "loginPage";
    }
}