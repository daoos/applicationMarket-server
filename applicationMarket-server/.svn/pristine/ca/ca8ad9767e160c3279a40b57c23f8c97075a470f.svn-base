package com.techwells.applicationMarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.applicationMarket.domain.UserTask;
import com.techwells.applicationMarket.util.PagingTool;

public interface UserTaskMapper {
    int deleteByPrimaryKey(Integer taskDetailId);

    int insert(UserTask record);

    int insertSelective(UserTask record);

    UserTask selectByPrimaryKey(Integer taskDetailId);

    int updateByPrimaryKeySelective(UserTask record);

    int updateByPrimaryKey(UserTask record);
    
    
    /**
     * 根据UserId获取完成的任务清单
     * @param pagingTool
     * @return
     */
    List<UserTask> selectUserTaskList(Integer userId);
    
    
    
    
    
}