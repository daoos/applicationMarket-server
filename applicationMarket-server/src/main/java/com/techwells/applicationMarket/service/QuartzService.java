package com.techwells.applicationMarket.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.dao.AppMapper;

/**
 * 定时调度任务的实现类
 * @author Administrator
 */
@Transactional
public interface QuartzService {
	/**
	 * 每24小时清空downloadCount_add这个字段的值
	 */
	public void resetDownloadCountAdd();
	
	/**
	 * 应用上架  
	 * 1、审核通过的，定时发布的应用、到达上架时间的需要自动上架
	 * 2、不需要，在sql语句中已经做出了筛选
	 */
	void ground();
	
	
	/**
	 * 设置区块的高度
	 */
	void setBlock();
	
	/**
	 * 获取moac交易的信息
	 */
	void getMoacTrasactionDetail();
	
}
