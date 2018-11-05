package com.techwells.applicationMarket.dao;

import com.techwells.applicationMarket.domain.FeedBackImage;

public interface FeedBackImageMapper {
    int deleteByPrimaryKey(Integer feedbackImageId);

    int insert(FeedBackImage record);

    int insertSelective(FeedBackImage record);

    FeedBackImage selectByPrimaryKey(Integer feedbackImageId);

    int updateByPrimaryKeySelective(FeedBackImage record);

    int updateByPrimaryKey(FeedBackImage record);
    
    /**
     * 根据反馈Id删除对应的图片
     * @param feedBackId  反馈信息的Id
     * @return
     */
    int deleteImageBatch(Integer feedBackId);
}