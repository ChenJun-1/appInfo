package com.t201.cj.service.provider;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.t201.cj.dao.provider.ProviderMapper;
import com.t201.cj.pojo.Provider;
@Service
public class ProviderServiceImpl implements ProviderService{
	
	@Resource
	private ProviderMapper providerMapper;
	
	@Override
	public List<Provider> getProviderList(String proName, String proCode,
			Integer currentPageNo, Integer pageSize) throws Exception {
		currentPageNo = (currentPageNo-1)*pageSize;
		return providerMapper.getProviderList(proName, proCode, currentPageNo, pageSize);
	}

	@Override
	public int getproviderCount(String proName, String proCode)
			throws Exception {
		return providerMapper.getProviderCount(proName, proCode);
	}

	@Override
	public boolean add(Provider provider) throws Exception {
		return providerMapper.add(provider) == 1 ? true : false;
	}

	@Override
	public Provider getProviderById(String id) {
		return providerMapper.getProviderById(id);
	}

	@Override
	public boolean modify(Provider provider) throws Exception {
		return providerMapper.modify(provider) == 1 ? true : false;
	}

	@Override
	public boolean delProviderById(String id) {
		return providerMapper.delProviderById(id) == 1 ? true : false;
	}

}
