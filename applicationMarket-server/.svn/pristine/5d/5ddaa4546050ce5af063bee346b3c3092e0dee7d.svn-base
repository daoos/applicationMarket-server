package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.Aaa;
import com.techwells.applicationMarket.util.PagingTool;

/**
 * 模板的service
 * @author 陈加兵
 */
@Transactional
public interface AaaService {
	
	
	/**
	 * 添加模版
	 * @param aaa
	 * @return
	 * @throws Exception
	 */
	Object addAaa(Aaa aaa)throws Exception;
	
	/**
	 * 根据模版Id获取信息
	 * @param aaaId
	 * @return
	 * @throws Exception
	 */
	Object getAaaById(Integer aaaId)throws Exception;
	
	
	/**
	 * 修改模版
	 * @param aaa
	 * @return
	 * @throws Exception
	 */
	Object modifyAaa(Aaa aaa)throws Exception;
	
	/**
	 * 根据模版Id删除数据
	 * @param aaaId
	 * @return
	 * @throws Exception
	 */
	Object deleteAaa(Integer aaaId)throws Exception;
	
	/**
	 * 分页获取模版数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getAaaList(PagingTool pagingTool)throws Exception;
	
	
	
	
	
}
