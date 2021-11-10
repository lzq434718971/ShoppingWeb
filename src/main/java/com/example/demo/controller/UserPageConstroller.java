package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.datastruct.Goods;
import com.example.demo.dao.datastruct.ShopOrder;
import com.example.demo.dao.interf.GoodsRepository;
import com.example.demo.dao.interf.OrderListRepository;
import com.example.demo.service.interf.UserService;
import com.example.demo.socket.CancelOrderSocket;
import com.example.demo.socket.PayConfirmedSocket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserPageConstroller {
	@Autowired
	OrderListRepository orderr;
	@Autowired
	GoodsRepository goodsr;
	@Autowired
    private ObjectMapper objectMapper;
	@Autowired
	private UserService users;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private PayConfirmedSocket payConfirmedSocket;
	@Autowired
	private CancelOrderSocket cancelOrderSocket;
	
	private List<ShopOrder> getPageList(String userName,int page,int pagesize,String orderProperty,Boolean isDesc){
    	int lower=pagesize*page;
    	int upper=lower+pagesize;
    	return orderr.getLimitOrderInfoInOrderByUserName(userName, lower, upper, orderProperty, isDesc);
    }
	
	@GetMapping("/{userName}")
	public String userinfo(@PathVariable String userName,HttpServletRequest request,ModelMap model,@RequestParam Map<String,Object> params) throws JsonProcessingException {
		if(!ControllerUtil.confirmUser(request, userName))
		{
			return "error";
		}
		ControllerUtil.addUserNameAttribute(request, model);
		
		if(!params.containsKey("page"))
    	{
			List<ShopOrder> list=getPageList(userName, 0, 10, "order_id", false);
    		String obj=objectMapper.writeValueAsString(list);
    		model.addAttribute("orderList", obj);
    	}
    	else
    	{
    		List<ShopOrder> list=getPageList(userName,Integer.parseInt(params.get("page").toString()),10,"order_id",false);
    		String obj=objectMapper.writeValueAsString(list);
    		model.addAttribute("orderList",obj);
    	}
		return "userInfoPage";
	}
	
	@PostMapping("/{userName}/cancelOrder")
	@Transactional
	public String deleteOrder(@PathVariable String userName,HttpServletRequest request,@RequestParam Map<String,Object> params) {
		if(!ControllerUtil.confirmUser(request, userName))
		{
			return "error";
		}
		String orderIdListStr=params.get("orderIdList").toString();
		String[] orderIdList=orderIdListStr.split(",");
		for(String orderId:orderIdList)
		{
			users.cancelOrder(Integer.valueOf(orderId));
		}
		cancelOrderSocket.sendInfo(userName);
		return "blank";
	}
	
	@PostMapping("/{userName}/payQRCode")
	@Transactional
	public String payOrder(@PathVariable String userName,ModelMap model,HttpServletRequest request,@RequestParam Map<String,Object> params) {
		if(!ControllerUtil.confirmUser(request, userName))
		{
			return "error";
		}
		ControllerUtil.addUserNameAttribute(request, model);
		
		String orderIdListStr=params.get("orderIdList").toString();
		String[] orderIdList=orderIdListStr.split(",");
		log.info(orderIdListStr);
		double totalPrice=0;
		for(String orderId:orderIdList)
		{
			ShopOrder order=orderr.getOrderByID(Integer.valueOf(orderId));
			Goods goods=goodsr.getGoodsInfoByName(order.getGoodsName());
			totalPrice+=order.getCount()*goods.getPrice();
		}
		model.addAttribute("totalPrice",totalPrice);
		
		redisTemplate.opsForValue().set("tmpOrderList_"+userName, orderIdListStr);
		return "payOrderPage";
	}
	
	@GetMapping("/{userName}/payConfirmed")
	@Transactional
	public String payConfirmed(@PathVariable String userName,ModelMap model,@RequestParam Map<String,Object> params) {
		String orderIdListStr=(String) redisTemplate.opsForValue().get("tmpOrderList_"+userName);
		String[] orderIdList=orderIdListStr.split(",");
		for(String orderId:orderIdList)
		{
			users.payOrder(Integer.valueOf(orderId));
		}
		payConfirmedSocket.sendInfo(userName, "payConfirmed");
		return "paySuccess";
	}
	
	@GetMapping("/{userName}/paySuccess")
	public String paySuccess(@PathVariable String userName,ModelMap model) {
		if(!redisTemplate.hasKey("tmpOrderList_"+userName))
		{
			return "error";
		}
		redisTemplate.opsForValue().getOperations().delete("tmpOrderList_"+userName);
		model.addAttribute("userName",userName);
		return "paySuccess";
	}
}
