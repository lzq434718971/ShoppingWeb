package com.example.demo.dao.datastruct;

import javax.validation.constraints.Min;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 商品信息实体
 * @author 86158
 *
 */
@Data
@NoArgsConstructor
public class Goods {
	private String name;
	@Min(0)
	private double price;
	@Min(0)
	private int stock;
}
