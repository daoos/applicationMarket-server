package com.techwells.applicationMarket.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.ApkVersionMapper;
import com.techwells.applicationMarket.domain.ApkVersion;
import com.techwells.applicationMarket.service.ApkVersionService;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class ApkVersionServiceImpl implements ApkVersionService {
	@Resource
	private ApkVersionMapper apkVersionMapper;
	
	@Override
	public Object getLastApkVersion(Integer type) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		ApkVersion apkVersion=apkVersionMapper.selectLastApkVersionByType(type);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(apkVersion);
		return resultInfo;
	}

}
