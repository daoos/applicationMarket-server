package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.SystemConfig;

@Transactional
public interface SystemConfigService {
	
	/**
	 * 修改系统配置
	 * @param systemConfig
	 * @return
	 * @throws Exception
	 */
	Object modifyInfo(SystemConfig systemConfig)throws Exception;
	
	/**
	 * 获取系统配置信息
	 * @param configId
	 * @return
	 * @throws Exception
	 */
	Object getConfigInfo(Integer configId)throws Exception;
}
