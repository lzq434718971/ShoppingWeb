package com.example.demo.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public class ControllerUtil {
	public static void addUserNameAttribute(HttpServletRequest request,ModelMap model)
	{
		Principal principal=request.getUserPrincipal();
    	if(principal != null)
    	{
        	model.addAttribute("userName",request.getUserPrincipal().getName());
    	}
    	else
    	{
    		model.addAttribute("userName",null);
    	}
	}
	
	/**
	 * 判断访问该网址的用户是不是指定的用户
	 * @param request
	 * @param pathUserName
	 * @return
	 */
	public static boolean confirmUser(HttpServletRequest request,String pathUserName)
	{
		Principal principal=request.getUserPrincipal();
		return principal.getName().equals(pathUserName);
	}
}
