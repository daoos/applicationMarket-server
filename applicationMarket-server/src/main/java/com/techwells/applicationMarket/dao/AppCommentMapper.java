package com.techwells.applicationMarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.applicationMarket.domain.AppComment;
import com.techwells.applicationMarket.domain.rs.ScorePercent;
import com.techwells.applicationMarket.domain.rs.UserCommentVos;
import com.techwells.applicationMarket.util.PagingTool;

public interface AppCommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(AppComment record);

    int insertSelective(AppComment record);

    AppComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(AppComment record);

    int updateByPrimaryKey(AppComment record);
    
    /**
     * 根据应用Id和用户Id查询评论
     * @param userId
     * @param appId
     * @return
     */
    AppComment selectAppCommentByAppIdAndUserId(@Param("userId")Integer userId,@Param("appId")Integer appId);
    
    
    /**
     * 获取评论的总数
     * @param appId
     * @return
     */
    int countTotal(PagingTool pagingTool);
    
    /**
     * 分页获取评论信息
     * @param pagingTool
     * @return
     */
    List<UserCommentVos> selectUserCommentVos(PagingTool pagingTool);
    
    
    /**
     * 获取应用分数的各种百分比
     * @return
     */
    List<ScorePercent> selectScorePercents(PagingTool pagingTool);
    
    
    /**
     * 获取平均的分数
     * @param pagingTool
     * @return
     */
    Double selectAvgScore(PagingTool pagingTool);
    
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}