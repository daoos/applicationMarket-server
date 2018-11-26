package com.techwells.applicationMarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.applicationMarket.domain.User;
import com.techwells.applicationMarket.util.PagingTool;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 根据用户获取用户信息
     * @param userName  用户名 账号
     * @return
     */
    User selectUserByUserName(String userName);
    
    /**
     * 根据邮箱获取用户信息
     * @param userName  用户名 账号
     * @return
     */
    User selectUserByEmail(String email);
    
    /**
     * 获取用户审核列表 分页
     * @param pagingTool
     * @return
     */
    List<User> selectExmainList(PagingTool pagingTool);
    
    /**
     * 获取用户审核列表的总数
     * @param pagingTool
     * @return
     */
    int countTotalExaminList(PagingTool pagingTool);
    
    
    /**
     * 批量修改用户信息
     * @param ids
     * @param status
     * @return
     */
    int updateUserBatch(@Param("ids")String[] ids,@Param("status")Integer status);
    
    
    /**
 	 * 获取用户信息
 	 * @param pagingTool
 	 * @return
 	 */
 	List<User> selectUserListBack(PagingTool pagingTool);
 	
 	int countTotalUserListBack(PagingTool pagingTool);
    
    
 	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}