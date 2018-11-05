package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.Area;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer areaId);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer areaId);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);
    
    /**
     * 根据市的编码获取所有的区
     * @param cityCode  市的编码
     * @return
     */
    List<Area> selectAreaList(String cityCode);
}