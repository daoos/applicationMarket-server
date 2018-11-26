package com.techwells.applicationMarket.dao;

import com.techwells.applicationMarket.domain.UserCode;

public interface UserCodeMapper {
    int deleteByPrimaryKey(Integer codeId);

    int insert(UserCode record);

    int insertSelective(UserCode record);

    UserCode selectByPrimaryKey(Integer codeId);

    int updateByPrimaryKeySelective(UserCode record);

    int updateByPrimaryKey(UserCode record);
    
    UserCode selectUserCodeByMobile(String mobile);  
    
}