package com.t201.cj.service.bill;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.t201.cj.dao.bill.BillMapper;

@Service
public class BillServiceImpl implements BillService {
	@Resource
	private BillMapper billMapper;
	
	@Override
	public boolean delBillByProviderId(String providerId) {
		return billMapper.delBillByProviderId(providerId) > 0 ? true : false;
	}

	@Override
	public int getCountBillByProviderId(String providerId) {
		return billMapper.getCountBillByProviderId(providerId);
	}

}
