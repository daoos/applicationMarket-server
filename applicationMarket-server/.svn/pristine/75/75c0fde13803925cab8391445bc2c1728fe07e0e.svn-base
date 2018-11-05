package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.AppComment;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface AppCommentService {
	
	/**
	 * 添加评论
	 * @param appComment  
	 * @return
	 * @throws Exception
	 */
	Object addComment(AppComment appComment)throws Exception;
	
	/**
	 * 分页获取该引用的全部评论
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	Object getCommentList(PagingTool pagingTool)throws Exception;
	
}
