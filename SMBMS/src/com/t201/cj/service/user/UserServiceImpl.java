package com.t201.cj.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.t201.cj.dao.user.UserMapper;
import com.t201.cj.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
	
	@Override
	public User login(String userCode, String userPassword) throws Exception{
		User user = null;
		user = userMapper.getLoginUser(userCode);
		//匹配密码
		if (null != user) {
			if (!user.getUserPassword().equals(userPassword)) {
				user = null;
			}
		}
		return user;
	}

	@Override
	public List<User> getUserList(String userName, int userRole,
			int currentPageNo, int pageSize) throws Exception{
		currentPageNo = (currentPageNo-1)*pageSize;
		return userMapper.getUserList(userName, userRole, currentPageNo, pageSize);
	}

	@Override
	public int getUserCount(String userName, int userRole) throws Exception{
		return userMapper.getUserCount(userName, userRole);
	}

	@Override
	public User selectUserCodeExist(String userCode) throws Exception {
		return userMapper.getLoginUser(userCode);
	}

	@Override
	public boolean add(User user) throws Exception {
		return userMapper.add(user) == 1 ? true : false;
	}

	@Override
	public boolean modify(User user) {
		return userMapper.modify(user) == 1 ? true : false;
	}

	@Override
	public User getUserById(String id) {
		return userMapper.getUserById(id);
	}

	@Override
	public boolean delUser(int id) {
		return userMapper.delUser(id) == 1 ? true : false;
	}

	@Override
	public boolean updatePwd(String id, String pwd) {
		return userMapper.updatePwd(id, pwd) == 1 ? true : false;
	}

}
