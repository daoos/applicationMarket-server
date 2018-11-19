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
    
    
    /**
     * 根据用户Id和任务Id获取信息
     * @param userId  用户Id
     * @param taskId  任务Id
     * @return
     */
    List<UserTask> selectUserTasksListByUserIdAndTaskId(@Param("userId")Integer userId,@Param("taskId")Integer taskId);

    
    
    
    
    
    
}