package com.t201.cj.service.role;

import java.util.List;

import com.t201.cj.pojo.Role;

public interface RoleService {
	
	/*
	 *查询所有角色信息 
	 */
	public List<Role> getRoleList()throws Exception;
}
