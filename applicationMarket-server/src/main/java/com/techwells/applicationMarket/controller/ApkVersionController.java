package com.techwells.applicationMarket.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.service.ApkVersionService;
import com.techwells.applicationMarket.util.ResultInfo;

@RestController
public class ApkVersionController {
	@Resource
	private ApkVersionService apkVersionService;
	
	/**
	 * 获取最新版本的信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/apk/getLastVersion")
	public Object getLastVersion(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String type=request.getParameter("type");  //应用的类别 1 安卓 2 ios
		
		if (StringUtils.isEmpty(type)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用类型不能为空");
			return resultInfo;
		}
		
		try {
			Object object=apkVersionService.getLastApkVersion(Integer.parseInt(type));
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
}
