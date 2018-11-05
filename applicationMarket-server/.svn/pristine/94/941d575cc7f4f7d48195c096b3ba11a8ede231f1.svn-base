package com.techwells.applicationMarket.dao;

import java.util.List;

import com.techwells.applicationMarket.domain.Notice;
import com.techwells.applicationMarket.domain.rs.NoticeAdmin;
import com.techwells.applicationMarket.util.PagingTool;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer noticeId);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Integer noticeId);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKeyWithBLOBs(Notice record);

    int updateByPrimaryKey(Notice record);
    
    int countTotal(PagingTool pagingTool);
    
    /**
     * 分页获取公告列表
     * @param pagingTool
     * @return
     */
    List<NoticeAdmin> selectNoticeAdmins(PagingTool pagingTool);
    
    
}