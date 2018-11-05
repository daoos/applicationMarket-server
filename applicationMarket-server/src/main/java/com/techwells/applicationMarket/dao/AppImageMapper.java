package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.AppImage;

public interface AppImageMapper {
    int deleteByPrimaryKey(Integer appImageId);

    int insert(AppImage record);

    int insertSelective(AppImage record);

    AppImage selectByPrimaryKey(Integer appImageId);

    int updateByPrimaryKeySelective(AppImage record);

    int updateByPrimaryKey(AppImage record);
    
    
    /**
     * 获取应用的截图
     * @param appId
     * @return
     */
    List<AppImage> selectImages(Integer appId);
}