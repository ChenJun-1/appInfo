package com.t201.cj.service.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.t201.cj.dao.role.RoleMapper;
import com.t201.cj.pojo.Role;

@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleMapper roleMapper;
	
	@Override
	public List<Role> getRoleList() throws Exception {
		return roleMapper.getRoleList();
	}

}
