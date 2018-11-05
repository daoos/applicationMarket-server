package com.techwells.applicationMarket.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.User;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface UserService {
	
	/**
	 * 用户注册
	 * @param userName  用户账号
	 * @param password  密码
	 * @return
	 * @throws Exception
	 */
	Object regist(String userName,String password)throws Exception;
	
	/**
	 * 用户登录
	 * @param userName  用户账号
	 * @param password  密码
	 * @return
	 * @throws Exception
	 */
	Object login(String userName,String password)throws Exception;
	
	
	/**
	 * 忘记密码的处理
	 * @param userName 用户名
	 * @param newPwd  新密码
	 * @return
	 * @throws Exception
	 */
	Object forgetPassword(String userName,String newPwd)throws Exception;
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Object modifyInfo(User user)throws Exception;
	
	/**
	 * 根据用户名获取用户信息
	 * @param userName 用户名
	 * @return
	 * @throws Exception
	 */
	User getUser(String userName)throws Exception;

	/**
	 * 获取用户审核列表
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getExaminList(PagingTool pagingTool)throws Exception;

	/**
	 * 审核用户
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	Object modifyBatch(String[] ids, Integer status)throws Exception;

	/**
	 * 获取用户详情
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Object getUserById(Integer userId)throws Exception;

	/**
	 * 修改密码
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	Object modifyPwd(Integer userId, String oldPwd, String newPwd);

	/**
	 * 分页获取用户信息
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getUserListBack(PagingTool pagingTool)throws Exception;

	/**
	 * 获取用户完成的任务
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Object getTaskListBack(Integer userId)throws Exception;
	
	/**
	 * 批量获取用户信息
	 * @param userIds
	 * @return
	 */
	List<User> getUserListById(String[] userIds);

	/**
	 * 获取个人中心详情
	 * @param userId  用户Id
	 * @param platform  平台
	 * @return
	 * @throws Exception 
	 */
	Object getCenterInfo(Integer userId, Integer platform)throws Exception; 
	
	
	
	
	
}
