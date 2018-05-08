package com.t201.cj.service.provider;

import java.util.List;

import com.t201.cj.pojo.Provider;

public interface ProviderService {
	
	/**
	 * 根据供应商id删除供应商
	 * @param id
	 * @return
	 */
	public boolean delProviderById(String id);
	
	/**
	 * 修改供应商
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public boolean modify(Provider provider)throws Exception;
	
	/**
	 * 根据供应商id查询供应商信息
	 * @param id
	 * @return
	 */
	public Provider getProviderById(String id);
	
	/**
	 * 增加供应商
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	public boolean add(Provider provider) throws Exception;
	
	/**
	 * 通过条件查询-providerList
	 * @param proName
	 * @param proCode
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public List<Provider> getProviderList(String proName,String proCode,Integer currentPageNo, Integer pageSize) throws Exception;
	
	/**
	 * 通过条件查询-供应商表记录数
	 * @param proName
	 * @param proCode
	 * @return
	 * @throws Exception
	 */
	public int getproviderCount(String proName,String proCode) throws Exception;
}
