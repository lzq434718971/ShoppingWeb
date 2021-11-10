package com.example.demo.dao.datastruct;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 订单实体
 * @author 86158
 *
 */
@Data
@NoArgsConstructor
public class ShopOrder {
	private int orderId;
	private String userName;
	private String goodsName;
	@NotNull
	@Min(0)
	private int count;
}
