package com.example.demo.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.datastruct.RoleAssign;
import com.example.demo.dao.datastruct.ShopRole;
import com.example.demo.dao.datastruct.User;
import com.example.demo.dao.interf.RoleAssignRepository;
import com.example.demo.dao.interf.ShopRoleRepository;
import com.example.demo.dao.interf.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	ShopRoleRepository roler;
	@Autowired
	RoleAssignRepository roleassr;

	@Override
	public int createUser(String name, String psw) {
		return jdbcTemplate.update("insert into user(name, password) values(?, ?)", name, psw);
	}

	@Override
	public int deleteByName(String name) {
		return jdbcTemplate.update("delete from user where name = ?", name);
	}

	@Override
	public int deleteAllUsers() {
		return jdbcTemplate.update("delete from user");
	}

	@Override
	public User getByName(String name) {
		List<User> users = jdbcTemplate.query("select name,password from user where name = ? for update", (resultSet, i) -> {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }, name);
		if(users.size()>0)
		{
			return users.get(0);
		}
        return null;
	}

	@Override
	public int getAllUsersCount() {
		return jdbcTemplate.queryForObject("select count(1) from user", Integer.class);
	}

	@Override
	public int setPassWordByName(String name, String newPsw) {
		return jdbcTemplate.update("update user set password = ? where name = ?",newPsw,name);
	}

	@Override
	public int addRoleByUserName(String userName, String role) {
		ShopRole roleinfo=roler.findByName(role);
		roleassr.save(new RoleAssign(userName,roleinfo.getId()));
		return 0;
	}

	@Override
	public int removeRoleByUserName(String userName, String role) {
		ShopRole roleinfo=roler.findByName(role);
		roleassr.deleteById(roleinfo.getId());
		return 0;
	}

	@Override
	public List<ShopRole> getRoleByUserName(String userName) {
		List<ShopRole> roles = jdbcTemplate.query("select role.id,name from role,role_assign where role_id = role.id and user_name = ? for update", (resultSet, i) -> {
            ShopRole role = new ShopRole();
            role.setId(resultSet.getInt("id"));
            role.setName(resultSet.getString("name"));
            return role;
        }, userName);
		return roles;
	}
	
}
