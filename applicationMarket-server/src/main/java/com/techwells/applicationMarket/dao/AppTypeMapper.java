package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.AppType;
import com.techwells.applicationMarket.util.PagingTool;

public interface AppTypeMapper {
    int deleteByPrimaryKey(Integer appTypeId);

    int insert(AppType record);

    int insertSelective(AppType record);

    AppType selectByPrimaryKey(Integer appTypeId);

    int updateByPrimaryKeySelective(AppType record);

    int updateByPrimaryKey(AppType record);
    
    List<AppType> selectAppTypes();
    
    /**
     * 查询热门分类
     * @return
     */
    List<AppType> selectHotAppTypes();
}