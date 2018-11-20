package com.techwells.applicationMarket.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.techwells.applicationMarket.domain.FeedBack;
import com.techwells.applicationMarket.domain.FeedBackImage;
import com.techwells.applicationMarket.service.FeedBackService;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.Base64Util;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.StringUtil;

/**
 * 反馈的controller
 * @author Administrator
 *
 */
@RestController
public class FeedBackController {	
	@Resource
	private FeedBackService feedBackService;
	
	Logger logging=Logger.getLogger(FeedBackController.class);
	
	
	/**
	 * 添加反馈
	 * @param content 内容
	 * @images 图片的base64数组
	 * @param request
	 * @return
	 */
	@RequestMapping("/feedBack/addFeedBack")
	public Object addFeedBack(HttpServletRequest request,@RequestParam(value="images",required=false)MultipartFile[] images){
		ResultInfo resultInfo=new ResultInfo();
		String content=request.getParameter("content");
//		String[] images=request.getParameterValues("images");   //图片的数组
		String userId=request.getParameter("userId");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		
		if(!StringUtil.isNumber(userId)){
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id必须 数字");
			return resultInfo;
		}
		
		
		FeedBack feedBack=new FeedBack();
		feedBack.setUserId(Integer.parseInt(userId));
		feedBack.setCreateDate(new Date());
		
		if (!StringUtils.isEmpty(content)) {
			feedBack.setContent(content);
		}
		
		if (images!=null&&images.length>3) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("图片最多上传三张");
			return resultInfo;
		}
		
		//如果没有反馈内容
		if (StringUtils.isEmpty(content)&&images==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请填写反馈的内容");
			return resultInfo;
		}
		
		List<FeedBackImage> feedBackImages=new ArrayList<FeedBackImage>(3);  //最多三张
		
		//上传了图片
		if (images!=null&&images.length>0) {
			
			//遍历图片上传
			for (MultipartFile image : images) {
				String fileName=System.currentTimeMillis()+image.getOriginalFilename();
				String path=ApplicationMarketConstants.UPLOAD_PATH+"feedBack-image/";
				File targetFile=new File(path,fileName);
				String imageUrl=ApplicationMarketConstants.UPLOAD_URL+"feedBack-image/"+fileName;
				//创建文件夹
				if (!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				
				try {
					image.transferTo(targetFile);
				} catch (Exception e) {
					resultInfo.setCode("-1");
					resultInfo.setMessage("上传图片失败");
					return resultInfo;
				}
				
				//写入成功之后
				FeedBackImage image2=new FeedBackImage();
				image2.setImageUrl(imageUrl);
				image2.setCreateDate(new Date());
				feedBackImages.add(image2); 
			}
		}
		
		try {
			Object object=feedBackService.addFeedBack(feedBack, feedBackImages);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加反馈异常");
			return resultInfo;
		}
		
	}
	
	/**
	 * 分页获取反馈信息
	 * @param pageNum  当前页数
	 * @param pageSize  每页显示的数量
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/feedBack/getFeedBackList")
	public Object getFeedBackList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String account=request.getParameter("account");  //用户账号
		String realName=request.getParameter("realName");
		String mobile=request.getParameter("mobile");
		String provinceCode=request.getParameter("provinceCode");
		String feedBackDate=request.getParameter("feedBackDate");
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		logging.debug("------------------------>"+feedBackDate);
		
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
		
		
		
		Map<String, Object> params=new HashMap<String, Object>();  //封装请求参数
		
		//如果这些参数不为空，那么就封装进去
		if (!StringUtils.isEmpty(account)) {
			params.put("account", account);
		}
		
		if (!StringUtils.isEmpty(realName)) {
			params.put("realName", realName);
		}
		
		if (!StringUtils.isEmpty(mobile)) {
			params.put("mobile", mobile);
		}
		
		if (!StringUtils.isEmpty(provinceCode)) {
			params.put("provinceCode", provinceCode);
		}
		
		if (!StringUtils.isEmpty(feedBackDate)) {
			params.put("feedBackDate", feedBackDate);
		}
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		pagingTool.setParams(params);
		
		try {
			Object object=feedBackService.getFeedBackList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
		
		
		
	}
	
	
	/**
	 * 根据Id删除反馈信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/feedBack/deleteFeedBack")
	public Object deleteFeedBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String feedBackId=request.getParameter("feedBackId");
		
		//校验参数
		if (StringUtils.isEmpty(feedBackId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("反馈信息的Id不能为空");
			return resultInfo;
		}
		
		if (!StringUtil.isNumber(feedBackId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("反馈Id只能是数字");
			return resultInfo;
		}
		
		//调用service层的方法
		
		try {
			Object object=feedBackService.deleteFeedBack(Integer.parseInt(feedBackId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除异常");
			return resultInfo;
		}
		
	}
	
	
	
	
	/**
	 * 回复评论
	 * @param request
	 * @return
	 */
	@RequestMapping("/feedBack/replyFeedBack")
	public Object replyFeedback (HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String content=request.getParameter("content");
		
		String feedBackId=request.getParameter("feedBackId");
		
		String adminId=request.getParameter("adminId");
		
		if (StringUtils.isEmpty(adminId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("回复的内容不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(feedBackId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("反馈Id不能为空");
			return resultInfo;
		}
		
		FeedBack feedBack=new FeedBack();
		feedBack.setFeedbackId(Integer.parseInt(feedBackId));
		feedBack.setContent(content);
		feedBack.setAdminId(Integer.parseInt(adminId));
		
		try {
			Object object=feedBackService.replyFeedBack(feedBack);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("回复异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 上传反馈cd
	 * @param files
	 * @return
	 */
	@RequestMapping("/feedBack/uploadImage")
	public Object uploadImage(@RequestParam("myFile")MultipartFile[] files){
		
		//封装返回结果
		Map<String, Object> map=new HashMap<String, Object>();
		
		List<String> imageUrls=new ArrayList<String>();  //存储图片地址
		
		for (MultipartFile file : files) {
			imageUrls.add("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=50fc711ddf1b0ef473e89e5eedc451a1/b151f8198618367a2e8a46ee23738bd4b31ce586.jpg");
			logging.debug("----------------------------------"+file.getOriginalFilename()+"----------------------------------");
		}
		map.put("errno","0");  //错误代码
		map.put("data", imageUrls);
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
