package com.techwells.applicationMarket.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.service.AppVersionService;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 安装包的Controller
 * @author Administrator
 *
 */
@RestController
public class AppVersionController {
	
	@Resource
	private AppVersionService appVersionService;
	
	/**
	 * 审核通过
	 * @param versionId  版本Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/version/examinPass")
	public Object examinPass(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String versionId=request.getParameter("versionId");
		
		//校验参数
		if (StringUtils.isEmpty(versionId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("版本Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=appVersionService.exmainPass(Integer.parseInt(versionId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 审核失败 
	 * @param versionId 版本Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/version/examinFail")
	public Object examinFail(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String versionId=request.getParameter("versionId");
		//校验参数
		if (StringUtils.isEmpty(versionId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("版本Id不能为空");
			return resultInfo;
		}
		//调用service层的方法
		try {
			Object object=appVersionService.exmainFail(Integer.parseInt(versionId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核异常");
			return resultInfo;
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}

