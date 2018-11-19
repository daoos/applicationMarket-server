package com.techwells.applicationMarket.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.SystemConfigMapper;
import com.techwells.applicationMarket.domain.SystemConfig;
import com.techwells.applicationMarket.service.SystemConfigService;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	@Resource
	private SystemConfigMapper systemConfigMapper;
	
	@Override
	public Object modifyInfo(SystemConfig systemConfig) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public Object getConfigInfo(Integer configId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		SystemConfig config=systemConfigMapper.selectByPrimaryKey(configId);
		
		if (config==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
		
		resultInfo.setResult(config);
		resultInfo.setTotal(1);
		resultInfo.setMessage("获取成功");
		return resultInfo;
		
	}

	@Override
	public SystemConfig getSystemConfigById(Integer configId) throws Exception {
		return systemConfigMapper.selectByPrimaryKey(configId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
