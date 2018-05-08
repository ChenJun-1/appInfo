package com.t201.cj.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.t201.cj.pojo.Provider;

public interface ProviderMapper {
	
	/**
	 * 根据供应商id删除供应商
	 * @param id
	 * @return
	 */
	public int delProviderById(@Param("id") String id);
	
	/**
	 * 修改供应商
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public int modify(Provider provider)throws Exception;
	
	/**
	 * 根据供应商id查询供应商信息
	 * @param id
	 * @return
	 */
	public Provider getProviderById(@Param("id")String id);
	
	/**
	 * 增加供应商信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int add(Provider provider)throws Exception;
	
	/**
	 * 通过条件查询-providerList
	 * @param proName
	 * @param proCode
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Provider> getProviderList(@Param("proName")String proName,
										  @Param("proCode")String proCode,
										  @Param("currentPageNo")Integer currentPageNo, 
										  @Param("pageSize")Integer pageSize)throws Exception;
	
	/**
	 * 通过条件查询-供应商表记录数
	 * @param proName
	 * @param proCode
	 * @return
	 * @throws Exception
	 */
	public int getProviderCount(@Param("proName")String proName,
							    @Param("proCode")String proCode)throws Exception;
}
