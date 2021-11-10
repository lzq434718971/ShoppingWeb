package com.example.demo.service.interf;

public interface UserService {
	
	/**
	 * 用户注册
	 * @param userName
	 * @param passWord
	 * @return	0:用户已存在;1:正常注册
	 */
	int register(String userName,String passWord);
	
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return	0:登陆失败	;1:已登录
	 */
	int login(String userName,String password);
	
	/**
	 * 用户注销
	 * @param userName
	 * @return	0:注销失败	;1:注销成功
	 */
	int logout(String userName);
	
	/**
	 * 记录用户浏览行为
	 * @param userName
	 * @param goodsName
	 * @return 0:记录失败;1:记录成功
	 */
	int visit(String userName,String goodsName);
	
	/**
	 * 用户提交订单
	 * @param userName
	 * @param goodsName
	 * @param count
	 * @return 0:提交失败;1:提交成功;2:商品数量不足
	 */
	int submitOrder(String userName,String goodsName,int count);
	
	/**
	 * 用户取消订单
	 * @param orderID
	 * @return 0:取消失败;1:取消成功
	 */
	int cancelOrder(int orderID);
	
	/**
	 * 记录用户支付订单
	 * @param orderID
	 * @return 0:记录失败;1:记录成功
	 */
	int payOrder(int orderID);
}
