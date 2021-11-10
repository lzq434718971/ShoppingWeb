package com.example.demo.dao.datastruct;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 用户信息实体
 * @author 86158
 *
 */
@Data
@NoArgsConstructor
public class User {
	private String name;
	private String password;
}
