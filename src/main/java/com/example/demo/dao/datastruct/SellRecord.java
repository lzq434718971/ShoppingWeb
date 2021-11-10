package com.example.demo.dao.datastruct;

import java.sql.Timestamp;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 销售记录实体
 * @author 86158
 *
 */
@Data
@NoArgsConstructor
public class SellRecord {
	private int recordId;
	private String userName;
	private String goodsName;
	@Min(0)
	public Integer count;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp sellTime;
}
