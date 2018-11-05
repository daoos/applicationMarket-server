package com.techwells.applicationMarket.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sun.util.logging.resources.logging;

import com.techwells.applicationMarket.domain.RecommendType;
import com.techwells.applicationMarket.service.RecommendTypeService;
import com.techwells.applicationMarket.service.impl.AppServiceImpl;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 推荐分类的controller
 * 
 * @author Administrator
 *
 */
@RestController
public class RecommendTypeController {
	@Resource
	private RecommendTypeService recommendTypeService;
	private Logger logging = Logger.getLogger(RecommendTypeController.class);

	/**
	 * 添加推荐分类
	 * 
	 * @param title
	 *            分类的名称
	 * @param sort
	 *            首页展示的顺序
	 * @return
	 */
	@RequestMapping("/recommendType/addRecommendType")
	public Object addRecommendType(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();

		String title = request.getParameter("title");
		String sort = request.getParameter("sort"); // 首页展示的顺序

		// 校验参数：
		if (StringUtils.isEmpty(title)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分类名称不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(sort)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("首页展示的顺序不能为空");
			return resultInfo;
		}

		// 封装数据
		RecommendType recommendType = new RecommendType();
		recommendType.setCreateDate(new Date());
		recommendType.setSort(Integer.parseInt(sort));
		recommendType.setTitle(title);

		// 调用service层的数据
		try {
			Object object = recommendTypeService
					.addRecommendType(recommendType);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加异常");
			return resultInfo;
		}
	}

	/**
	 * 删除
	 * 
	 * @param recommendTypeId
	 *            id
	 * @return
	 */
	@RequestMapping("/recommendType/deleteRecommendType")
	public Object deleteRecommendType(Integer recommendTypeId) {
		ResultInfo resultInfo = new ResultInfo();

		if (StringUtils.isEmpty(recommendTypeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("推荐分类Id不能为空");
			return resultInfo;
		}

		try {
			Object object = recommendTypeService
					.deleteRecommendType(recommendTypeId);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除异常");
			return resultInfo;
		}

	}

	/**
	 * 获取推荐分类的详细信息
	 * 
	 * @param recommendTypeId
	 *            推荐分类Id
	 * @return
	 */
	@RequestMapping("/recommendType/getRecommendType")
	public Object getRecommendType(Integer recommendTypeId) {
		ResultInfo resultInfo = new ResultInfo();
		if (StringUtils.isEmpty(recommendTypeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("推荐分类Id不能为空");
			return resultInfo;
		}

		try {
			Object object = recommendTypeService
					.getRecommendType(recommendTypeId);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}

	/**
	 * 修改推荐分类信息
	 * @param title  分类名称
	 * @param sort 首页展示的顺序
	 * @param typeId  分类Id
	 * @return
	 */
	@RequestMapping("/recommendType/modifyRecommendType")
	public Object modifyRecommendType(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();

		String title = request.getParameter("title");
		String sort = request.getParameter("sort"); // 首页展示的顺序
		String typeId = request.getParameter("typeId");

		// 校验参数：
		if (StringUtils.isEmpty(title)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分类名称不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(sort)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("首页展示的顺序不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(typeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("分类Id不能为空");
			return resultInfo;
		}
		
		
		//封装参数
		RecommendType recommendType=new RecommendType();
		recommendType.setRecommendTypeId(Integer.parseInt(typeId));
		recommendType.setTitle(title);
		recommendType.setSort(Integer.parseInt(sort));
		
		//调用service层中的方法
		try {
			Object object=recommendTypeService.modifyRecommendType(recommendType);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改异常");
			return resultInfo;
		}
	}
	

	/**
	 * 分页获取推荐分类列表
	 * 
	 * @param pageNum
	 *            当前的页数
	 * @param pageSize
	 *            每页显示的数量
	 * @param title 推荐分类的标题
	 * @return
	 */
	@RequestMapping("/recommendType/getRecommendTypeList")
	public Object getRecommendTypeList(Integer pageNum, Integer pageSize,String title) {
		ResultInfo resultInfo = new ResultInfo();

		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setMessage("当前页数不能为空");
			resultInfo.setCode("-1");
			return resultInfo;
		}

		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setMessage("每页显示的数量不能为空");
			resultInfo.setCode("-1");
			return resultInfo;
		}
		
		Map<String, Object> params=new HashMap<String, Object>();
		
		//如果推荐分类标题不为空
		if (!StringUtils.isEmpty(title)) {
			params.put("title", title);
		}

		PagingTool pagingTool = new PagingTool(pageNum, pageSize);
		pagingTool.setParams(params);
		
		try {
			Object object = recommendTypeService
					.getRecommendTypeList(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}

	
	/**
	 * 根据推荐分类的Id获取更多的应用
	 * @param pageNum
	 * @param pageSize
	 * @param typeId  分类的Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/recommendType/getAppList")
	public Object getAppList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		String typeId=request.getParameter("typeId");  //分类的Id
		String platform=request.getParameter("platform");  //平台  1 安卓 2 ios

		
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
		
		if (StringUtils.isEmpty(typeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("推荐分类Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(platform)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("支持的平台不能为空");
			return resultInfo;
		}
		
		
		
		//封装参数
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("typeId", Integer.parseInt(typeId));
		params.put("platform", Integer.parseInt(platform));
		pagingTool.setParams(params);
		try {
			Object object=recommendTypeService.getAppList(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 获取推荐分类的详细信息，包括推荐的应用
	 * @param typeId  应用Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/recommendType/getDetailInfo")
	public Object getRecommendTypeDetail(HttpServletRequest request){
			ResultInfo resultInfo=new ResultInfo();
			String typeId=request.getParameter("typeId");
			if (StringUtils.isEmpty(typeId)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("分类Id不能为空");
				return resultInfo;
			}
			
			try {
				Object object=recommendTypeService.getDetailInfo(Integer.parseInt(typeId));
				return object;
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("-1");
				resultInfo.setMessage("获取异常");
				return resultInfo;
			}
			
	}
	
	
	
	/**
	 * 从推荐分类中移除软件
	 * @param request
	 * @return
	 */
	@RequestMapping("/recommendType/removeApp")
	public Object removeApp(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");
		String recommendTypeId=request.getParameter("recommendTypeId");
		
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(recommendTypeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("推荐分类Id不能为空");
			return resultInfo;
		}
		
		
		//调用service层的方法
		try {
			Object object=recommendTypeService.removeApp(Integer.parseInt(appId),Integer.parseInt(recommendTypeId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
