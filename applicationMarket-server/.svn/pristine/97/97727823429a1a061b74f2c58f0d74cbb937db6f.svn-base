package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.Provinces;

public interface ProvincesMapper {
    int deleteByPrimaryKey(Integer provinceId);

    int insert(Provinces record);

    int insertSelective(Provinces record);

    Provinces selectByPrimaryKey(Integer provinceId);

    int updateByPrimaryKeySelective(Provinces record);

    int updateByPrimaryKey(Provinces record);
    
    /**
     * 查询所有的省份
     * @return
     */
    List<Provinces> selectProvincesList();
}