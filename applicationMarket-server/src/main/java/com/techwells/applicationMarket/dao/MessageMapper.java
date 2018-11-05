package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.Message;
import com.techwells.applicationMarket.util.PagingTool;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
    
    List<Message> selectMessageList(PagingTool pagingTool);
    
    int countTotal(PagingTool pagingTool);
}