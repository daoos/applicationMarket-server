package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.City;

public interface CityMapper {
    int deleteByPrimaryKey(Integer cityId);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer cityId);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
    
    /**
     * 根据省份的编码获取市
     * @param provinceCode  省份的编码
     * @return
     */
    List<City> selectCityList(String provinceCode);
}