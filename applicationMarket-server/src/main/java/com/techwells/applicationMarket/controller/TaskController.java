package com.techwells.applicationMarket.controller;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StopWatch.TaskInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.domain.Task;
import com.techwells.applicationMarket.service.TaskService;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.DateUtil;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 任务的controller
 * @author Administrator
 *
 */
@RestController
public class TaskController {
	@Resource
	private TaskService taskService;
	
	/**
	 * 添加任务
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/addTask")
	public Object addTask(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String taskName=request.getParameter("taskName");  //任务名称
		String introduction=request.getParameter("introduction");  //简介
		String typeId=request.getParameter("typeId");   //任务类型
		String appId=request.getParameter("appId");   //应用Id，只有下载
		String link=request.getParameter("link");  //链接
		String rewardMoney=request.getParameter("money");
		String allowNum=request.getParameter("num");  //可以完成的次数
		String poupLeve=request.getParameter("poupLeve");   //弹窗等级
		String startDate=request.getParameter("startDate");  //开始日期
		String endDate=request.getParameter("endDate");   //结束时间
		String status=request.getParameter("status");   //状态
		String activated=request.getParameter("activated");  //奖励的类型  1 墨客币 2 井通
		String publishId=request.getParameter("publishId");  //发布人Id
		
		
		//校验参数
		if (StringUtils.isEmpty(publishId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发布Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(taskName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(introduction)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务简介不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(typeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务类别不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(activated)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("奖励的类型不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(rewardMoney)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("奖励的墨客币不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(allowNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("允许下载的次数不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(poupLeve)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("弹窗等级不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(startDate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("开始时间不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(endDate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("结束时间不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务状态不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(link)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("链接不能为空");
//			return resultInfo;
//		}
		
		
		//判断添加的任务中的app是否已经上架了，如果没有上架，不可以发布,我们在搜索的时候就已经进行了筛选，因此这里不用判断了
		
		
		//封装数据
		
		Task task=new Task();
		
		//如果类别为下载安装类，那么需要指定的应用Id
		if (typeId.equals("1")) {
			if (StringUtils.isEmpty(appId)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("应用Id不能为空");
				return resultInfo;
			}
			task.setAppId(Integer.parseInt(appId));
		}
		
		task.setTaskName(taskName);
		task.setIntroduction( introduction);
		task.setTaskTypeId(Integer.parseInt(typeId));
		task.setLink( link);   //链接
		task.setRewardMoney(Double.parseDouble(rewardMoney));
		task.setPoupLeve(Integer.parseInt(poupLeve));
		task.setAllowNumber(Integer.parseInt(allowNum));
		task.setStatus(Integer.parseInt(status));
		task.setActivated(Integer.parseInt(activated));  //奖励类型
		task.setPublishId(Integer.parseInt(publishId));
		task.setCreateDate(new Date());
		
		String pattern="yyyy-MM-dd HH:mm:ss";
		try {
			task.setStartDate(DateUtil.getDateFromString(startDate, pattern));
			task.setEndDate(DateUtil.getDateFromString(endDate, pattern));
		} catch (ParseException e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("日期转换异常");
			return resultInfo;
		}
		
		
		//调用service层的方法
		try {
			Object object=taskService.addTask(task);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取任务详情
	 * @param taskId  任务Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/getTask")
	public Object getTaskDetail(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String taskId=request.getParameter("taskId");
		if (StringUtils.isEmpty(taskId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=taskService.getTask(Integer.parseInt(taskId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
		
	}
	
	
	/**
	 * 删除任务
	 * @param taskId  任务Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/deleteTask")
	public Object deleteTask(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String taskId=request.getParameter("taskId");
		
		if (StringUtils.isEmpty(taskId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=taskService.deleteTask(Integer.parseInt(taskId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取任务分类列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/getTaskTypeList")
	public Object getTaskTypeList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		//调用service层的方法
		try {
			Object object=taskService.getTaskTypeList();
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取任务列表  后台使用
	 * @param 
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/getTaskList")
	public Object getTaskList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		//可选参数 查询用
		String taskId=request.getParameter("taskId");
		String taskName=request.getParameter("taskName");  //任务名称
		String taskTypeId=request.getParameter("typeId");  //任务类别
		String poupLeve=request.getParameter("poupLeve");  //弹窗级别
		String status=request.getParameter("status");
		String publishDate=request.getParameter("publishDate");   //发布时间
		
		//校验参数
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
		
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		Map<String, Object> params=new HashMap<String, Object>();  
		
		//封装请求参数
		if (!StringUtils.isEmpty(taskId)) {
			params.put("taskId", Integer.parseInt(taskId));
		}
		
		if (!StringUtils.isEmpty(taskName)) {
			params.put("taskName", taskName);
		}
		
		if (!StringUtils.isEmpty(taskTypeId)) {
			params.put("taskTypeId", Integer.parseInt(taskTypeId));
		}
		
		if (!StringUtils.isEmpty(poupLeve)) {
			params.put("poupLeve", Integer.parseInt(poupLeve));
		}
		
		if (!StringUtils.isEmpty(status)) {
			params.put("status", Integer.parseInt(status));
		}
		
		//封装日期，需要转换类型
		if (!StringUtils.isEmpty(publishDate)) {
			params.put("publishDate", publishDate);
		}
		
		pagingTool.setParams(params);
		
		//调用service层的方法
		try {
			Object object=taskService.getTaskList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 任务生效或者失效
	 * @param taskId  任务Id
	 * @param status 状态  1 生效  0 失效
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/UpOrUnderTask")
	public Object UpOrUnderTask(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String taskId=request.getParameter("taskId"); 
		String status=request.getParameter("status");
		
		//校验参数
		if (StringUtils.isEmpty(taskId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务的状态不能为空不能为空");
			return resultInfo;
		}
		
		
		Task task=new Task();   //封装参数
		task.setTaskId(Integer.parseInt(taskId));
		task.setStatus(Integer.parseInt(status));
		
		//调用service层的方法
		try {
			Object object =taskService.modifyTask(task);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 获取任务清单 分页  前台
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/getTaskDetailList")
	public Object getTaskDetailList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		String pageNum=request.getParameter("pageNum");   //当前页数  
		String pageSize=request.getParameter("pageSize");  //每页显示的数量
		String platform=request.getParameter("platform");   //平台支持
		
		//校验参数
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
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
		
		if (StringUtils.isEmpty(platform)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("支持的平台不能为空");
			return resultInfo;
		}
		
		
		//封装参数
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", Integer.parseInt(userId));
		params.put("platform", Integer.parseInt(platform));
		pagingTool.setParams(params);
		
		//调用service的方法
		try {
			Object object = taskService.getDetailList(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
		
	}
	
	/**
	 * 领取任务奖励
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/receiveReward")
	public Object receiveReward(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String taskDetailId=request.getParameter("taskDetailId");
		
		if (StringUtils.isEmpty(taskDetailId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务明细Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=taskService.receiveReward(Integer.parseInt(taskDetailId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	@RequestMapping("/task/upload")
	public Object upload(@RequestParam(value="file")MultipartFile file){
		ResultInfo resultInfo=new ResultInfo();
		
		String fileName=file.getOriginalFilename();
		String path=ApplicationMarketConstants.UPLOAD_PATH+"task-image/";
		File targetFile=new File(path,fileName);
		String url=ApplicationMarketConstants.UPLOAD_URL+"task-image/"+fileName;
		
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("上传失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("上传成功");
		resultInfo.setResult(url);
		return resultInfo;
	}
	
	
	
	/**
	 * 修改任务
	 * @param request
	 * @return
	 */
	@RequestMapping("/task/modifyTask")
	public Object modifyTask(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String taskId=request.getParameter("taskId");  //任务Id
		String taskName=request.getParameter("taskName");  //任务名称
		String introduction=request.getParameter("introduction");  //简介
		String appId=request.getParameter("appId");   //应用Id，只有下载，可选
		String link=request.getParameter("link");  //链接  可选，
		String rewardMoney=request.getParameter("money");
		String allowNum=request.getParameter("num");  //可以完成的次数
		String poupLeve=request.getParameter("poupLeve");   //弹窗等级
		String startDate=request.getParameter("startDate");  //开始日期
		String endDate=request.getParameter("endDate");   //结束时间
		String status=request.getParameter("status");   //状态
		String activated=request.getParameter("activated");  //奖励的类型  1 墨客币 2 井通
		
		//校验参数
		if (StringUtils.isEmpty(taskName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(introduction)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务简介不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(activated)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("奖励的类型不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(rewardMoney)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("奖励的墨客币不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(allowNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("允许下载的次数不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(poupLeve)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("弹窗等级不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(startDate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("开始时间不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(endDate)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("结束时间不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("任务状态不能为空");
			return resultInfo;
		}
		
		
		//封装数据
		Task task=new Task();
		task.setTaskId(Integer.parseInt(taskId));
		task.setTaskName(taskName);
		task.setIntroduction(introduction);
		if (!StringUtils.isEmpty(link)) {
			task.setLink(link);
		}
		
		task.setActivated(Integer.parseInt(activated));
		task.setRewardMoney(Double.parseDouble(rewardMoney));
		task.setAllowNumber(Integer.parseInt(allowNum));
		task.setPoupLeve(Integer.parseInt(poupLeve));
		task.setStatus(Integer.parseInt(status));
		if (!StringUtils.isEmpty(appId)) {
			task.setAppId(Integer.parseInt(appId));
		}
		String pattern="yyyy-MM-dd HH:mm:ss";
		try {
			task.setStartDate(DateUtil.getDateFromString(startDate, pattern));
			task.setEndDate(DateUtil.getDateFromString(endDate, pattern));
		} catch (ParseException e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("日期转换异常");
			return resultInfo;
		}
		
		try {
			Object object=taskService.modifyTask(task);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
