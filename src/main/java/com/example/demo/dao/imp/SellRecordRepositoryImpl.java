package com.example.demo.dao.imp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.datastruct.SellRecord;
import com.example.demo.dao.interf.SellRecordRepository;

@Repository
public class SellRecordRepositoryImpl implements SellRecordRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int addSellRecord(String userName, String goodsName, int count, Timestamp sellTime) {
		if(null==sellTime)
		{
			Calendar now=Calendar.getInstance();
			sellTime = new Timestamp(now.getTimeInMillis());
		}
		return jdbcTemplate.update("insert into sell_record(user_name,goods_name,count,sell_time) values(?,?,?,?)",
				userName,goodsName,count,sellTime);
	}

	@Override
	public int removeSellRecord(int recordID) {
		return jdbcTemplate.update("delete from sell_record where record_id = ?",
				recordID);
	}

	@Override
	public int setRecord(int recordID, String userName, String goodsName, int count, Timestamp sellTime) {
		return jdbcTemplate.update("update sell_record set user_name = ?,goods_name=?,count=?,sellTime=? where record_id=?",
				userName,goodsName,count,sellTime,recordID);
	}

	@Override
	public List<SellRecord> getRecordByUserName(String userName) throws Exception
	{
		return jdbcTemplate.query("select record_id,user_name,goods_name,count,sell_time from sell_record where user_name=? for update",
				(resultSet, i) -> {
					SellRecord record=new SellRecord();
					record.setRecordId(resultSet.getInt("record_id"));
					record.setUserName(resultSet.getString("user_name"));
					record.setGoodsName(resultSet.getString("goods_name"));
					record.setCount(resultSet.getInt("count"));
					record.setSellTime(resultSet.getTimestamp("sell_time"));
					return record;
				},
				userName);
	}

	@Override
	public List<SellRecord> getRecordByGoodsName(String goodsName) {
		return jdbcTemplate.query("select record_id,user_name,goods_name,count,sell_time from sell_record where goods_name=? for update",
				(resultSet, i) -> {
					SellRecord record=new SellRecord();
					record.setRecordId(resultSet.getInt("record_id"));
					record.setUserName(resultSet.getString("user_name"));
					record.setGoodsName(resultSet.getString("goods_name"));
					record.setCount(resultSet.getInt("count"));
					record.setSellTime(resultSet.getTimestamp("sell_time"));
					return record;
				},
				goodsName);
	}

	@Override
	public List<SellRecord> getRecordByDate(int year, int month, int day) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar target=Calendar.getInstance();
		target.set(year, month-1, day);
		String targetStr=f.format(target.getTime());
		return jdbcTemplate.query("select record_id,user_name,goods_name,count,sell_time from sell_record where date(sell_time)=? for update",
				(resultSet, i) -> {
					SellRecord record=new SellRecord();
					record.setRecordId(resultSet.getInt("record_id"));
					record.setUserName(resultSet.getString("user_name"));
					record.setGoodsName(resultSet.getString("goods_name"));
					record.setCount(resultSet.getInt("count"));
					record.setSellTime(resultSet.getTimestamp("sell_time"));
					return record;
				},
				targetStr);
	}

	@Override
	public int addSellRecord(String userName, String goodsName, int count) {
		return addSellRecord(userName,goodsName,count,null);
	}

}
