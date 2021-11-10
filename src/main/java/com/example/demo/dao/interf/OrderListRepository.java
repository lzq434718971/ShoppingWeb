package com.example.demo.dao.interf;

import java.util.List;

import com.example.demo.dao.datastruct.ShopOrder;

/**
 * 
 * 订单表访问接口
 * @author 86158
 *
 */
public interface OrderListRepository {
	/**
	 * 添加订单记录
	 */
	int addOrder(String userName,String goodsName,int count);
	
	/**
	 * 根据订单id删除订单记录
	 */
	int removeOrder(int orderID);
	
	/**
	 * 根据订单id修改订单记录
	 */
	int setOrder(int orderID,String userName,String goodsName,int count);
	
	/**
	 * 根据订单id获取订单记录
	 */
	ShopOrder getOrderByID(int orderID);
	
	/**
	 * 根据用户名获取订单记录
	 */
	List<ShopOrder> getOrderByUserName(String userName);
	
	/**
	 * 获取全部订单，以某种顺序排列，取其中指定区段
	 */
	List<ShopOrder> getLimitOrderInfoInOrderByUserName(String userName,int lower,int upper,String property,Boolean isDesc);
}
