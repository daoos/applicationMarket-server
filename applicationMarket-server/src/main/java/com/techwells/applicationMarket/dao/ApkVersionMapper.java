package com.techwells.applicationMarket.dao;

import com.techwells.applicationMarket.domain.ApkVersion;

public interface ApkVersionMapper {
    int deleteByPrimaryKey(Integer versionId);

    int insert(ApkVersion record);

    int insertSelective(ApkVersion record);

    ApkVersion selectByPrimaryKey(Integer versionId);

    int updateByPrimaryKeySelective(ApkVersion record);

    int updateByPrimaryKey(ApkVersion record);
    
    ApkVersion selectLastApkVersionByType(Integer type);
    
}