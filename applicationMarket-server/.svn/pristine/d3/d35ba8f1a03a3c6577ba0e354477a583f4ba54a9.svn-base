package com.techwells.applicationMarket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.FeedBackImageMapper;
import com.techwells.applicationMarket.dao.FeedBackMapper;
import com.techwells.applicationMarket.domain.FeedBack;
import com.techwells.applicationMarket.domain.FeedBackImage;
import com.techwells.applicationMarket.domain.rs.FeedBackImageUserProvinceVos;
import com.techwells.applicationMarket.service.FeedBackService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class FeedBackServiceImpl implements FeedBackService {
	
	@Resource
	private FeedBackMapper feedBackMapper;
	
	@Resource
	private FeedBackImageMapper imageMapper;
		
	@Override
	public Object addFeedBack(FeedBack feedBack,
			List<FeedBackImage> feedBackImages) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//添加返回内容
		int count=feedBackMapper.insertSelective(feedBack);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("反馈失败");
			return resultInfo;
		}
		
		//自增主键的返回，保存图片信息
		for (FeedBackImage feedBackImage : feedBackImages) {
			feedBackImage.setFeedbackId(feedBack.getFeedbackId());  //设置反馈Id
			int count1=imageMapper.insertSelective(feedBackImage);
			//如果一张图片没有保存成功，那么必须回滚数据
			if (count1==0) {
				throw new RuntimeException();
			}
		}
		
		resultInfo.setMessage("提交成功");
		return resultInfo;
	}

	@Override
	public Object getFeedBackList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<FeedBackImageUserProvinceVos> feedBackImageUserProvinceVos=feedBackMapper.selectFeedBackImageUserProvinceVos(pagingTool);
		int count=feedBackMapper.countTotal(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(feedBackImageUserProvinceVos);
		resultInfo.setTotal(count);
		return resultInfo;
	}

	@Override
	public Object replyFeedBack(FeedBack feedBack) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=feedBackMapper.updateByPrimaryKeySelective(feedBack);  
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("回复失败");
			return resultInfo;
		}
		
		//消息推送
		
		resultInfo.setMessage("回复成功");
		return resultInfo;
	}

	@Override
	public Object deleteFeedBack(Integer feedBackId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=feedBackMapper.deleteByPrimaryKey(feedBackId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		
		//删除对应的图片信息
		int count1=imageMapper.deleteImageBatch(feedBackId);
		resultInfo.setMessage("删除成功");
		return resultInfo;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
