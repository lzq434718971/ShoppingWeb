package com.example.demo.dao.interf;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.datastruct.RoleAssign;

public interface RoleAssignRepository extends JpaRepository<RoleAssign,Integer>{
	
	/**
	 * 删除用户所有权限
	 * @param userName
	 */
	void deleteByUserName(String userName);
	
	/**
	 * 根据用户名删除其对应权限
	 * @param userName
	 * @param roleId
	 */
	void deleteByUserNameAndRoleId(String userName,int roleId);
}
