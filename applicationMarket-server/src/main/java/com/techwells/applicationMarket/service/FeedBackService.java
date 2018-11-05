package com.techwells.applicationMarket.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.FeedBack;
import com.techwells.applicationMarket.domain.FeedBackImage;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface FeedBackService {
	
	/**
	 * 添加反馈
	 * @param feedBack
	 * @param feedBackImages  反馈的图片
	 * @return
	 * @throws Exception
	 */
	Object addFeedBack(FeedBack feedBack,List<FeedBackImage> feedBackImages)throws Exception;
	
	/**
	 * 分页获取反馈的内容
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getFeedBackList(PagingTool pagingTool)throws Exception;
	
	/**
	 * 回复反馈信息，时间就是一个修改的操作
	 * @param feedBack  反馈的对象
	 * @return
	 * @throws Exception
	 */
	Object replyFeedBack(FeedBack feedBack)throws Exception;
	
	/**
	 * 删除反馈信息
	 * @param feedBackId
	 * @return
	 * @throws Exception
	 */
	Object deleteFeedBack(Integer feedBackId)throws Exception;
	
	
	
	
}	
