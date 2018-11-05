package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.ReportReason;

public interface ReportReasonMapper {
    int deleteByPrimaryKey(Integer reportReasonId);

    int insert(ReportReason record);

    int insertSelective(ReportReason record);

    ReportReason selectByPrimaryKey(Integer reportReasonId);

    int updateByPrimaryKeySelective(ReportReason record);

    int updateByPrimaryKey(ReportReason record);
    
    /**
     * 获取所有的原因
     * @return
     */
    List<ReportReason> selectReasons();
    
}