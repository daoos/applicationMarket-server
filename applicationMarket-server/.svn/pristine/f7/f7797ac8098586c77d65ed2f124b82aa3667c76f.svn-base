package com.techwells.applicationMarket.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.domain.Message;
import com.techwells.applicationMarket.service.MessageService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 消息的controller
 * @author Administrator
 *
 */
@RestController
public class MessageController {
	@Resource
	private MessageService messageService;
	
	
	/**
	 * 添加消息
	 * @param title 标题
	 * @param content  内容
	 * @param request
	 * @return
	 */
	@RequestMapping("/message/addMessage")
	public Object addMessage(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		
		//标题
		if (StringUtils.isEmpty(title)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("标题不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("内容给不能为空");
			return resultInfo;
		}
		
		Message message=new Message();
		message.setContent(content);
		message.setCreateDate(new Date());
		message.setTitle(title);
		
		try {
			Object object=messageService.addMessage(message);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取消息详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/message/getMessage")
	public Object getMessage(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String messageId=request.getParameter("messageId");
		
		if (StringUtils.isEmpty(messageId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("消息Id不能为空");
			return resultInfo;
		}
		
		//调用servce层的方法
		try {
			Object object=messageService.getMessage(Integer.parseInt(messageId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取消息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/message/getMessageList")
	public Object getMessageList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前页数不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("每页显示的数量不能为空");
			return resultInfo;
		}
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		try {
			Object object=messageService.getMessageList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 删除消息
	 * @param messageId  消息Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/message/delete")
	public Object deleteMessage(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String messageId=request.getParameter("messageId");
		if (StringUtils.isEmpty(messageId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("消息Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=messageService.delete(Integer.parseInt(messageId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
