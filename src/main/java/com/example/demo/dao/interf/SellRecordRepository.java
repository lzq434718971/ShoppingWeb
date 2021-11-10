package com.example.demo.dao.interf;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;

import com.example.demo.dao.datastruct.SellRecord;

/**
 * 
 * 销售数据表访问接口
 * @author 86158
 *
 */
@Validated
public interface SellRecordRepository {
	/**
	 * 添加一条销售记录
	 */
	int addSellRecord(String userName,String goodsName,@Min(0) int count,Timestamp sellTime);
	
	/**
	 * 添加一条销售记录，记录时间为当前时间
	 */
	int addSellRecord(String userName,String goodsName,@Min(0) int count);
	
	/**
	 * 根据销售记录id移除一条记录
	 */
	int removeSellRecord(int recordID);
	
	/**
	 * 根据id定位并修改一条记录
	 */
	int setRecord(int recordID,String userName,String goodsName,@Min(0) int count,Timestamp sellTime);
	
	/**
	 * 根据用户名查询销售记录
	 */
	List<SellRecord> getRecordByUserName(String userName) throws Exception;
	
	/**
	 * 根据商品名查询销售记录
	 */
	List<SellRecord> getRecordByGoodsName(String goodsName);
	
	/**
	 * 根据日期查询销售记录
	 */
	List<SellRecord> getRecordByDate(@Min(0) int year,@Min(0) @Max(11) int month,@Min(0) int day);

}
