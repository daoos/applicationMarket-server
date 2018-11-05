package com.techwells.applicationMarket.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.sql.dialect.postgresql.ast.expr.PGAnalytic;
import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.domain.AppComment;
import com.techwells.applicationMarket.service.AppCommentService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@RestController
public class AppCommentController {
	@Resource
	private AppCommentService appCommentService;
	
	
	/**
	 * 添加评论
	 * @param appId 应用Id
	 * @param userId  用户Id
	 * @param score 评论的分数
	 * @param content 评论的内容
	 * @param request
	 * @return
	 */
	@RequestMapping("/comment/addComment")
	public Object addComment(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");
		String userId=request.getParameter("userId");
		String content=request.getParameter("content");
		String score=request.getParameter("score");
		
		//校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("评论内容不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(score)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分数不能为空");
			return resultInfo;
		}
		
		//封装参数
		
		AppComment comment=new AppComment();
		comment.setCreateDate(new Date());
		comment.setAppId(Integer.parseInt(appId));
		comment.setUserId(Integer.parseInt(userId));
		comment.setContent(content);
		comment.setScore(Integer.parseInt(score));
		
		//调用service层的方法
		try {
			Object object=appCommentService.addComment(comment);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 获取评论列表
	 * @param appId  应用Id
	 * @param pageNum  当前页数
	 * @param pageSize  每页显示的数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/comment/getCommentList")
	public Object getCommentList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		//校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
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
		
		
		
		//封装请求参数
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("appId", Integer.parseInt(appId));
		pagingTool.setParams(params);
		
		//调用service层的方法
		
		try {
			Object object=appCommentService.getCommentList(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
