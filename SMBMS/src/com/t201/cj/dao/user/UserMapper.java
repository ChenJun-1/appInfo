package com.t201.cj.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.t201.cj.pojo.User;

public interface UserMapper {
	
	/**
	 * 修改用户密码
	 * @param id
	 * @param pwd
	 * @return
	 */
	public int updatePwd(@Param("id")String id,@Param("pwd")String pwd);
	
	/**
	 * 根据用户id删除用户信息
	 * @param id
	 * @return
	 */
	public int delUser(@Param("id")int id);
	
	/**
	 * 根据用户id查询用户信息
	 * @param id
	 * @return
	 */
	public User getUserById(@Param("id")String id);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public int modify(User user);
	
	/**
	 * 通过userCode获取User
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public User getLoginUser(@Param("userCode")String userCode) throws Exception;
	
	/**
	 * 增加用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int add(User user)throws Exception;

	/**
	 * 通过条件查询-userList
	 * @param userName
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserList(@Param("userName")String userName,@Param("userRole")int userRole,@Param("currentPageNo")int currentPageNo, @Param("pageSize")int pageSize)throws Exception;
	
	/**
	 * 通过条件查询-用户表记录数
	 * @param userName
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	public int getUserCount(@Param("userName")String userName,@Param("userRole")int userRole)throws Exception;
}
