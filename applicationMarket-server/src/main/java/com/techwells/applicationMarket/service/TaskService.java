package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.Task;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface TaskService {
	
	
	/**
	 * 添加任务
	 * @param task
	 * @return
	 * @throws Exception
	 */
	Object addTask(Task task)throws Exception;
	
	Object getTask(Integer taskId) throws Exception;
	
	Object deleteTask(Integer taskId)throws Exception;
	
	Object modifyTask(Task task)throws Exception;

	/**
	 * 获取所有的任务分类
	 * @return
	 * @throws Exception
	 */
	Object getTaskTypeList()throws Exception;

	/**
	 * 分页获取任务列表
	 * @param pagingTool
	 * @return
	 */
	Object getTaskList(PagingTool pagingTool)throws Exception;

	/**
	 * 分页获取任务清单
	 * @param pagingTool
	 * @return
	 */
	Object getDetailList(PagingTool pagingTool)throws Exception;

	/**
	 * 领取任务奖励
	 * @param taskDetailId 任务明细Id
	 * @param hash 转账的hash值
	 * @return
	 * @throws Exception
	 * @throws Throwable 
	 */
	Object receiveReward(Integer taskDetailId,String hash)throws Exception, Throwable;
	
	/**
	 * 立即完成任务
	 * @param userId
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	Object finishTask(Integer userId, Integer taskId)throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
}
