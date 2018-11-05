package com.techwells.applicationMarket.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.domain.Report;
import com.techwells.applicationMarket.domain.ReportImage;
import com.techwells.applicationMarket.service.AppReportService;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.Base64Util;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 举报的controller
 * @author Administrator
 */
@RestController
public class ReportController {
	
	@Resource
	private AppReportService appReportService;
	
	
	/**
	 * 获取举报原因列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/report/getReasons")
	public Object getReasons(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		//调用service的方法
		try {
			Object object=appReportService.getReportReasonList();
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	
	
	/**
	 * 添加举报内容
	 * @param reasons  举报原因的Id，逗号分割
	 * @param content  举报的内容
	 * @param images  图片  Base64封装的数组
	 * @param request
	 * @return
	 */
	@RequestMapping("/report/addReport")
	public Object addReport(HttpServletRequest request,@RequestParam(value="images",required=false)MultipartFile[] images){
		ResultInfo resultInfo=new ResultInfo();
		String reasons=request.getParameter("reasons");  //原因Id，逗号分割
		String content=request.getParameter("content");  //举报的内容
//		String[] images=request.getParameterValues("images");  //图片的base64的数组
		
		//校验参数
		
		if (StringUtils.isEmpty(reasons)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("原因Id不能为空");
			return resultInfo;
		}
		
		//如果图片大于三张
		if (images!=null&&images.length>3) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("图片只能上传 0 - 3张");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("举报描述不能为空");
			return resultInfo;
		}
		
		
		List<ReportImage> reportImages=new ArrayList<ReportImage>();
		
		//上传图片
		for (MultipartFile image : images) {
			
			String fileName=System.currentTimeMillis()+image.getOriginalFilename();  //文件名称
			String filepath=ApplicationMarketConstants.UPLOAD_PATH+"report-image/"; //存储在服务器的路径
			File targetFile=new File(filepath,fileName);
			String fileUrl=ApplicationMarketConstants.UPLOAD_URL+"report-image/"+fileName;  //文件的访问路径
//			//转换成byte[]
//			try {
//				byte[] data=Base64Util.GenerateImage(image);
//				
//				FileOutputStream outputStream=new FileOutputStream(targetFile);
//				outputStream.write(data);
//				outputStream.flush();
//				outputStream.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//				resultInfo.setCode("-1");
//				resultInfo.setMessage("上传文件异常");
//				return resultInfo;
//			}
			
			//保存图片
			try {
				image.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件异常");
				return resultInfo;
			}
			
			
			
			//封住数据
			ReportImage reportImage=new ReportImage();
			reportImage.setCreateDate(new Date());
			reportImage.setImageUrl(fileUrl);
			reportImages.add(reportImage);
		}
		
		//封装数据
		Report report=new Report();
		
		report.setCreateDate(new Date());
		report.setDescription(content);
		report.setReportReasonId(reasons);
		
		//调用service层的方法
		
		try {
			Object object=appReportService.addReport(report, reportImages);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
