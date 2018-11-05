package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.FeedBack;
import com.techwells.applicationMarket.domain.rs.FeedBackImageUserProvinceVos;
import com.techwells.applicationMarket.util.PagingTool;

public interface FeedBackMapper {
    int deleteByPrimaryKey(Integer feedbackId);

    int insert(FeedBack record);

    int insertSelective(FeedBack record);

    FeedBack selectByPrimaryKey(Integer feedbackId);

    int updateByPrimaryKeySelective(FeedBack record);

    int updateByPrimaryKey(FeedBack record);
    
    
    /**
     * 分页获取反馈信息
     * @param pagingTool
     * @return
     */
    List<FeedBackImageUserProvinceVos> selectFeedBackImageUserProvinceVos(PagingTool pagingTool);
    
    /**
     * 统计个数
     * @param pagingTool
     * @return
     */
    int countTotal(PagingTool pagingTool);
    
}