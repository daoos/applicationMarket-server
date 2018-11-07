package com.techwells.applicationMarket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.domain.Aaa;
import com.techwells.applicationMarket.service.AaaService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * Aaa的controller
 * @author 陈加兵
 */
@RestController
public class AaaController {
	
	@Resource
	private AaaService aaaService;
	
	private Logger logger=Logger.getLogger(AaaController.class);  //日志管理
	
	/**
	 * 添加模板
	 * @param request
	 * @return
	 */
	@RequestMapping("/aaa/addAaa")
	public Object addAaa(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String aaaName=request.getParameter("aaaName");
		
		if (StringUtils.isEmpty(aaaName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("aaaName不能为空");
			return resultInfo;
		}
		
		//封装数据
		Aaa aaa=new Aaa();
		aaa.setAaaName(aaaName);
		
		//调用service层的方法
		try {
			Object object=aaaService.addAaa(aaa);
			return object;
		} catch (Exception e) {
			logger.error("添加模板异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 获取模板详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/aaa/getAaaById")
	public Object getAaaById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String aaaId=request.getParameter("aaaId");
		
		if (StringUtils.isEmpty(aaaId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模版Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=aaaService.getAaaById(Integer.parseInt(aaaId));
			return object;
		} catch (Exception e) {
			logger.error("获取模板详情异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改模版
	 * @param request
	 * @return
	 */
	@RequestMapping("/aaa/modifyAaa")
	public Object modifyAaa(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String aaaId=request.getParameter("aaaId");
		
		if (StringUtils.isEmpty(aaaId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模版Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Aaa aaa=new Aaa();
		aaa.setAaaId(Integer.parseInt(aaaId));
		
		//调用service层的方法
		try {
			Object object=aaaService.modifyAaa(aaa);
			return object;
		} catch (Exception e) {
			logger.error("修改模板异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 根据Id删除模版
	 * @param request
	 * @return
	 */
	@RequestMapping("/aaa/deleteAaaById")
	public Object deleteAaa(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String aaaId=request.getParameter("aaaId");
		
		if (StringUtils.isEmpty(aaaId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("模版Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=aaaService.deleteAaa(Integer.parseInt(aaaId));
			return object;
		} catch (Exception e) {
			logger.error("根据Id删除模板异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取模版列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/aaa/getAaaList")
	public Object getAaaList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		//校验数据
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
		
		//构造分页数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		//封装过滤条件
		Map<String, Object> params=new HashMap<String, Object>();
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=aaaService.getAaaList(pagingTool);
			return object;
		} catch (Exception e) {
			logger.error("获取模板列表异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
}
