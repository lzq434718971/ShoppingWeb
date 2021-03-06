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
	
	private List<Goods> getPageList(String keyword,int page,int pagesize,String orderProperty,Boolean isDesc){
    	int lower=pagesize*page;
    	int upper=lower+pagesize;
    	return goodsr.getGoodsInfoLimitByKeywordInOrder(keyword,lower, upper, orderProperty, isDesc);
    }
	
	@GetMapping("/")
    public String domain() {
        return "redirect:/home";
    }
	
    @GetMapping("/home")
    public String test(HttpServletRequest request,ModelMap model,@RequestParam Map<String,Object> params) throws JsonProcessingException {
    	ControllerUtil.addUserNameAttribute(request, model);
    	int page,pagesize=10;
    	String keyword,sortParam;
    	boolean sortWay;
    	if(!params.containsKey("page"))
    	{
    		page=0;
    	}
    	else
    	{
    		page=Integer.parseInt(params.get("page").toString());
    	}
    	if(!params.containsKey("keyword"))
    	{
    		keyword="";
    	}
    	else
    	{
    		keyword=params.get("keyword").toString();
    	}
    	if(!params.containsKey("sortParam")
    			||params.get("sortParam").toString().isBlank())
    	{
    		sortParam="name";
    	}
    	else
    	{
    		sortParam=params.get("sortParam").toString();
    	}
    	if(!params.containsKey("sortWay")
    			||params.get("sortWay").toString().isBlank())
    	{
    		sortWay=false;
    	}
    	else
    	{
    		sortWay=Boolean.valueOf(params.get("sortWay").toString());
    	}
    	log.info(keyword);
    	log.info(sortParam);
    	log.info(String.valueOf(sortWay));
    	if(!params.containsKey("page"))
    	{
    		List<Goods> list=getPageList(keyword,0,10,sortParam,sortWay);
    		String obj=objectMapper.writeValueAsString(list);
    		model.addAttribute("goodsList", obj);
    	}
    	else
    	{
    		List<Goods> list=getPageList(keyword,Integer.parseInt(params.get("page").toString()),10,sortParam,sortWay);
    		String obj=objectMapper.writeValueAsString(list);
    		model.addAttribute("goodsList",obj);
    	}
        return "index";
    }
    
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        return "loginPage";
    }
    
    @GetMapping("/logout_success")
    public String logoutSuccess(HttpServletRequest request,ModelMap model) {
    	ControllerUtil.addUserNameAttribute(request, model);
        return "logout";
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
    	log.info("???????????????????????????");
    	//?????????????????????????????????
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
    			log.info("???????????????...");
    		}
    	}
    	else
    	{
    		generateValidationCode(request,params);
    	}
    	return "blank";
    }
    
    //??????????????????????????????
    private void generateValidationCode(HttpServletRequest request,Map<String,Object> params)
    {
    	String targetEmail=params.get("email").toString();
    	HttpSession session = request.getSession();
    	int code=new Random().nextInt(9000)+1000;
    	session.setAttribute("validationCode", code);
    	Calendar calendar=Calendar.getInstance();
    	session.setAttribute("lastRequest",calendar.getTimeInMillis());
		mailService.sendSimpleMail(targetEmail, "LzqWebShop:???????????????", String.valueOf(code));
    }
}