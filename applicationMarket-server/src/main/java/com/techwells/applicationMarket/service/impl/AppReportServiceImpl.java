package com.techwells.applicationMarket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.dao.ReportImageMapper;
import com.techwells.applicationMarket.dao.ReportMapper;
import com.techwells.applicationMarket.dao.ReportReasonMapper;
import com.techwells.applicationMarket.domain.Report;
import com.techwells.applicationMarket.domain.ReportImage;
import com.techwells.applicationMarket.domain.ReportReason;
import com.techwells.applicationMarket.service.AppReportService;
import com.techwells.applicationMarket.util.ResultInfo;


@Service
public class AppReportServiceImpl implements AppReportService {

	@Resource
	private ReportMapper reportMapper;
	
	@Resource
	private ReportImageMapper reportImageMapper;
	
	@Resource
	private ReportReasonMapper reportReasonMapper;

	
	@Override
	public Object addReport(Report report,
			List<ReportImage> reportImages) throws Exception {
		
		ResultInfo resultInfo=new ResultInfo();
		
		//存储举报
		int count=reportMapper.insertSelective(report);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		
		//保存图片
		//设置举报的id
		for (ReportImage reportImage : reportImages) {
			reportImage.setReportId(report.getReportId());  //设置id
			int count1=reportImageMapper.insertSelective(reportImage);
			//如果插入失败，那么直接回滚
			if (count1==0) {
				throw new RuntimeException();
			}
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}



	@Override
	public Object getReportReasonList() throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<ReportReason> reasons=reportReasonMapper.selectReasons();
		resultInfo.setMessage("获取成功");
		resultInfo.setTotal(reasons.size());
		resultInfo.setResult(reasons);
		return resultInfo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
		
	
	
}
