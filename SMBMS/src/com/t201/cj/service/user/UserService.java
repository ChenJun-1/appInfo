package com.t201.cj.service.user;

import java.util.List;

import com.t201.cj.pojo.User;

public interface UserService {
	/**
	 * 修改用户密码
	 * @param id
	 * @param pwd
	 * @return
	 */
	public boolean updatePwd(String id,String pwd);
	
	/**
	 * 根据用户id删除用户信息
	 * @param id
	 * @return
	 */
	public boolean delUser(int id);
	
	/**
	 * 根据用户id查询用户信息
	 * @param id
	 * @return
	 */
	public User getUserById(String id);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public boolean modify(User user);
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public boolean add(User user) throws Exception;
	
	/**
	 * 根据userCode查询出User
	 * @param userCode
	 * @return
	 */
	public User selectUserCodeExist(String userCode) throws Exception;
	
	/**
	 * 用户登录
	 * @param userCode
	 * @param userPassword
	 * @return
	 * @throws Exception
	 */
	public User login(String userCode,String userPassword) throws Exception;
	
	/**
	 * 通过条件查询-userList
	 * @param connection
	 * @param userName
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserList(String userName,int userRole,int currentPageNo,int pageSize)throws Exception;
	
	/**
	 * 通过条件查询-用户表记录数
	 * @param connection
	 * @param userName
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	public int getUserCount(String userName,int userRole)throws Exception;
}
