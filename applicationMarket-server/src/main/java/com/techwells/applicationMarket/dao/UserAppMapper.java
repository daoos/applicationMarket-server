package com.techwells.applicationMarket.dao;

import com.techwells.applicationMarket.domain.UserApp;
import com.techwells.applicationMarket.domain.UserAppKey;

public interface UserAppMapper {
    int deleteByPrimaryKey(UserAppKey key);

    int insert(UserApp record);

    int insertSelective(UserApp record);

    UserApp selectByPrimaryKey(UserAppKey key);

    int updateByPrimaryKeySelective(UserApp record);

    int updateByPrimaryKey(UserApp record);
}