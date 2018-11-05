package com.techwells.applicationMarket.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.techwells.applicationMarket.domain.AppType;
import com.techwells.applicationMarket.service.AppTypeService;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 应用类型的controller
 * @author Administrator
 *
 */
@RestController
public class AppTypeController {
	@Resource
	private AppTypeService appTypeService;
	
	/**
	 * 获取分类列表，前台
	 * @param request
	 * @return
	 */
	@RequestMapping("/appType/getAppTypeList")
	public Object getAppTypeList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		try {
			Object object=appTypeService.getAppTypeList();
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 设置为热门分类
	 * @param request
	 * @return
	 */
	@RequestMapping("/appType/hot")
	public Object hot(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String typeId=request.getParameter("typeId");
		if (StringUtils.isEmpty(typeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分类Id不能为空");
			return resultInfo;
		}
		//封装数据
		AppType appType=new AppType();
		appType.setIsHot(1);  //设置为热门分类
		
		try {
			Object object=appTypeService.hot(appType);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}		
	}
	
	
	
	/**
	 * 取消热门
	 * @param request
	 * @return
	 */
	@RequestMapping("/appType/cancelHot")
	public Object cancelHot(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String typeId=request.getParameter("typeId");  //分类Id
		if (StringUtils.isEmpty(typeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分类Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		AppType appType=new AppType();
		appType.setIsHot(0);  //设置为热门分类
		
		try {
			Object object=appTypeService.cancelHot(appType);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
