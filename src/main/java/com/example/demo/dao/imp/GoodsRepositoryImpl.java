package com.example.demo.dao.imp;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.datastruct.Goods;
import com.example.demo.dao.interf.GoodsRepository;

@Repository
public class GoodsRepositoryImpl implements GoodsRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int addGoodsToSell(String name, double price, int num) {
		return jdbcTemplate.update("insert into goods(name,price,stock) values(?,?,?)",
				name,
				price,
				num);
	}
	
	@Override
	public int removeGoodsFromSell(String name) {
		return jdbcTemplate.update("delete from goods where name = ?",name);
	}

	@Override
	public int setGoodsStock(String name, int num) {
		return jdbcTemplate.update("update goods set stock = ? where name = ?",num,name);
	}

	@Override
	public Goods getGoodsInfoByName(String name) {
		List<Goods> list = jdbcTemplate.query("select * from goods where name = ? for update",(resultSet, i) -> {
            Goods goods = new Goods();
            goods.setName(resultSet.getString("name"));
            goods.setPrice(resultSet.getDouble("price"));
            goods.setStock(resultSet.getInt("stock"));
            return goods;
        },name);
		if(list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Goods> getAllGoodsInfo() {
		return jdbcTemplate.query("select * from goods for update",(resultSet, i) -> {
            Goods goods = new Goods();
            goods.setName(resultSet.getString("name"));
            goods.setPrice(resultSet.getDouble("price"));
            goods.setStock(resultSet.getInt("stock"));
            return goods;
        });
	}

	@Override
	public int getAllGoodsKindCount() {
		return jdbcTemplate.queryForObject("select count(1) from goods", Integer.class);
	}

	@Override
	public int setGoodsPrice(String name, double price) {
		return jdbcTemplate.update("update goods set price = ? where name = ?",price,name);
	}

	@Override
	public List<Goods> getGoodsInfoLimitByKeywordInOrder(String keyword,int lower, int upper, String property, Boolean isDesc) {
		String sql="select * from goods";
		String keywordRule=" where name regexp '.*"+keyword+".*'";
		String orderRule=" order by " + property;
		String desc=isDesc?" desc":"";
		sql += keywordRule + orderRule + desc + " limit ?,? for update";
		return jdbcTemplate.query(sql,(resultSet, i) -> {
            Goods goods = new Goods();
            goods.setName(resultSet.getString("name"));
            goods.setPrice(resultSet.getDouble("price"));
            goods.setStock(resultSet.getInt("stock"));
            return goods;
        },lower,upper);
	}

}
