package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.AppType;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface AppTypeService {
	/**
	 * 获取app分类的列表
	 * @return
	 * @throws Exception
	 */
	Object getAppTypeList()throws Exception;

	/**
	 * 设置热门分类
	 * @param appType
	 * @return
	 * @throws Exception
	 */
	Object hot(AppType appType)throws Exception;

	/**
	 * 取消热门分类
	 * @param appType
	 * @return
	 * @throws Exception
	 */
	Object cancelHot(AppType appType)throws Exception;
	
	
	
	
	
	
	
	
	
	
}
