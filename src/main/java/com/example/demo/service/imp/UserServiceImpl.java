package com.example.demo.service.imp;

import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.demo.dao.datastruct.Goods;
import com.example.demo.dao.datastruct.RoleAssign;
import com.example.demo.dao.datastruct.ShopOrder;
import com.example.demo.dao.datastruct.User;
import com.example.demo.dao.datastruct.VisitRecord;
import com.example.demo.dao.interf.GoodsRepository;
import com.example.demo.dao.interf.OrderListRepository;
import com.example.demo.dao.interf.RoleAssignRepository;
import com.example.demo.dao.interf.SellRecordRepository;
import com.example.demo.dao.interf.UserRepository;
import com.example.demo.dao.interf.VisitRecordRepository;
import com.example.demo.service.interf.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userr;
	@Autowired
	private OrderListRepository orderr;
	@Autowired
	private SellRecordRepository sellr;
	@Autowired 
	private VisitRecordRepository visitr;
	@Autowired
	private GoodsRepository goodsr;
	@Autowired
	private RoleAssignRepository roleassr;
	
	@Override
	@Transactional
	public int register(String userName, String passWord) {
		try
		{
			if(null == userr.getByName(userName))
			{
				userr.createUser(userName, passWord);
				roleassr.save(new RoleAssign(userName,2));
				return 1;
			}
			return 0;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@Transactional
	public int login(String userName, String password) {
		try
		{
			User uinfo=userr.getByName(userName);
			if(uinfo.getPassword().equals(password))
			{
				return 1;
			}
			return 0;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@Transactional
	public int visit(String userName,String goodsName) {
		try
		{
			visitr.save(new VisitRecord(userName,goodsName));
			return 0;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	@Modifying(clearAutomatically = true)
	@Transactional(isolation=Isolation.REPEATABLE_READ)
	public int submitOrder(String userName, String goodsName, int count) {
		try
		{
			Goods goods=goodsr.getGoodsInfoByName(goodsName);
			if(goods.getStock() < count)
			{
				return 2;
			}
			else
			{
				goodsr.setGoodsStock(goodsName, goods.getStock()-count);
				orderr.addOrder(userName, goodsName, count);
				return 1;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	@Transactional
	public int cancelOrder(int orderID) {
		try
		{
			ShopOrder order=orderr.getOrderByID(orderID);
			if(order == null)
			{
				return 0;
			}
			orderr.removeOrder(orderID);
			Goods goods=goodsr.getGoodsInfoByName(order.getGoodsName());
			goodsr.setGoodsStock(order.getGoodsName(), goods.getStock()+order.getCount());
			return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@Transactional
	public int payOrder(int orderID) {
		try
		{
			ShopOrder order=orderr.getOrderByID(orderID);
			orderr.removeOrder(orderID);
			sellr.addSellRecord(order.getUserName(), order.getGoodsName(), order.getCount());
			return 1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@Transactional
	public int logout(String userName) {
		// TODO Auto-generated method stub
		return 0;
	}

}
