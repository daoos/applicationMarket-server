package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.Notice;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface NoticeService {	
	/**
	 * 添加公告
	 * @param notice  公告对象
	 * @return
	 * @throws Exception
	 */
	Object addNotice(Notice notice)throws Exception;
	
	/**
	 * 删除公告
	 * @param noticeId
	 * @return
	 * @throws Exception
	 */
	Object deleteNotice(Integer noticeId)throws Exception;
	
	/**
	 * 获取公告详情
	 * @param noticeId 公告Id
	 * @return
	 * @throws Exception
	 */
	Object getNotice(Integer noticeId)throws Exception;
	
	
	/**
	 * 分页获取公告
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getNoticeList(PagingTool pagingTool)throws Exception;
	
	/**
	 * 修改公告
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	Object modifyNotice(Notice notice)throws Exception;
	
	
	

}
