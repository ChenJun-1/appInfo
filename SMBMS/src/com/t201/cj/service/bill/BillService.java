package com.t201.cj.service.bill;

public interface BillService {
	/**
	 * 根据供应商id查询该供应商下的订单数量
	 * @param providerId
	 * @return
	 */
	public int getCountBillByProviderId(String providerId);
	
	/**
	 * 根据供应商id删除供应商下所有订单
	 * @param providerId
	 * @return
	 */
	public boolean delBillByProviderId(String providerId);
}
