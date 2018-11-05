package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.RecommendType;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface RecommendTypeService {	
	/**
	 * 添加推荐分类
	 * @param recommendType  推荐分类对象
	 * @return
	 * @throws Exception
	 */
	Object addRecommendType(RecommendType recommendType)throws Exception;
	
	/**
	 * 删除推荐分类
	 * @param recommendTypeId
	 * @return
	 * @throws Exception
	 */
	Object deleteRecommendType(Integer recommendTypeId)throws Exception;
	
	/**
	 * 获取推荐分类详情
	 * @param recommendTypeId 推荐分类Id
	 * @return
	 * @throws Exception
	 */
	Object getRecommendType(Integer recommendTypeId)throws Exception;
	
	
	/**
	 * 分页获取推荐分类
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getRecommendTypeList(PagingTool pagingTool)throws Exception;
	
	/**
	 * 修改推荐分类
	 * @param recommendType
	 * @return
	 * @throws Exception
	 */
	Object modifyRecommendType(RecommendType recommendType)throws Exception;
	
	/**
	 * 查看同一个页面同一个位置的推荐分类的个数
	 * @param page  页面的位置
	 * @param location 位置
	 * @param activated  状态
	 * @return
	 * @throws Exception
	 */
	RecommendType countRecommendTypeByPageAndLoaction(Integer page,Integer location,Integer activated)throws Exception;

	
	/**
	 * 根据推荐分类Id获取应用列表
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getAppList(PagingTool pagingTool)throws Exception;
	
	
	
	/**
	 * 获取推荐分类的相信信息
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	Object getDetailInfo(Integer typeId)throws Exception;

	/**
	 * 从推荐分类中移除应用
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	Object removeApp(Integer appId,Integer recommendTypeId)throws Exception;
	
	
	
	
	
	
	

}
