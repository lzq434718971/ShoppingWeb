package com.example.demo.dao.datastruct;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 访问记录实体
 * @author 86158
 *
 */
@Entity
@Table(name="visit_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String userName;
	private String goodsName;
	private Timestamp time;
	
	public VisitRecord(String userName,String goodsName,Timestamp time)
	{
		this.userName=userName;
		this.goodsName=goodsName;
		this.time=time;
	}
	
	public VisitRecord(String userName,String goodsName)
	{
		this.userName=userName;
		this.goodsName=goodsName;

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(Calendar.getInstance().getTime());
		Timestamp ts = Timestamp.valueOf(time);
		this.time=ts;
	}
}
