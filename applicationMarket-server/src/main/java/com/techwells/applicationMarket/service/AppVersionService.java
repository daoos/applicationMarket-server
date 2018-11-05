package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.AppVersion;

@Transactional
public interface AppVersionService {
	
	/**
	 * 安装包审核通过
	 * @param versionId  版本Id
	 * @return
	 * @throws Exception
	 */
	public Object exmainPass(Integer versionId) throws Exception;
	
	/**
	 * 版本审核失败
	 * @param versionId  版本Id
	 * @return
	 * @throws Exception
	 */
	public Object exmainFail(Integer versionId)throws Exception;
	
	/**
	 * 根据appId获取最新版本的安装包
	 * @param appId
	 * @return
	 */
	AppVersion getLastVersionByAppId(Integer appId);
}
