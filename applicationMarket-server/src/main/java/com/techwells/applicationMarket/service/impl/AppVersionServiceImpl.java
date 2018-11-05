package com.techwells.applicationMarket.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.AppVersionMapper;
import com.techwells.applicationMarket.domain.AppVersion;
import com.techwells.applicationMarket.service.AppVersionService;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class AppVersionServiceImpl implements AppVersionService {
	
	@Resource
	private AppVersionMapper versionMapper;
	
	@Resource
	private AppMapper appMapper;
	
	/**
	 * 审核通过
	 * 1、状态设置成通过
	 * 2、原先版本的状态设置成历史版本即可
	 */
	@Override
	public Object exmainPass(Integer versionId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		AppVersion appVersion=versionMapper.selectByPrimaryKey(versionId);  //获取当前审核的版本
		
		if (appVersion==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该版本不存在");
			return resultInfo;
		}
		
		//获取对应应用的最新版本
		AppVersion lastVersion=versionMapper.selectLastVersion(appVersion.getAppId());
		
		if (lastVersion==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("数据库中无最新版本");
			return resultInfo;
		}
		
		
		appVersion.setActivated(3);  //审核通过
		
		//修改状态
		int count=versionMapper.updateByPrimaryKeySelective(appVersion);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核失败");
			return resultInfo;
		}
		
		//修改最新版本为历史版本
		lastVersion.setIsHistoryVersion(1); 
		int count1=versionMapper.updateByPrimaryKeySelective(lastVersion);
		
		if (count1==0) {
			throw new RuntimeException();  //直接抛出异常，回滚数据
		}
		resultInfo.setMessage("审核通过成功");
		return resultInfo;
	}

	
	/**
	 * 审核失败 直接设置状态即可
	 */
	@Override
	public Object exmainFail(Integer versionId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		AppVersion version=versionMapper.selectByPrimaryKey(versionId);
		
		if (version==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该版本不存在");
			return resultInfo;
		}
		
		version.setActivated(1);  //设置状态为审核失败
		int count=versionMapper.updateByPrimaryKeySelective(version);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("审核成功");
		return resultInfo;
	}


	@Override
	public AppVersion getLastVersionByAppId(Integer appId) {
		
		return null;
	}
	
}
