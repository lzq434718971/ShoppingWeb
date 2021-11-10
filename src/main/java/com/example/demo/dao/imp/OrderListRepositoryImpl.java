package com.example.demo.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.datastruct.Goods;
import com.example.demo.dao.datastruct.ShopOrder;
import com.example.demo.dao.interf.OrderListRepository;

@Repository
public class OrderListRepositoryImpl implements OrderListRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addOrder(String userName, String goodsName, int count) {
		return jdbcTemplate.update("insert into orderlist(user_name,goods_name,count) values(?,?,?)",
				userName,goodsName,count);
	}

	@Override
	public int removeOrder(int orderID) {
		return jdbcTemplate.update("delete from orderlist where order_id = ?",orderID);
	}

	@Override
	public int setOrder(int orderID, String userName, String goodsName, int count) {
		return jdbcTemplate.update("update orderlist set user_name = ?,goods_name = ?,count = ? where order_id = ?",
				userName,goodsName,count,orderID);
	}

	@Override
	public ShopOrder getOrderByID(int orderID) {
		List<ShopOrder> orders = jdbcTemplate.query("select order_id,user_name,goods_name,count from orderlist where order_id = ? for update", 
				(resultSet, i) -> {
            ShopOrder order = new ShopOrder();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setUserName(resultSet.getString("user_name"));
            order.setGoodsName(resultSet.getString("goods_name"));
            order.setCount(resultSet.getInt("count"));
            return order;
        }, orderID);
		if(orders.size()>0)
		{
			return orders.get(0);
		}
        return null;
	}

	@Override
	public List<ShopOrder> getOrderByUserName(String userName) {
		List<ShopOrder> orders = jdbcTemplate.query("select order_id,user_name,goods_name,count from orderlist where user_name = ?", 
				(resultSet, i) -> {
            ShopOrder order = new ShopOrder();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setUserName(resultSet.getString("user_name"));
            order.setGoodsName(resultSet.getString("goods_name"));
            order.setCount(resultSet.getInt("count"));
            return order;
        }, userName);
		return orders;
	}

	@Override
	public List<ShopOrder> getLimitOrderInfoInOrderByUserName(String userName,int lower, int upper, String property, Boolean isDesc) {
		String sql="select * from orderlist where user_name = ? order by " + property;
		String desc=isDesc?" desc":"";
		sql+=desc+" limit ?,? for update";
		return jdbcTemplate.query(sql,(resultSet, i) -> {
            ShopOrder order = new ShopOrder();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setUserName(resultSet.getString("user_name"));
            order.setGoodsName(resultSet.getString("goods_name"));
            order.setCount(resultSet.getInt("count"));
            return order;
        },userName,lower,upper);
	}

}
