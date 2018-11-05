package com.techwells.applicationMarket.dao;

import java.awt.Paint;
import java.util.List;

import com.techwells.applicationMarket.domain.Task;
import com.techwells.applicationMarket.domain.rs.TaskAdminVos;
import com.techwells.applicationMarket.domain.rs.TaskAppVersionVos;
import com.techwells.applicationMarket.util.PagingTool;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
    
    /**
     * 分页获取任务
     * @param pagingTool
     * @return
     */
    List<TaskAdminVos> selectTaskList(PagingTool pagingTool);
    
    /**
     * 分页获取总数
     * @param pagingTool
     * @return
     */
    int countTotal(PagingTool pagingTool);
    
    
    /**
     * 分页获取任务信息，前台
     * @param pagingTool
     * @return
     */
    List<TaskAppVersionVos> selecTaskAppVersionVos(PagingTool pagingTool);
    
    /**
     * 获取数量  
     * @param pagingTool
     * @return
     */
    int countTotalDetail(PagingTool pagingTool);
    
    /**
     * 分页获取任务信息
     * @param pagingTool
     * @return
     */
    List<Task> selectTaskDetailList(PagingTool pagingTool);
    
    
    /**
     * 获取弹窗等级最高的任务
     * @return
     */
    Task selectHighLeve();
    
    
    
    
    
    
    
    
    
}