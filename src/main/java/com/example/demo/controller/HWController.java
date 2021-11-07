package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.example.demo.service.imp.MailService;
import com.example.demo.service.interf.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HWController {
	@Autowired
	GoodsRepository goodsr;
	@Autowired
	UserService users;
	@Autowired
    private ObjectMapper objectMapper;
	@Autowired
    private MailService mailService;
	
	private List<Goods> getPageList(int page,int pagesize,String orderProperty,Boolean isDesc){
    	int lower=pagesize*page;
    	int upper=lower+pagesize;
    	return goodsr.getLimitGoodsInfoInOrder(lower, upper, orderProperty, isDesc);
    }
	
	@GetMapping("/")
    public String domain() {
        return "hello";
    }
	
    @GetMapping("/hello")
    public String test(HttpServletRequest request,ModelMap model,@RequestParam Map<String,Object> params) throws JsonProcessingException {
    	ControllerUtil.addUserNameAttribute(request, model);
    	
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
    
    @GetMapping("/register")
    public String register(HttpServletRequest request) {
        return "registerPage";
    }
    
    @PostMapping("/register")
    public String registerValidate(HttpServletRequest request,@RequestParam Map<String,Object> params) {
    	HttpSession session = request.getSession();
    	String postValidationCode=params.get("validationCode").toString();
    	String trueValidationCode=session.getAttribute("validationCode").toString();
    	if(trueValidationCode.equals(postValidationCode))
    	{
    		String userName=params.get("username").toString();
    		String password=params.get("password").toString();
    		String confirmPassword=params.get("confirmpassword").toString();
    		log.info("pass:"+password);
    		log.info("cpass:"+confirmPassword);
    		if(password.equals(confirmPassword))
    		{
    			log.info("registered!");
    			users.register(userName, password);
    		}
    	}
		return "blank";
    }
    
    @GetMapping("/validation")
    public String validation(HttpServletRequest request,@RequestParam Map<String,Object> params) {
    	log.info("接收到验证码请求！");
    	//每次请求验证码最小间隔
    	double cooldown=60;
    	HttpSession session = request.getSession();
    	if(session.getAttribute("lastRequest")!=null)
    	{
    		long lastRequest=(long) session.getAttribute("lastRequest");
    		Calendar calendar=Calendar.getInstance();
    		if(calendar.getTimeInMillis()-lastRequest>=cooldown*1000)
    		{
    			generateValidationCode(request,params);
    		}
    		else
    		{
    			log.info("验证码冷却...");
    		}
    	}
    	else
    	{
    		generateValidationCode(request,params);
    	}
    	return "blank";
    }
    
    //生成验证码并发送邮件
    private void generateValidationCode(HttpServletRequest request,Map<String,Object> params)
    {
    	String targetEmail=params.get("email").toString();
    	HttpSession session = request.getSession();
    	int code=new Random().nextInt(9000)+1000;
    	session.setAttribute("validationCode", code);
    	Calendar calendar=Calendar.getInstance();
    	session.setAttribute("lastRequest",calendar.getTimeInMillis());
		mailService.sendSimpleMail(targetEmail, "LzqWebShop:您的验证码", String.valueOf(code));
    }
}