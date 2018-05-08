package com.t201.cj.dao.bill;

import org.apache.ibatis.annotations.Param;

public interface BillMapper {
	/**
	 * 根据供应商id查询该供应商下的订单数量
	 * @param providerId
	 * @return
	 */
	public int getCountBillByProviderId(@Param("providerId")String providerId);
	
	/**
	 * 根据供应商id删除供应商下所有订单
	 * @param providerId
	 * @return
	 */
	public int delBillByProviderId(@Param("providerId")String providerId);
}
