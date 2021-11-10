package com.example.demo.dao.datastruct;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="role_assign")
@Data
@NoArgsConstructor
public class RoleAssign {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String userName;
	private int roleId;
	
	public RoleAssign(String userName,int roleId)
	{
		this.userName=userName;
		this.roleId=roleId;
	}
}
