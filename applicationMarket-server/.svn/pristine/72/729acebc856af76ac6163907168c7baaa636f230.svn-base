package com.techwells.applicationMarket.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.Report;
import com.techwells.applicationMarket.domain.ReportImage;
import com.techwells.applicationMarket.domain.ReportReason;

@Transactional
public interface AppReportService {
	
	/**
	 * 添加举报
	 * @param report  举报的实体类
	 * @param reportImages  图片
	 * @return
	 * @throws Exception
	 */
	Object addReport(Report report,List<ReportImage> reportImages)throws Exception;
	
	/**
	 * 获取举报的原因列表
	 * @return
	 * @throws Exception
	 */
	Object getReportReasonList()throws Exception;
}
