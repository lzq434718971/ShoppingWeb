package com.example.demo.dao.interf;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.datastruct.ShopRole;

/**
 * 
 * 权限表访问接口
 * @author 86158
 *
 */
public interface ShopRoleRepository extends JpaRepository<ShopRole,Integer>{
	
	/**
	 * 根据id删除role
	 * @param id
	 */
	void deleteById(int id);
	
	/**
	 * 根据name获取一个role
	 * @param name
	 * @return
	 */
	ShopRole findByName(String name);
}
