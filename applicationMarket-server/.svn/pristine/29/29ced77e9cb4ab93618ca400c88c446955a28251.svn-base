package com.techwells.applicationMarket.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AppCommentMapper;
import com.techwells.applicationMarket.dao.UserAppMapper;
import com.techwells.applicationMarket.domain.AppComment;
import com.techwells.applicationMarket.domain.UserApp;
import com.techwells.applicationMarket.domain.UserAppKey;
import com.techwells.applicationMarket.domain.rs.ScorePercent;
import com.techwells.applicationMarket.domain.rs.UserCommentVos;
import com.techwells.applicationMarket.service.AppCommentService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 应用评论的业务类
 * @author Administrator
 *
 */
@Service
public class AppCommentServiceImpl implements AppCommentService {
	@Resource
	private AppCommentMapper appCommentMapper;
	@Resource
	private UserAppMapper userAppMapper;

	/**
	 * 1、下载软件的才能评论
	 * 2、只能评论一次
	 */
	@Override
	public Object addComment(AppComment appComment) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//需要验证当前用户有没有下载过该软件，如果没有下载过该软件不能评论
		UserAppKey userAppKey=new UserAppKey();
		userAppKey.setAppId(appComment.getAppId());
		userAppKey.setUserId(appComment.getUserId());
		
		//查询是否下载过该软件
		UserApp userApp=userAppMapper.selectByPrimaryKey(userAppKey);
		
		if (userApp==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请先下载软件使用");
			return resultInfo;
		}
		
		
		//查询是否评论过该软件
		AppComment comment=appCommentMapper.selectAppCommentByAppIdAndUserId(appComment.getUserId(), appComment.getAppId());
		
		//已经评论过了，那么不能再评论
		if (comment!=null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("已经发表过评论了，不能再发表了");
			return resultInfo;
		}
		
		int count=appCommentMapper.insertSelective(appComment);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		
		
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@Override
	public Object getCommentList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//获取总数
		int count=appCommentMapper.countTotal(pagingTool);
		
		//获取全部的评论
		List<UserCommentVos> userCommentVos=appCommentMapper.selectUserCommentVos(pagingTool);
		
		//获取分数的百分比
		List<ScorePercent> scorePercent=appCommentMapper.selectScorePercents(pagingTool);
		
		//获取平均分数
		
		Double avgScore=appCommentMapper.selectAvgScore(pagingTool);
		
		
		//封装数据
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("commentList", userCommentVos);
		map.put("scorePercent", scorePercent);
		if (avgScore==null) {
			map.put("avgScore", 0);
		}else {
			map.put("avgScore", avgScore);
		}
		
		resultInfo.setTotal(count);
		resultInfo.setResult(map);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}
	
	
	
	
	
	
}
