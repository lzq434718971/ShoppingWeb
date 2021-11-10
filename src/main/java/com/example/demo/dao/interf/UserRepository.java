package com.example.demo.dao.interf;

import java.util.List;

import com.example.demo.dao.datastruct.ShopRole;
import com.example.demo.dao.datastruct.User;

/**
 * 
 * 用户信息表访问接口
 * @author 86158
 *
 */
public interface UserRepository {
	/**
	 * 新增用户
	 */
	int createUser(String name,String psw);
	
	/**
	 * 删除用户
	 */
	int deleteByName(String name);
	
	/**
	 * 删除所有用户
	 */
	int deleteAllUsers();
	
	/**
	 * 根据名字获取用户
	 */
	User getByName(String name);
	
	/**
	 * 获取总用户数量
	 */
	int getAllUsersCount();
	
	/**
	 * 修改密码
	 */
	int setPassWordByName(String name,String newPsw);
	
	/**
	 * 添加权限
	 */
	int addRoleByUserName(String userName,String role);
	
	/**
	 * 移除权限
	 */
	int removeRoleByUserName(String userName,String role);
	
	/**
	 * 获得用户权限组
	 */
	List<ShopRole> getRoleByUserName(String userName);
	
}
