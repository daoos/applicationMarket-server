package com.techwells.applicationMarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.applicationMarket.domain.Banner;
import com.techwells.applicationMarket.util.PagingTool;

public interface BannerMapper {
    int deleteByPrimaryKey(Integer bannerId);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Integer bannerId);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);
    
    Banner countBannerByPageAndLocation(@Param("location")Integer location,@Param("activated")Integer activated,@Param("platform")Integer platform);
    
    
    int countTotal(PagingTool pagingTool);
    
    List<Banner> selectBannerList(PagingTool pagingTool);
    
    /**
     * 获取首页的精推荐的广告
     * @param type
     * @return
     */
    List<Banner> selectBanners(@Param("platform")Integer platform);  
    
}