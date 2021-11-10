package com.example.demo.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.datastruct.RoleAssign;
import com.example.demo.dao.datastruct.SellRecord;
import com.example.demo.dao.datastruct.ShopRole;
import com.example.demo.dao.datastruct.User;
import com.example.demo.dao.interf.GoodsRepository;
import com.example.demo.dao.interf.OrderListRepository;
import com.example.demo.dao.interf.RoleAssignRepository;
import com.example.demo.dao.interf.ShopRoleRepository;
import com.example.demo.dao.interf.UserRepository;
import com.example.demo.service.imp.TransTest;
import com.example.demo.service.interf.UserService;
import com.example.demo.util.ValidateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
public class TestController {
	@Autowired
	TransTest tt;
	@Autowired
	OrderListRepository os;
	@Autowired
	RoleAssignRepository roleassr;
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request) {
		os.addOrder("lzq2", "gpu2", 3);
		os.addOrder("lzq2", "gpu1", 6);
		os.addOrder("lzq2", "gpu1650", 10);
		os.addOrder("lzq2", "gpu1660", 7);
		os.addOrder("lzq4", "gpu2", 3);
		os.addOrder("lzq4", "gpu1", 6);
		os.addOrder("lzq4", "gpu1650", 10);
		os.addOrder("lzq4", "gpu1660", 7);
		String result = request.getRemoteAddr();
        return result;
    }
}
