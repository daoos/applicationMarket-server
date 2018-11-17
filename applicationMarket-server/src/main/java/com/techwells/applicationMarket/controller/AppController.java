package com.techwells.applicationMarket.controller;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.abi.datatypes.Int;

import tools.PaginationPlugin;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.domain.App;
import com.techwells.applicationMarket.domain.AppImage;
import com.techwells.applicationMarket.domain.AppVersion;
import com.techwells.applicationMarket.domain.User;
import com.techwells.applicationMarket.domain.UserApp;
import com.techwells.applicationMarket.service.AppService;
import com.techwells.applicationMarket.service.AppVersionService;
import com.techwells.applicationMarket.service.UserService;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.HanyuPinyinUtils;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.UploadFileUtils;

/**
 * 应用的controller
 * 
 * @author Administrator
 *
 */
@RestController
public class AppController {
	@Resource
	private AppService appService;
	@Resource
	private UserService userService;
	@Resource
	private AppVersionService versionService;
	
	/**
	 * 添加应用
	 * @param platform
	 * @param packageFile
	 * @param packageName
	 * @param versionName
	 * @param versionNum
	 * @param appTypeId
	 * @param personalRecommend
	 * @param licenseFile
	 * @param supportLanguage
	 * @param tariffType
	 * @param appIntroduction
	 * @param versionIntroduction
	 * @param supportSystem
	 * @param privacy
	 * @param logoFile
	 * @param appImage
	 * @param examineExplain
	 * @param publishDate
	 * @param publishId
	 * @param userAccount  用户账户  如果是用户添加，那么需要填写用户账户，将该应用和用户绑定起来
	 * @return
	 */
	@RequestMapping("/app/addApp")
	public Object addApp(
			String platform,
			@RequestParam(value = "packageFile", required = false) MultipartFile packageFile,
			String packageName,
			String versionName,
			String versionNum,
			String appTypeId,
			String personalRecommend,
			@RequestParam(value = "licenseFile", required = false) MultipartFile licenseFile,
			String supportLanguage,
			String tariffType,
			String appIntroduction,
			String versionIntroduction,
			String supportSystem,
			String privacy,
			String versionFeaturs,
			String appUrl,  //应用链接
			String size,  //安装包大小  ios
			String developCompany,  //开发商
//			String downloadMoney,
			@RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
			@RequestParam(value = "appImage", required = false) MultipartFile[] appImage,
			String examineExplain, String publishType, String publishId,
			String appName, String publishDay, String publishTime,String userAccount) {
		ResultInfo resultInfo = new ResultInfo();
		
		if (StringUtils.isEmpty(platform)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("平台不能为空");
			return resultInfo;
		}
		
		//如果是IOS
		if (platform.equals("2")) {
			if (StringUtils.isEmpty(appUrl)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("应用下载链接不能为空");
				return resultInfo;
			}
		}
		

		if (StringUtils.isEmpty(userAccount)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户账号不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(versionName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("版本名称不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(versionNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("版本号不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(appTypeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("软件分类不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(personalRecommend)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("个性推荐语不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(supportLanguage)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("支持语言不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(tariffType)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("资费类型不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(appIntroduction)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用简介不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(versionIntroduction)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前版本介绍不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(developCompany)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("开发商不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(supportSystem)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("支持系统不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(privacy)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("隐私权限不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(publishType)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发布时间不能为空");
			return resultInfo;
		}

		if (appImage == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用截图不能为空");
			return resultInfo;
		}

		if (logoFile == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用图标不能为空");
			return resultInfo;
		}

		if (licenseFile == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("资质许可证明不能为空");
			return resultInfo;
		}

		//如果是安卓的话，那么安装包不能为空
		if (platform.equals("1")&&packageFile == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("软件安装包不能为空");
			return resultInfo;
		}
		
		if (platform.equals("1")&&StringUtils.isEmpty(packageName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("包名不能为空");
			return resultInfo;
		}
		
		if (platform.equals("2")&&StringUtils.isEmpty(size)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("安装包大小不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(publishId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发布人Id不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(appName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用名称不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(versionFeaturs)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("新版特性不能为空");
			return resultInfo;
		}
		
		User user=null;  //用户信息
		//根据用户账号获取用户信息
		if (!StringUtils.isEmpty(userAccount)) {
			try {
				user=userService.getUser(userAccount);
				if (user==null) {
					resultInfo.setCode("-1");
					resultInfo.setMessage("该用户不存在");
					return resultInfo;
				}
				
				
				//如果用户不是开发者，或者是申请了开发者但是没有通过审核
				if (user.getIsDeveloper().equals(0)||(!user.getIsDeveloper().equals(0)&&user.getExaminStatus().equals(2))) {
					resultInfo.setCode("-1");
					resultInfo.setMessage("该用户不是开发者");
					return resultInfo;
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("-1");
				resultInfo.setMessage("获取用户信息异常");
				return resultInfo;
			}
		}

		// 校验参数 
		if ((appImage != null && appImage.length > 5)
				|| (appImage != null && appImage.length < 4)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("只能上传张图片4-5张图片");
			return resultInfo;
		}

		// 如果上传的资质许可证大于10m
		if ((licenseFile.getSize() / 1024 / 1024) > 10) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("资质许可证明不能大于10M");
			return resultInfo;
		}

		// 封装数据
		App app = new App();
		AppVersion appVersion = new AppVersion();
		appVersion.setActivated(3);  //安装包直接审核通过
		List<AppImage> appImages = new ArrayList<AppImage>();

		// 免费软件
		if (!tariffType.equals("2") && tariffType.equals("3")) {
//			if (StringUtils.isEmpty(downloadMoney)) {
//				resultInfo.setCode("-1");
//				resultInfo.setMessage("请填写下载所需的墨客币");
//				return resultInfo;
//			}
//			app.setDownloadMoney(Double.parseDouble(downloadMoney));
		}

		// 定时发布
		if (publishType.equals("2")) {
			if (StringUtils.isEmpty(publishDay)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("定时发布的第几天不能为空");
				return resultInfo;
			}

			if (StringUtils.isEmpty(publishTime)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("定时发布的时间不能为空");
				return resultInfo;
			}
			app.setPublishDay(publishDay);
			app.setPublishHour(publishTime);
		}

		// 存储文件

		// 1、软件安装包
		if (platform.equals("1")) {
			String packFileName = packageFile.getOriginalFilename(); // 安装包名称

			String packageUrl = null;
			try {
				//上传软件安装包
				packageUrl = UploadFileUtils.uploadFile("app/"+HanyuPinyinUtils.toHanyuPinyin(appName), packFileName,
						packageFile);   
				appVersion.setPackageName(packageName);  //设置安装包的名称
				appVersion.setDownloadUrl(ApplicationMarketConstants.UPLOAD_URL+"app/"+HanyuPinyinUtils.toHanyuPinyin(appName)+"/"+packFileName);  //设置下载安装包的相对路径
				Long b=packageFile.getSize();
				System.out.println(b);
				appVersion
						.setSize(String.valueOf(packageFile.getSize() / 1024 / 1024)
								+ "M");
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传软件安装包失败，请重新上传");
				return resultInfo;
			}
		}else {  //如果是IOS
			appVersion.setDownloadUrl(appUrl);  //设置下载链接
			appVersion.setSize(size);
		}

		// 2.资质许可证明上传

		String licenseFileName = System.currentTimeMillis()
				+ licenseFile.getOriginalFilename();// 文件名称
		String licenseUrl = null;

		try {
			licenseUrl = UploadFileUtils.uploadFile("app/"+HanyuPinyinUtils.toHanyuPinyin(appName), licenseFileName,
					licenseFile);
			app.setLicense("app/"+HanyuPinyinUtils.toHanyuPinyin(appName)+"/"+licenseFileName);  //设置资质许可证明的相对路径，用来下载
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("上传资质许可证明失败，请重新上传");
			return resultInfo;
		}

		// 3、上传应用图标
		String logoFileName = System.currentTimeMillis()
				+ logoFile.getOriginalFilename();
		String logoUrl = null;
		try {
			logoUrl = UploadFileUtils
					.uploadFile("app/"+HanyuPinyinUtils.toHanyuPinyin(appName), logoFileName, logoFile);
			app.setLogo(logoUrl);
			appVersion.setLogo(logoUrl);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("上传应用图标失败，请重新上传");
			return resultInfo;
		}

		// 4.上传截图
		for (MultipartFile imageFile : appImage) {
			String appImageFileName = System.currentTimeMillis()
					+ imageFile.getOriginalFilename();
			String imageUrl = null;
			try {
				imageUrl = UploadFileUtils.uploadFile("app/"+HanyuPinyinUtils.toHanyuPinyin(appName), appImageFileName,
						imageFile);
				AppImage image = new AppImage();
				image.setImageUrl(imageUrl);
				image.setCreateDate(new Date());
				appImages.add(image);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传应用截图失败失败，请重新上传");
				return resultInfo;
			}
		}

		// 审核辅助说明选填
		if (!StringUtils.isEmpty(examineExplain)) {
			app.setExamineExplain(examineExplain);
		}

		app.setCreateDate(new Date());
		appVersion.setCreateDate(new Date());
		app.setPlateform(Integer.parseInt(platform));
//		appVersion.setPackageName(packageName);
		appVersion.setVersionName(versionName);
		appVersion.setVersionNumber(versionNum);
		appVersion.setVersionFeatures(versionFeaturs);
		
		app.setTypeId(Integer.parseInt(appTypeId));
		app.setPersonalRecommend(personalRecommend);
		app.setSupportLanguage(Integer.parseInt(supportLanguage));
		app.setTariffType(Integer.parseInt(tariffType));
		app.setIntroduction(appIntroduction);
		appVersion.setIntroduction(versionIntroduction);
		appVersion.setSupportSys(supportSystem);
		app.setPrivacy(privacy);
		app.setExamineExplain(examineExplain);  //审核说明
		app.setPublishType(Integer.parseInt(publishType));
//		app.setUserId(user.getUserId());  //设置发布人的id
		app.setName(appName);
		app.setDevelopCompany(developCompany);  //开发商
		
		//设置用户账号，表示是用户上传的应用
		if (user!=null) {
			app.setUserId(user.getUserId());//设置发布人的id
		}
		
		// 调用service层的方法
		try {
			Object object = appService.addApp(app, appImages, appVersion);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加应用异常");
			return resultInfo;
		}
	}

	/**
	 * 分页获取数据  后台
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getAppList")
	public Object getAppList(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appId = request.getParameter("appId");
		String appName = request.getParameter("appName");
		String platform = request.getParameter("platform");
		String publishAccount = request.getParameter("publishAccount"); // 发布人的账号
		String examinStatus = request.getParameter("examinStatus");
		String groudStatus = request.getParameter("groundStatus"); // 上架的状态
		String isRecommend = request.getParameter("isRecommend"); // 是否推荐
		
		
		String isMust=request.getParameter("isMust");  //是否必备
		
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

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

		// 封装条件
		HashMap<String, Object> params = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(appId)) {
			params.put("appId", Integer.parseInt(appId));
		}

		if (!StringUtils.isEmpty(appName)) {
			params.put("appName", appName);

		}

		if (!StringUtils.isEmpty(platform)) {
			params.put("platform", Integer.parseInt(platform));

		}
		if (!StringUtils.isEmpty(publishAccount)) {
			params.put("publishAccount", publishAccount);
		}

		if (!StringUtils.isEmpty(examinStatus)) {
			if (!examinStatus.equals("4")) {
				params.put("examinStatus", Integer.parseInt(examinStatus));
			}
		}

		if (!StringUtils.isEmpty(groudStatus)) {
			if (!groudStatus.equals("3")) {
				params.put("groudStatus", Integer.parseInt(groudStatus));
			}
		}

		if (!StringUtils.isEmpty(isRecommend)) {
			if (!isRecommend.equals("3")) {
				params.put("isRecommend", Integer.parseInt(isRecommend));
			}
		}
		
		if (!StringUtils.isEmpty(isMust)) {
			params.put("isMust", Integer.parseInt(isMust));
		}

		PagingTool pagingTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		pagingTool.setParams(params);

		// 调用service层的方法
		try {
			Object object = appService.getAppList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("查询异常");
			return resultInfo;
		}

	}

	/**
	 * 审核通过
	 * 
	 * @param appId 应用Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/examinPass")
	public Object examinPass(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appId = request.getParameter("appId");
		// 校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}

		// 调用service层的方法

		try {
			Object object = appService.examinPass(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核异常");
			return resultInfo;
		}
	}

	/**
	 * 审核拒绝的方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/examinFail")
	public Object examinFail(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appId = request.getParameter("appId");
		// 校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}

		try {
			Object object = appService.examinFail(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}

	/**
	 * 下架
	 * 
	 * @param appId
	 *            应用Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/under")
	public Object under(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appId = request.getParameter("appId");
		// 校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}

		try {
			Object object = appService.under(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}

	/**
	 * 上架
	 * @param appId  应用Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/ground")
	public Object ground(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");
		
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		//调用servuce层的方法
		
		try {
			Object object=appService.ground(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("上架异常");
			return resultInfo;
		}
	}
	
	/**
	 * 推荐应用
	 * 
	 * @param appId
	 *            应用Id
	 * @param appTypeId
	 *            推荐分类Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/recommend")
	public Object recommend(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appId = request.getParameter("appId");
		String recommendTypeId = request.getParameter("recommendTypeId");

		// 校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(recommendTypeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用分类Id不能为空");
			return resultInfo;
		}

		// 封装数据
		App app = new App();
		app.setAppId(Integer.parseInt(appId));
		app.setRecommendTypeId(Integer.parseInt(recommendTypeId));

		// 调用service层的方法
		try {
			Object object = appService.recommend(app);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("推荐异常");
			return resultInfo;
		}
	}

	/**
	 * 根据应用名称获取应用列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/searchApp")
	public Object searchApp(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appName = request.getParameter("appName");
		String platform=request.getParameter("platform");  //平台  1 安卓 2 ios

		// 校验参数
		if (StringUtils.isEmpty(appName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用名称不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(platform)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("支持平台不能为空");
			return resultInfo;
		}

		// 调用service层的方法
		try {
			Object object = appService.searchApp(appName,Integer.parseInt(platform));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}

	/**
	 * 根据Id获取历史版本
	 * 
	 * @param appId
	 *            应用Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getHistory")
	public Object getHistory(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appId = request.getParameter("appId");

		// 参数校验
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}

		// 调用service层的方法

		try {
			Object object = appService.getHistory(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}

	/**
	 * 获取应用的权限
	 * 
	 * @param appId
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getPower")
	public Object getPower(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appId = request.getParameter("appId");
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}

		// 调用service的方法
		try {
			Object object = appService.getPower(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}

	}

	/**
	 * 获取应用详情,前台接口，包括评论等信息
	 * @param appId  应用Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getAppDetail")
	public Object getAppDetail(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String appId = request.getParameter("appId");

		// 校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}

		// 调用service层的方法
		try {
			Object object = appService.getAppDetail(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}

	
	/**
	 * 下载应用
	 * 目前下载应用不需要钱  
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/app/download")
	public Object download(HttpServletRequest request) throws Exception{
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");
		String userId=request.getParameter("userId");
		//调用service层的方法
		return appService.download(Integer.parseInt(userId),Integer.parseInt(appId));
	}
	
	
	/**
	 * 分页获取分类下的应用
	 * @param typeId  分类Id
	 * @param pageNum 当前页数
	 * @param pageSize  每页显示的数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getAppListByType")
	public Object getAppListByType(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String typeId=request.getParameter("typeId");  //分类Id
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize"); 
		String platform=request.getParameter("platform");  //平台
		
		//校验参数
		if(StringUtils.isEmpty(typeId)){
			resultInfo.setCode("-1");
			resultInfo.setMessage("分类Id不能为空");
			return resultInfo;
		}
		
		if(StringUtils.isEmpty(platform)){
			resultInfo.setCode("-1");
			resultInfo.setMessage("支持平台不能为空");
			return resultInfo;
		}
		
		if(StringUtils.isEmpty(pageNum)){
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前页数不能为空");
			return resultInfo;
		}
		
		if(StringUtils.isEmpty(pageSize)){
			resultInfo.setCode("-1");
			resultInfo.setMessage("每页显示的数量不能为空");
			return resultInfo;
		}
		
		//封装数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("typeId", Integer.parseInt(typeId));
		params.put("platform", Integer.parseInt(platform));
		pagingTool.setParams(params);
		
		//调用service层的方法
		
		try {
			Object object=appService.getAppListByType(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	/**
	 * 畅销榜的获取
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getHostSaleList")
	public Object getHotSaleList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		String platform=request.getParameter("platform");   //平台支持
		 
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
		
		//封装数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("platform", Integer.parseInt(platform));
		
		pagingTool.setParams(params);
		
		//调用service层的方法
		try {
			Object object=appService.getHotSaleList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}

	
	/**
	 * 获取飙升榜中的应用
	 * @param pageNum  当前的页数
	 * @param pageSize 每页显示的数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getSoaringList")
	public Object getSoaringList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		String platform=request.getParameter("platform");   //平台支持
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
		
		//封装数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("platform", Integer.parseInt(platform));
		pagingTool.setParams(params);
		
		//调用service层的方法
		try {
			Object object=appService.getSoaringList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 应用置顶
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/top")
	public Object top(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");
		//校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=appService.top(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		
	}
	
	
	/**
	 * 获取应用的详细信息，后台，包括版本，历史版本，截图
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getAppDetailInfo")
	public Object getAppDetailInfo(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");
		
		//校验参数
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		
		//调用service的方法
		try {
			Object object=appService.getAppDetailInfo(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
		
		
	}
	
	
	/**
	 * 更新安装包
	 * @param packageFile  安装包
	 * @param packageName  包名
	 * @param versionName  版本名称
	 * @param introduction  介绍
	 * @param supportSys  支持的系统
	 * @param privacy  权限
	 * @param logo  图标
	 * @param appId  应用Id
	 * @param url 应用下载链接
	 * @param versionNum  版本号
	 * @param size  安装包大小
	 * @return
	 * 
	 * 1、安装包不需要审核直接替换即可
	 */
	@RequestMapping("/app/updateApp")
	public Object updateApp(
			@RequestParam(value = "packageFile", required = false) MultipartFile packageFile,
			String versionName, String introduction, String supportSys,
			String privacy, MultipartFile logo, Integer appId,
			String versionNum, String versionFeatures, String url,
			String platform, String size,String packageName) {
		ResultInfo resultInfos=new ResultInfo();
		
		if (StringUtils.isEmpty(versionName)) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("版本名称不能为空");
			return resultInfos;
		}
		
		if (StringUtils.isEmpty(introduction)) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("当前版本介绍不能为空");
			return resultInfos;
		}
		if (StringUtils.isEmpty(supportSys)) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("支持系统不能为空");
			return resultInfos;
		}
		
		if (StringUtils.isEmpty(versionFeatures)) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("新版特性不能为空");
			return resultInfos;
		}
		
		if (StringUtils.isEmpty(privacy)) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("隐私权限说明不能为空");
			return resultInfos;
		}
		
		if (logo==null) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("应用图标不能为空");
			return resultInfos;
		}
		
		
		if (StringUtils.isEmpty(platform)) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("平台不能为空");
			return resultInfos;
		}
		
		
		if (!StringUtils.isEmpty(platform)) {
			if (platform.equals("1")) {  //安卓
				if (packageFile==null) {
					resultInfos.setCode("-1");
					resultInfos.setMessage("安装包不能为空");
					return resultInfos;
				}
				
				if (StringUtils.isEmpty(packageName)) {
					resultInfos.setCode("-1");
					resultInfos.setMessage("包名不能为空");
					return resultInfos;
				}
				
			}else {  //ios
				if (StringUtils.isEmpty(url)) {
					resultInfos.setCode("-1");
					resultInfos.setMessage("应用下载链接不能为空");
					return resultInfos;
				}
				
				if (StringUtils.isEmpty(size)) {
					resultInfos.setCode("-1");
					resultInfos.setMessage("安装包大小不能为空");
					return resultInfos;
				}
			}
		}
		
		
		
		
		//根据appId获取应用的信息
		App app=null;
		try {
			 app=appService.getAppById(appId);
		} catch (Exception e1) {
			e1.printStackTrace();
			resultInfos.setCode("-1");
			resultInfos.setMessage("获取应用信息异常");
			return resultInfos;
		}
		
		if (app==null) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("该应用不存在");
			return resultInfos;
		}
		
		
		AppVersion version=new AppVersion();  //用来封装数据
		//保存文件
		
		//更新安装包
		if (packageFile!=null) {
			//安装包
			String packageFileName=packageFile.getOriginalFilename();  //安装包的名称
			String packageUrl=null;
			try {
				packageUrl = UploadFileUtils.uploadFile("app/"+HanyuPinyinUtils.toHanyuPinyin(app.getName()),packageFileName,
						packageFile);
				
				version.setDownloadUrl(ApplicationMarketConstants.UPLOAD_URL+"app/"+HanyuPinyinUtils.toHanyuPinyin(app.getName())+"/"+packageFileName);  //设置安装包的路径，用来下载
				version.setPackageName(packageName); //设置安装包名称
				
				version
				.setSize(String.valueOf(packageFile.getSize() / 1024 / 1024)
						+ "M");   //设置大小
			} catch (Exception e) {
				resultInfos.setCode("-1");
				resultInfos.setMessage("上传安装包异常");
				return resultInfos;
			}
		}

		if (platform.equals("2")) {  //IOS
			version.setSize(size);  //安装包大小
			version.setDownloadUrl(url);   //下载链接
		}
		
		//上传应用图标
		String logoName=System.currentTimeMillis()+logo.getOriginalFilename();
		String logoUrl=null;
		try {
			logoUrl = UploadFileUtils.uploadFile("app/"+HanyuPinyinUtils.toHanyuPinyin(app.getName()),logoName,
					logo);
			version.setLogo(logoUrl);;  //图标
		} catch (Exception e) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("上传安装包异常");
			return resultInfos;
		}
		version.setAppId(appId);
		version.setCreateDate(new Date());
		version.setVersionName(versionName);
		version.setVersionNumber(versionNum);
		version.setIntroduction(introduction);
		version.setVersionFeatures(versionFeatures);
		//调用service层的方法
		try {
			Object object=appService.updateApp(version);
			return object;
		} catch (Exception e) {
			resultInfos.setCode("-1");
			resultInfos.setMessage("异常");
			return resultInfos;
		}
	}
	
	/**
	 * 根据应用名称进行模糊查找应用
	 * @param appName  应用名称
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/searchByAppName")
	public Object searchByAppName(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appName=request.getParameter("appName");
		
		String platform=request.getParameter("platform");
		
		//校验参数
		if (StringUtils.isEmpty(appName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用名称不能为空");
			return resultInfo;
		}
		
		Integer p=null;
		if (StringUtils.isEmpty(platform)) {
			p=null;
		}else {
			p=Integer.parseInt(platform);
		}
		
		try {
			Object object=appService.searchByAppName(appName,p);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 批量导入应用信息
	 * @param request
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/app/export")
	@ResponseBody
	public Object export(HttpServletRequest request,@RequestParam("file")MultipartFile file){
		ResultInfo resultInfo=new ResultInfo();
		String path = request.getSession().getServletContext().getRealPath("/");

		System.out.println("文件路径：" + path);
		String originalFilename = file.getOriginalFilename();
		String type = file.getContentType();

		System.out.println("目标文件名称：" + originalFilename + ",目标文件类型：" + type);
		
		File targetFile = new File(path, originalFilename);

		if (!originalFilename.endsWith(".xls")
				&& !originalFilename.endsWith(".xlsx")) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请上传Excel格式文件");
			return resultInfo;
		}
		
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		} else if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("上传文件失败");
			return resultInfo;
		}
		
		Workbook hssfWorkbook = null;
		
		try {
			if (originalFilename.endsWith(".xlsx")) {
				hssfWorkbook = new XSSFWorkbook(new FileInputStream(targetFile));
			} else if (originalFilename.endsWith(".xls")) {
				hssfWorkbook = new HSSFWorkbook(new FileInputStream(targetFile));
			} else {
				resultInfo.setMessage("请上传xls或者xlsx格式的文件！");
				resultInfo.setCode("-1");
				return resultInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			
			int allRowNum = hssfSheet.getLastRowNum();
			// 循环行Row

			// 数字补0处理
			for (int rowNum = 1; rowNum <= allRowNum; rowNum++) {
				Row hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					
					Cell platNameCell=hssfRow.getCell(0);
					platNameCell.setCellType(CellType.STRING);  //设置成字符串
					String platName=platNameCell.getStringCellValue();  //平台
					
					//软件的分类是数字，需要转换
					Cell appTypeCell=hssfRow.getCell(1);
					appTypeCell.setCellType(CellType.STRING);  //设置成字符串
					String appType=appTypeCell.getStringCellValue();  //软件分类
					
					String personal=hssfRow.getCell(2).getStringCellValue();  //个性推荐语
					
					String license=hssfRow.getCell(3).getStringCellValue();  //资质许可证明
					
					Cell supportLaCell=hssfRow.getCell(4);  //支持的语言
					supportLaCell.setCellType(CellType.STRING);
					String supportLa=supportLaCell.getStringCellValue();  //支持语言
					
					Cell tariffTypeCell=hssfRow.getCell(5);  //资费类型
					tariffTypeCell.setCellType(CellType.STRING);
					String tariffType=tariffTypeCell.getStringCellValue();  //资费类型
					
					String introduction=hssfRow.getCell(6).getStringCellValue();  //应用简介
					
					String versionintro=hssfRow.getCell(7).getStringCellValue();  //当前版本介绍
					
					String priacy=hssfRow.getCell(8).getStringCellValue();  //隐私权限说明
					
					String logo=hssfRow.getCell(9).getStringCellValue();  //应用图标
					
					String appImages=hssfRow.getCell(10).getStringCellValue();  //应用截图，多张用中文分号隔离
					
					String appName=hssfRow.getCell(11).getStringCellValue();  //应用名称
					
					String packageName=hssfRow.getCell(12).getStringCellValue();  //安装包的名称
					
					String versionName=hssfRow.getCell(13).getStringCellValue();  //版本名称
					
					Cell versionNumCell=hssfRow.getCell(14);  //版本号
					versionNumCell.setCellType(CellType.STRING);
					String versionNum=versionNumCell.getStringCellValue();  //版本号
					
					String versionFeatures=hssfRow.getCell(15).getStringCellValue();  //版本特性
					
					String size=hssfRow.getCell(16).getStringCellValue();  //安装包大小
					
					
					
					
//					String publishDate=hssfRow.getCell(15).getStringCellValue();  //发布时间
//					String publishType=hssfRow.getCell(15).getStringCellValue();  //发布的方式  1 审核后立即发布 2定时发布
					
					if (StringUtils.isEmpty(platName)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("平台不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(appType)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("软件分类不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(personal)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("个性推荐语不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(license)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("资质许可证明不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(supportLa)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("支持语言不能为空");
						return resultInfo;
					}
					
					
					if (StringUtils.isEmpty(tariffType)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("资费类型不能为空");
						return resultInfo;
					}
					
					
					if (StringUtils.isEmpty(introduction)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("应用简介不能为空");
						return resultInfo;
					}
					
					
					if (StringUtils.isEmpty(versionintro)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("当前版本介绍不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(priacy)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("隐私权限说明不能为空");
						return resultInfo;
					}
					
					
					if (StringUtils.isEmpty(logo)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("应用图标不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(appImages)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("应用图片不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(appName)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("应用名称不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(packageName)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("安装包不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(versionName)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("版本名称不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(versionNum)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("版本号不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(size)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("安装包大小不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(versionFeatures)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("版本特性不能为空");
						return resultInfo;
					}
					
					if (StringUtils.isEmpty(size)) {
						resultInfo.setCode("-1");
						resultInfo.setMessage("安装包大小不能为空");
						return resultInfo;
					}
					
					
					App app=new App();  //封装参数
					AppVersion version=new AppVersion();
					
					//封装数据
					app.setName(appName);
					app.setPlateform(Integer.parseInt(platName)); 
					app.setTypeId(Integer.parseInt(appType));  //分类Id
					app.setPersonalRecommend(personal);
					app.setLicense(license);  //资质许可证明
					app.setSupportLanguage(Integer.parseInt(supportLa));
					app.setTariffType(Integer.parseInt(tariffType));
					app.setIntroduction(introduction);
					app.setPrivacy(priacy);
					String logoUrl=ApplicationMarketConstants.UPLOAD_URL+"app/"+HanyuPinyinUtils.toHanyuPinyin(appName)+"/"+logo;  //图片的url
					app.setLogo(logoUrl);
					app.setName(appName);
					app.setCreateDate(new Date());
					app.setExamineStatus(1);  //审核通过
					app.setExaminDate(new Date());
					app.setExaminPersonName("批量导入");
					app.setGroundStatus(1);  //上架
					app.setPublishDate(new Date());  //立刻发布
					app.setGroundTime(new Date());   //上架时间 立刻上架
					app.setPublishType(1);  //立刻发布
					app.setUserId(1);
					
					version.setIntroduction(versionintro);
					version.setLogo(logoUrl);
					version.setPackageName(packageName);
					version.setVersionName(versionName);
					version.setVersionNumber(versionNum);
					version.setActivated(3); //审核通过
					version.setIsHistoryVersion(0);   //该版本为最新版本
					version.setSize(size);
					version.setCreateDate(new Date());
					version.setVersionFeatures(versionFeatures);  //版本特性
					version.setDownloadUrl(ApplicationMarketConstants.UPLOAD_URL+"app/"+HanyuPinyinUtils.toHanyuPinyin(appName)+"/"+packageName);
					
					String[] images=appImages.split("；");  //获取截图的数组
					
					//调用service的方法
					
					try {
						Object object=appService.export(app,version,images);
						if (((ResultInfo)object).getCode().equals("-1")) {
							continue;  //如果导入失败，继续导入下一条
						}
					} catch (Exception e) {
						continue;  //如果导入失败，继续导入下一条
					}
					
				}
			}
		}

		resultInfo.setMessage("导入成功");
		return resultInfo;
	}
	
	
	/**
	 * 后台获取审核的应用
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getExaminList")
	public Object getExaminList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		//可选参数
		String userId=request.getParameter("userId");  //用户Id
		String userName=request.getParameter("userName");  //用户名
		String realName=request.getParameter("realName"); //用户名称
		String provinceCode=request.getParameter("provinceCode"); //省份的编码
		String authDate=request.getParameter("authDate"); //认证请求时间
		String status=request.getParameter("status");  //审核状态  0 未审核 1 审核通过 2 审核失败
		
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
		
		if (!StringUtils.isEmpty(userId)) {
			params.put("userId", Integer.parseInt(userId));
		}
		
		if (!StringUtils.isEmpty(userName)) {
			params.put("userName", userName);
		}
		
		if (!StringUtils.isEmpty(realName)) {
			params.put("realName", realName);
		}
		
		if (!StringUtils.isEmpty(provinceCode)) {
			params.put("provinceCode", provinceCode);
		}
		
		if (!StringUtils.isEmpty(authDate)) {
			params.put("authDate", authDate);
		}
		
		if (!StringUtils.isEmpty(status)) {
			params.put("status", Integer.parseInt(status));
		}
		
		pagingTool.setParams(params);
		
		//调用service
		try {
			Object object=appService.getExaminList(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	
	
	/**
	 * 批量审核
	 * @param reqHeaders
	 * @return
	 */
	@RequestMapping("/app/exmain")
	public Object exmain(HttpServletRequest request){
		ResultInfo rs=new ResultInfo();
		String[] ids=request.getParameterValues("ids");
		String status=request.getParameter("status");
		
		if (ids==null||ids.length==0) {
			rs.setCode("-1");
			rs.setMessage("应用Id不能为空");
			return rs;
		}
		
		if (StringUtils.isEmpty(status)) {
			rs.setCode("-1");
			rs.setMessage("状态不能为空");
			return rs;
		}
		
		try {
			Object object=appService.exmain(ids,Integer.parseInt(status));
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode("-1");
			rs.setMessage("异常");
			return rs;
		}
	}
	

	/**
	 * 首页
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/home")
	public Object home(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String platform=request.getParameter("platform");
		
		if (StringUtils.isEmpty(platform)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("平台不能为空");
			return resultInfo;
		}
		
		//调用service
		try {
			Object object=appService.getHome(Integer.parseInt(platform));
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取必备应用
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getMustApp")
	public Object getMustApp(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		String platform=request.getParameter("platform");
		
		
		if (StringUtils.isEmpty(platform)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("支持的平台不能为空");
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
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("platform", Integer.parseInt(platform));
		
		pagingTool.setParams(params);
		
		try {
			Object object=appService.getMustApp(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	
	}
	
	
	/**
	 * 待升级应用列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/getUpgradeList")
	public Object getUpgradeList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");   //用户Id
		String pageNum=request.getParameter("pageNum");   
		String pageSize=request.getParameter("pageSize");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前显示的页数不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("每页显示的数量不能为空");
			return resultInfo;
		}
		
		//创建分页
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		//封装过滤条件
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", Integer.parseInt(userId));
		pagingTool.setParams(params);
		
		try {
			Object object=appService.getUpgradeList(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 应用升级
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/app/upgrade")
	public ResponseEntity<byte[]> upgrade(HttpServletRequest request) throws Exception{
		String userId=request.getParameter("userId");
		String appId=request.getParameter("appId");
		
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		
		if (StringUtils.isEmpty(appId)) {
			return null;
		}
		
		
		//调用service层的方法
		try {
			ResponseEntity<byte[]> responseEntity =appService.upgrade(Integer.parseInt(userId),Integer.parseInt(appId));
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;    //异常直接返回空
		}
		
	}
	
	
	/**
	 * 获取安装记录
	 */
	@RequestMapping("/app/getInstallRecord")
	public Object getInstallRecord(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
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
		
		//封装参数
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", Integer.parseInt(userId));
		pagingTool.setParams(params);
		//调用service层的方法
		try {
			Object object=appService.getInstallRecord(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 卸载应用
	 * 1、主要改变状态为卸载的状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/uninstall")
	public Object uninstall(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		String packName=request.getParameter("packageName"); //安装包的名称
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(packName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("安装包的名称不能为空");
			return resultInfo;
		}
		
		
		
		try {
			Object object=appService.uninstallApp(Integer.parseInt(userId),packName);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	 
	
	
	/**
	 * 设为必备和取消必备
	 * @param appId  应用Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/setMust")
	public Object setMust(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");
		String status=request.getParameter("status");
		
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("状态不能为空");
			return resultInfo;
		}
		
		App app=new App();
		app.setAppId(Integer.parseInt(appId));
		app.setIsMust(Integer.parseInt(status));  //设置为必备
		
		try {
			Object object=appService.modifyApp(app);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	
	
	
	/**
	 * 批量删除
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/deleteAppBatch")
	public Object deleteAppById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String[] appIds=request.getParameterValues("appIds");
		
		if (appIds==null||appIds.length==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		//调用service
		try {
			Object object=appService.deleteAppBatch(appIds);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改应用信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/modifyAppById")
	public Object modifyAppById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId");  //应用Id
		String appName=request.getParameter("appName");  //应用名称
		String downloadUrl=request.getParameter("downloadUrl"); //下载链接
		String packageName=request.getParameter("packageName");  //包名
		String versionName=request.getParameter("versionName");  //版本名称
		String versionNum=request.getParameter("versionNum"); //版本号码
		String type=request.getParameter("type"); //软件分类
		String personalRecommend=request.getParameter("personalRecommend"); //个性他推荐语
		String supportLanguage=request.getParameter("supportLanguage"); //支持语言
		String tariffType=request.getParameter("tariffType"); //资费类型
		String introduction=request.getParameter("introduction"); //简介
		String versionIntro=request.getParameter("versionIntro"); //当前版本介绍
		String versionFeatures=request.getParameter("versionFeatures"); //新版特性
		String supportSys=request.getParameter("supportSys"); //支持系统
		String privacy=request.getParameter("privacy"); //隐私权限
		
		
		//校验
		
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		App app;
		try {
			app = appService.getAppById(Integer.parseInt(appId));
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取应用信息 异常");
			return resultInfo;
		}
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		AppVersion version=new AppVersion();
		

		
		
		//应用存在，设置信息
		if (!StringUtils.isEmpty(appName)) {
			app.setName(appName);
		}
		
		if (!StringUtils.isEmpty(downloadUrl)) {
			version.setDownloadUrl(downloadUrl);
		}
		
		if (!StringUtils.isEmpty(packageName)) {
			version.setPackageName(packageName);
		}
		
		if (!StringUtils.isEmpty(versionName)) {
			version.setVersionName(versionName);
		}
		
		if (!StringUtils.isEmpty(versionNum)) {
			version.setVersionNumber(versionNum);
		}
		
		
		if (!StringUtils.isEmpty(type)) {
			app.setTypeId(Integer.parseInt(type));
		}
		
		if (!StringUtils.isEmpty(personalRecommend)) {
			app.setPersonalRecommend(personalRecommend);
		}
		
		if (!StringUtils.isEmpty(supportLanguage)) {
			app.setSupportLanguage(Integer.parseInt(supportLanguage));
		}
		
		
		if (!StringUtils.isEmpty(tariffType)) {
			app.setTariffType(Integer.parseInt(tariffType));
		}
		
		if (!StringUtils.isEmpty(introduction)) {
			app.setIntroduction(introduction);
		}
		
		if (!StringUtils.isEmpty(versionIntro)) {
			version.setIntroduction(versionIntro);
		}
		
		if (!StringUtils.isEmpty(versionFeatures)) {
			version.setVersionFeatures(versionFeatures);
		}
		
		if (!StringUtils.isEmpty(supportSys)) {
			version.setSupportSys(supportSys);
		}
		
		if (!StringUtils.isEmpty(privacy)) {
			app.setPrivacy(privacy);
		}
		
		
		//调用service的方法
		try {
			Object object=appService.modifyAppInfo(app,version);  
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}

	
	/**
	 * 取消推荐
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/cancelRecommend")
	public Object cancelRecommend(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String appId=request.getParameter("appId"); 
		
		if (StringUtils.isEmpty(appId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("应用Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=appService.cancelRecommend(Integer.parseInt(appId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
