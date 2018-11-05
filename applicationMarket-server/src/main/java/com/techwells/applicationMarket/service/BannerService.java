package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.Banner;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface BannerService {	
	/**
	 * 添加广告
	 * @param banner  广告对象
	 * @return
	 * @throws Exception
	 */
	Object addBanner(Banner banner)throws Exception;
	
	/**
	 * 删除广告
	 * @param bannerId
	 * @return
	 * @throws Exception
	 */
	Object deleteBanner(Integer bannerId)throws Exception;
	
	/**
	 * 获取广告详情
	 * @param bannerId 广告Id
	 * @return
	 * @throws Exception
	 */
	Object getBanner(Integer bannerId)throws Exception;
	
	
	/**
	 * 分页获取广告
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getBannerList(PagingTool pagingTool)throws Exception;
	
	/**
	 * 修改广告
	 * @param banner
	 * @return
	 * @throws Exception
	 */
	Object modifyBanner(Banner banner)throws Exception;
	
	/**
	 * 查看同一个页面同一个位置的广告的个数
	 * @param page  页面的位置
	 * @param location 位置
	 * @param activated  状态   
	 * @return
	 * @throws Exception
	 */
	Banner countBannerByPageAndLoaction(Integer location,Integer activated,Integer platform)throws Exception;
	
	
	

}
