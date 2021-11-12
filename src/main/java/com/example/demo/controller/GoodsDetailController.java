package com.example.demo.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.datastruct.Goods;
import com.example.demo.dao.datastruct.VisitRecord;
import com.example.demo.dao.interf.GoodsRepository;
import com.example.demo.dao.interf.VisitRecordRepository;
import com.example.demo.service.interf.UserService;
import com.example.demo.socket.AddToCartSocket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/goodsDetail")
public class GoodsDetailController {
	@Autowired
	GoodsRepository goodsr;
	@Autowired
	UserService users;
	@Autowired
	AddToCartSocket addToCartSocket;
	@Autowired
	VisitRecordRepository visitr;
	
	@GetMapping("/{goodsName}")
	public String goodsDetail(@PathVariable String goodsName,ModelMap model,HttpServletRequest request) {
		ControllerUtil.addUserNameAttribute(request, model);
		Goods goods=goodsr.getGoodsInfoByName(goodsName);
		model.addAttribute("goodsName",goods.getName());
		model.addAttribute("goodsPrice",goods.getPrice());
		model.addAttribute("goodsStock",goods.getStock());
		
		Principal principal=request.getUserPrincipal();
		visitr.save(new VisitRecord(principal.getName(),goodsName));
		
		return "goodsDetailPage";
	}
	@PostMapping("/{goodsName}")
	public String addToCart(@PathVariable String goodsName,HttpServletRequest request,@RequestParam Map<String,Object> params)
	{
		String userName=request.getUserPrincipal().getName();
		String count=params.get("count").toString();
		users.submitOrder(userName, goodsName, Integer.valueOf(count));
		addToCartSocket.sendInfo(userName);
		return "blank";
	}
}
