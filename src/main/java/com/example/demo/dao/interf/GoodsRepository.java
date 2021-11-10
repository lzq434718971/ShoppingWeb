package com.example.demo.dao.interf;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.validation.annotation.Validated;

import com.example.demo.dao.datastruct.Goods;

/**
 * 
 * 商品表访问接口
 * @author 86158
 *
 */
@Validated
public interface GoodsRepository {
	/**
	 * 添加商品,并指定单价和数量
	 */
	int addGoodsToSell(String name,double price,int num);
	
	/**
	 * 下架商品
	 */
	int removeGoodsFromSell(String name);
	
	/**
	 * 设定商品库存
	 */
	int setGoodsStock(String name,int num);
	
	/**
	 * 设定商品价格
	 */
	int setGoodsPrice(String name,double price);
	
	/**
	 * 获取商品信息
	 */
	Goods getGoodsInfoByName(String name);
	
	/**
	 * 获取所有商品信息
	 */
	List<Goods> getAllGoodsInfo();
	
	/**
	 * 获取商品种类数量
	 */
	int getAllGoodsKindCount();
	
	/**
	 * 获取全部商品，以某种顺序排列，取其中指定区段
	 */
	List<Goods> getGoodsInfoLimitByKeywordInOrder(String keyword,int lower,int upper,String property,Boolean isDesc);
}
