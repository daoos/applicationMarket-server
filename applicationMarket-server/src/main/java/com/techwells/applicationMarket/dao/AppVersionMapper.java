package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.AppVersion;

public interface AppVersionMapper {
    int deleteByPrimaryKey(Integer appVersionId);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectByPrimaryKey(Integer appVersionId);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);
    
    /**
     * 获取最新的安装包
     * @param appId
     * @return
     */
    AppVersion selectLastVersion(Integer appId);
    
    /**
     * 根据appId获取历史安装包
     * @param appId
     * @return
     */
    List<AppVersion> selectHistVersionList(Integer appId);
    
    /**
     * 修改最新的版本为历史版本
     * @param appId
     * @return
     */
    int changeHistoryVersion(Integer appId);
    
    /**
     * 根据安装包的名称查询应用版本信息
     * @param packName
     * @return
     */
    AppVersion selectAppVersionByPackName(String packName);
    
    
    
    
}