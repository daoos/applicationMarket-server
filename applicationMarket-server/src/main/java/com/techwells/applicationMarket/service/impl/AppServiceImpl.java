package com.techwells.applicationMarket.service.impl;

import java.io.File;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;

import jnr.ffi.Struct.int16_t;
import jnr.ffi.Struct.pid_t;

import org.apache.commons.io.FileUtils;
import org.apache.http.auth.NTCredentials;
import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AppCommentMapper;
import com.techwells.applicationMarket.dao.AppImageMapper;
import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.AppTypeMapper;
import com.techwells.applicationMarket.dao.AppVersionMapper;
import com.techwells.applicationMarket.dao.BannerMapper;
import com.techwells.applicationMarket.dao.RecommendTypeMapper;
import com.techwells.applicationMarket.dao.TaskMapper;
import com.techwells.applicationMarket.dao.UserAppMapper;
import com.techwells.applicationMarket.domain.App;
import com.techwells.applicationMarket.domain.AppComment;
import com.techwells.applicationMarket.domain.AppImage;
import com.techwells.applicationMarket.domain.AppType;
import com.techwells.applicationMarket.domain.AppVersion;
import com.techwells.applicationMarket.domain.Banner;
import com.techwells.applicationMarket.domain.RecommendType;
import com.techwells.applicationMarket.domain.Task;
import com.techwells.applicationMarket.domain.UserApp;
import com.techwells.applicationMarket.domain.UserAppKey;
import com.techwells.applicationMarket.domain.rs.AppAdminVos;
import com.techwells.applicationMarket.domain.rs.AppAndVersionVos;
import com.techwells.applicationMarket.domain.rs.AppUserVos;
import com.techwells.applicationMarket.domain.rs.AppVersionImageVos;
import com.techwells.applicationMarket.domain.rs.AppVersionImagesVos;
import com.techwells.applicationMarket.domain.rs.ScorePercent;
import com.techwells.applicationMarket.domain.rs.TaskAppVersionVos;
import com.techwells.applicationMarket.domain.rs.UserCommentVos;
import com.techwells.applicationMarket.service.AppService;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class AppServiceImpl implements AppService{

	@Resource
	private AppMapper appMapper;
	
	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Resource
	private AppImageMapper appImageMapper;
	
	@Resource
	private AppTypeMapper appTypeMapper;
	
	@Resource
	private RecommendTypeMapper recommendTypeMapper;
	
	@Resource
	private AppCommentMapper appCommentMapper;
	
	@Resource
	private BannerMapper bannerMapper;
	@Resource
	private TaskMapper taskMapper;
	
	@Resource
	private UserAppMapper userAppMapper;
	
	
	@Override
 	public Object addApp(App app, List<AppImage> images, AppVersion appVersion)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//添加应用app
		int count1=appMapper.insertSelective(app);
		
		if (count1==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		
		//上传版本
		appVersion.setAppId(app.getAppId());
		int count2=appVersionMapper.insertSelective(appVersion);
		
		if (count2==0) {
			throw new RuntimeException();  //抛出异常，回滚
		}
		
		
		//设置图片的Id
		for (AppImage appImage : images) {
			appImage.setAppId(app.getAppId());
			int count=appImageMapper.insertSelective(appImage);
			if (count==0) {
				throw new RuntimeException();
			}
		}
		
		//对应分类的数量+1
		AppType appType=appTypeMapper.selectByPrimaryKey(app.getTypeId());  //获取该分类的Id
		//该分类不存在，那么直接回滚数据
		if (appType==null) {
			throw new RuntimeException();
		}
		
		//总数+1
		appType.setAppCount(appType.getAppCount()+1);
		
		int count=appTypeMapper.updateByPrimaryKeySelective(appType);
		
		//如果修改失败，直接回滚数据
		if (count==0) {
			throw new RuntimeException();
		}
		
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@Override
	public Object getAppList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=appMapper.countTotal(pagingTool);
		List<AppAdminVos> appAdminVos=appMapper.selectAppAdminList(pagingTool);
		resultInfo.setTotal(count);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(appAdminVos);
		return resultInfo;
	}


	/**
	 * 上架
	 * 1、判断当前应用是否审核通过了
	 * 2、是否到了定时发布的时间
	 * 3、
	 */
	@Override
	public Object ground(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//根据Id获取应用信息
		App app=appMapper.selectByPrimaryKey(appId);
		
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		//如果应用存在，需要判断是否审核通过了
		if (!app.getExamineStatus().equals(1)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用未审核通过或者审核失败");
			return resultInfo;
		}
		
		//审核通过之后，需要判断设置了定时发布的时间，并且还未到发布时间，那么不能上架
		if (app.getPublishType()!=null&&app.getPublishDate()!=null&&app.getPublishType().equals(2)&&app.getPublishDate().getTime()>new Date().getTime()) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用设置了定时发布，不能提前上架");
			return resultInfo;
		}
		
		//现在可以设置上架
		
		app.setGroundStatus(1); //设置上架的状态
		app.setPublishDate(new Date());  //设置上架的时间
		int count=appMapper.updateByPrimaryKey(app);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("上架失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("上架成功");
		return resultInfo;
	}
	
	

	/**
	 * 一旦下架了，那么上架时间应该清空，点击上架之后会自动指定上架时间
	 */
	@Override
	public Object under(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//下架
		App app=appMapper.selectByPrimaryKey(appId);
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		if (!app.getGroundStatus().equals(1)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用没有上架");
			return resultInfo;
		}
		
		//修改状态
		app.setGroundStatus(2);
		app.setPublishDate(null);  //清空发布时间
		
		int count=appMapper.updateByPrimaryKey(app);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("下架失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("下架成功");
		return resultInfo;
		
	}

	//审核失败
	@Override
	public Object examinFail(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		App app=appMapper.selectByPrimaryKey(appId);
		
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		//修改状态
		
		app.setExamineStatus(3);
		app.setExaminDate(new Date());
		
		int count=appMapper.updateByPrimaryKeySelective(app);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("成功");
		return resultInfo;
		
	}

	
	//判断当前软件是否已经被推荐了
	//修改状态
	@Override
	public Object recommend(App app) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		App app2=appMapper.selectByPrimaryKey(app.getAppId());
		
		if (app2==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		//判断该软件是否已经上架了，只有上架的应用才能被推荐
		if (!app2.getGroundStatus().equals(1)) {
			resultInfo.setMessage("该软件还没有上架，不能推荐");
			resultInfo.setCode("-1");
			return resultInfo;
		}
		
		if (app2.getIsRecommend().equals(1)) {
			resultInfo.setMessage("该软件已经被推荐了");
			resultInfo.setCode("-1");
			return resultInfo;
		}
		
		//修改推荐状态为是
		app.setIsRecommend(1);
		int count=appMapper.updateByPrimaryKeySelective(app);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("推荐失败");
			return resultInfo;
		}
		
		//修改推荐分类的信息
		RecommendType recommendType=recommendTypeMapper.selectByPrimaryKey(app.getRecommendTypeId());
		//如果这个分类的信息不存在，那么会滚数据
		if (recommendType==null) {
			throw new RuntimeException();
		}
		
		recommendType.setCount(recommendType.getCount()+1);
		
		int count1=recommendTypeMapper.updateByPrimaryKeySelective(recommendType);
		
		if (count1==0) {
			throw new RuntimeException();  //回滚数据
		}
		
		resultInfo.setMessage("推荐成功");
		return resultInfo;
	}

	@Override
	public Object searchApp(String appName,Integer platform) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<AppAndVersionVos> appAndVersionVos=appMapper.selectAppAndVersionVosList(appName,platform);
		resultInfo.setResult(appAndVersionVos);
		resultInfo.setTotal(appAndVersionVos.size());
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	@Override
	public Object getHistory(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		List<AppAndVersionVos> appVersionVos=appMapper.selectHistoryList(appId);
		
		resultInfo.setTotal(appVersionVos.size());
		
		resultInfo.setResult(appVersionVos);
		
		resultInfo.setMessage("获取成功");
		
		return resultInfo;
	}

	@Override
	public Object getPower(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		AppAndVersionVos appAndVersionVos=appMapper.selectAppAndVersionVo(appId);
		
		if (appAndVersionVos==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取失败");
			return resultInfo;
		}
		
		//权限
		//去掉空格和换行符，并且根据中文分号分割字符串
		String[] powers=appAndVersionVos.getPower().replaceAll("\r\n|\r|\n|\n\r", "").split("；");
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("power", powers);
		map.put("supportSys",appAndVersionVos.getSupportSys());
		
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(map);
		return resultInfo;
	}
	
	/**
	 * 1、能够调用这个接口，说明已经是上架的，因此这里不需要判断是否上架
	 * 2、分类新版本的特性
	 * 3、获取平均得分
	 * 4、获取评分的百分比
	 * 5、获取推荐应用
	 */
	@Override
	public Object getAppDetail(int appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//获取应用的详细信息
		AppVersionImageVos appVersionImageVos=appMapper.selectAppVersionImageVos(appId,null);
		
		if (appVersionImageVos==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		//分离新版本的特性，判断是否为空，因为如果没有更新新版本，那么没有新版本特性
		if (appVersionImageVos.getFeatures()!=null) {
			//处理换行符并且使用中文分号将其分离
			String[] versionFeatures=appVersionImageVos.getFeatures().replaceAll("\r\n|\r|\n|\n\r", "").split("；");
			appVersionImageVos.setVersionFeatures(versionFeatures);  //存储
		}
		
		//设置主图
		appVersionImageVos.setPrimaryImage(appVersionImageVos.getAppImages().get(0).getImageUrl());
		
		//获取推荐应用
		PagingTool pagingTool=new PagingTool(1,4);  //获取首页第一个推荐列表的前四个
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("sort", 1);   //首页推荐的第一个推荐分类
		params.put("platform",appVersionImageVos.getPlatform());   //设置平台信息
		pagingTool.setParams(params);
		List<AppAndVersionVos> appAndVersionVos=recommendTypeMapper.selectAppList(pagingTool);
		
		
		//获取平均分数
		params.remove("sort");  //移除上面的sort
		params.put("appId", appId);
		pagingTool.setParams(params);  //设置appId
		Double avgScore=appCommentMapper.selectAvgScore(pagingTool);
		if (avgScore==null) {
			avgScore=(double) 0;
		}
		
		//获取分数等级的百分比
		List<ScorePercent> scorePercents=appCommentMapper.selectScorePercents(pagingTool);
		
		//填充为0的百分比
		for (int i = 1; i <=5; i++) {
			int count=0;
			for (ScorePercent scorePercent : scorePercents) {
				//如果有相等的，那么加1即可
				if (scorePercent.getScore().equals(i)) {
					count++;
					break;  //跳出这个循环
				}
			}
			
			//如果count任然等于0，那么可以肯定这个百分比不存在
			if (count==0) {
				ScorePercent scorePercent=new ScorePercent();
				scorePercent.setScore(i);
				scorePercent.setPercent(0d);
				scorePercents.add(scorePercent);
			}
		}
		
		
		PagingTool pagingTool2=new PagingTool(1,4);  //只获取一个
		pagingTool2.setParams(params);
		//获取一个评论
		List<UserCommentVos> userCommentVos=appCommentMapper.selectUserCommentVos(pagingTool2);
		
		
		//封装结果集
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appInfo", appVersionImageVos);
		map.put("avgScore", avgScore);
		map.put("scorePercent", scorePercents);
		map.put("recommendApps", appAndVersionVos);
		map.put("comment", userCommentVos);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(map);
		return resultInfo;
	}
	
	/**
	 * 1、保证获取的是已经上架的并且到了发布时间
	 * 2、根据创建时间倒序返回
	 */
	@Override
	public Object getAppListByType(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=appMapper.countTotalByType(pagingTool);
		List<AppAndVersionVos> andVersionVos=appMapper.selectAppAndVersionVos(pagingTool);
		resultInfo.setTotal(total);
		resultInfo.setResult(andVersionVos);
//		resultInfo.setCode("-1");
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	/**
	 * 1、上架的，到了发布时间的
	 * 2、根据下载次数降序
	 */
	@Override
	public Object getHotSaleList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//获取总数
		int total=appMapper.countHotSalTotal(pagingTool);
		
		//没有数据,直接返回，不需要查询数据库了
		if (total==0) {
			resultInfo.setResult(new ArrayList<AppAndVersionVos>());
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<AppAndVersionVos> appAndVersionVos=appMapper.selectHotSaleList(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(appAndVersionVos);
		resultInfo.setTotal(total);
		return resultInfo;
	}
	
	/*
	 * 1、上架的，并且到了发布时间的
	 * 2、24小时之内增加的下载次数降序排列，并且排除没有增加的应用 即是 download_count_add !=0
	 * 3、因此需要每24小时清空数据库中的download_count_add这个字段，重新计算
	 */
	@Override
	public Object getSoaringList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=appMapper.countSoaringTotal(pagingTool);
		
		//如果没有数据
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(new ArrayList<AppAndVersionVos>());
			resultInfo.setTotal(total);
		}
		
		List<AppAndVersionVos> appAndVersionVos=appMapper.selectSoaringList(pagingTool);
		
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(appAndVersionVos);
		resultInfo.setTotal(total);
		return resultInfo;
	}
	
	@Override
	public Object examinPass(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//修改状态
		App app=appMapper.selectByPrimaryKey(appId);
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		//判断是否是设置了定时发布
		if (app.getPublishType().equals(1)) {   //立即发布
			app.setPublishDate(new Date());  //立即发布
			app.setGroundStatus(1);  //设置上架
			app.setGroundTime(new Date());   //设置上架日期
		}else {   //定时发布
			String day=app.getPublishDay();  //天数
			String hour=app.getPublishHour();  //小时
			//转换时间
			Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH,Integer.parseInt(app.getPublishDay()));   //加上天数
			calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
			app.setPublishDate(calendar.getTime());   //设置定时发布的日期
			app.setGroundStatus(2);  //上架
			app.setGroundTime(calendar.getTime());   //设置上架时间
		}
		
		//修改审核的状态
		app.setExamineStatus(1);
		app.setExaminDate(new Date());
		
		int count=appMapper.updateByPrimaryKeySelective(app);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核通过失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("审核成功");
		return resultInfo;
	}

	@Override
	public Object top(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		App app=new App();
		app.setAppId(appId);
		app.setTop(1);
		app.setTopTime(new Date());
		
		//更新数据
		int count=appMapper.updateByPrimaryKeySelective(app);
		
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("置顶失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("置顶成功");
		return resultInfo;
	}

	@Override
	public Object getAppDetailInfo(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//获取应用详情
		App app=appMapper.selectByPrimaryKey(appId);
		
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		//获取最新的安装包
		AppVersion lastVersion=appVersionMapper.selectLastVersion(appId);
		
		//获取历史安装包
		List<AppVersion> histoVersions=appVersionMapper.selectHistVersionList(appId);
		
		//获取截图
		List<AppImage> appImages=appImageMapper.selectImages(appId);
		
		//获取软件分类信息
		AppType appType=appTypeMapper.selectByPrimaryKey(app.getTypeId());
		
		
		//封装
		AppVersionImagesVos appVersionImagesVos=new AppVersionImagesVos();
		appVersionImagesVos.setApp(app);
		appVersionImagesVos.setAppVersion(lastVersion);
		appVersionImagesVos.setHistoryVersions(histoVersions);
		appVersionImagesVos.setImages(appImages);
		appVersionImagesVos.setAppType(appType);
		resultInfo.setResult(appVersionImagesVos);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	//更新安装包，需要修改之前的安装包为历史版本
	@Override
	public Object updateApp(AppVersion version) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		
		//修改之前的版本是历史版本
		int count1=appVersionMapper.changeHistoryVersion(version.getAppId());
		
		if (count1==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("更换历史版本失败");
			return resultInfo;
		}
		
		
		//插入数据
		int count=appVersionMapper.insertSelective(version);
		
		if (count==0) {
			throw new RuntimeException();
		}
		resultInfo.setMessage("更新成功");
		return resultInfo;
	}

	@Override
	public Object searchByAppName(String appName,Integer platform) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<App> apps=appMapper.selectAppByAppName(appName,platform);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(apps);
		return resultInfo;
	}

	@Override
	public Object export(App app, AppVersion version, String[] images)
			throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//添加app
		int count1=appMapper.insertSelective(app);
		
		if (count1==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("导入失败");
			return resultInfo;
		}
		
		version.setAppId(app.getAppId());  //设置应用Id
		//导入version
		int count2=appVersionMapper.insertSelective(version);
		//插入失败直接抛异常回滚数据
		if (count2==0) {
			throw new RuntimeException();
		}
		
		//导入截图信息
//		List<AppImage> appImageList=new ArrayList<AppImage>();
		for (String image : images) {
			AppImage appImage=new AppImage();
			String imageUrl=ApplicationMarketConstants.UPLOAD_URL+"app/"+app.getName()+"/"+image;  //图片地址
			appImage.setImageUrl(imageUrl);
			appImage.setAppId(app.getAppId());  //设置appId
			int count3=appImageMapper.insertSelective(appImage);  //插入图片信息
			if (count3==0) {
				throw new RuntimeException();
			}
		}
		
		resultInfo.setMessage("导入成功");
		return resultInfo;
	}

	@Override
	public App getAppById(Integer appId) throws Exception {
		return appMapper.selectByPrimaryKey(appId);
	}
	
	@Override
	public Object getExaminList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=appMapper.countTotalAppUserVos(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<AppUserVos> appUserVos=appMapper.selectAppUserVos(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(appUserVos);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	@Override
	public Object exmain(String[] ids, Integer status) throws Exception {
		ResultInfo rs=new ResultInfo();
		
		// 审核通过，需要考虑到定时发布
		if (status.equals(1)) {
			// 便利Id，只要有一个审核出现异常，那么就需要回滚全部数据
			for (String id : ids) {
				Integer appId = Integer.parseInt(id);
				// 修改状态
				App app = appMapper.selectByPrimaryKey(appId);
				// 忽略不存在的应用，直接修改即可
				if (app != null) {
					// 判断是否是设置了定时发布
					if (app.getPublishType().equals(1)) { // 立即发布
						app.setPublishDate(new Date()); // 立即发布
						app.setGroundStatus(1); // 设置上架
						app.setGroundTime(new Date()); // 设置上架日期
					} else { // 定时发布
						String day = app.getPublishDay(); // 天数
						String hour = app.getPublishHour(); // 小时
						// 转换时间
						Calendar calendar = Calendar.getInstance();
						calendar.add(Calendar.DAY_OF_MONTH,
								Integer.parseInt(app.getPublishDay())); // 加上天数
						calendar.add(Calendar.HOUR_OF_DAY,
								Integer.parseInt(hour));
						app.setPublishDate(calendar.getTime()); // 设置定时发布的日期
						app.setGroundStatus(2); // 未上架
						app.setGroundTime(calendar.getTime()); // 设置上架时间
					}

					// 修改审核的状态
					app.setExamineStatus(1);
					app.setExaminDate(new Date());

					int count = appMapper.updateByPrimaryKeySelective(app);

					if (count == 0) {
						throw new RuntimeException(); // 审核失败，直接抛出异常即可
					}
				}
			}
		} else if (status.equals(3)) {// 审核不通过
			//遍历
			for (String id : ids) {
				//审核失败，直接修改状态为3即可
				App app=new App();
				app.setAppId(Integer.parseInt(id));
				app.setExamineStatus(3);
				app.setExaminDate(new Date());
				int count=appMapper.updateByPrimaryKeySelective(app);
				if (count==0) {
					throw new RuntimeException();
				}
			}
		}
		
		rs.setMessage("审核成功");
		return rs;
	}

	@Override
	public Object getHome(Integer platform) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//获取首页的广告，生效的，按照播放顺序返回
		List<Banner> banners=bannerMapper.selectBanners(platform);  
		
		/**
		 * 获取推荐分类
		 * 	1、已经上架的
		 */
		
		//获取最高等级的的推荐
		RecommendType recommendType=recommendTypeMapper.selectRecommendTypeBySort(1); 
		List<AppAndVersionVos> appAndVersionVos=null;  //推荐分类下的所有应用,只需要四个
		if (recommendType!=null) {
			appAndVersionVos=recommendTypeMapper.selectAppVersionVoList(recommendType.getRecommendTypeId(),platform,4);
		}
		
		//获取必备应用
//		List<AppAndVersionVos> mustApp=appMapper.selectMustApp();
		
		//获取任务弹窗，最高等级的弹窗的一个任务，到了时间，生效的任务
		Task task=taskMapper.selectHighLeve();
		TaskAppVersionVos taskApp=null;
		if (task!=null) {
			//如果是下载安装类，需要联合查询应用信息
			if (task.getTaskTypeId().equals(1)) {
				//查询应用的信息
				AppVersionImageVos appVersion=appMapper.selectAppVersionImageVos(task.getAppId(),platform);
				if (appVersion!=null) {
					taskApp=new TaskAppVersionVos();
					taskApp.setAppId(task.getAppId());  //应用Id
					taskApp.setAppName(appVersion.getAppName());  //应用名称
					taskApp.setLogo(appVersion.getLogo());  //图标 
					taskApp.setVersionNum(appVersion.getVersionNum()); //版本号
					taskApp.setSize(appVersion.getSize());   //应用大小
					taskApp.setIntroduction(task.getIntroduction());//简介
					taskApp.setRewardMoney(task.getRewardMoney());  //奖励的钱
					taskApp.setActivated(task.getActivated());   //奖励的类型
					taskApp.setTaskTypeId(task.getTaskTypeId());  //任务类型的Id
					taskApp.setTaskName(task.getTaskName());
					taskApp.setTaskId(task.getTaskId());
				}
			}else {   //如果是其他类型的，直接返回信息即可
				taskApp=new TaskAppVersionVos();
				taskApp.setIntroduction(task.getIntroduction());//简介
				taskApp.setRewardMoney(task.getRewardMoney());  //奖励的钱
				taskApp.setActivated(task.getActivated());   //奖励的类型
				taskApp.setLink(task.getLink());  //链接
				taskApp.setTaskTypeId(task.getTaskTypeId());  //任务类型的Id
				taskApp.setTaskName(task.getTaskName());
				taskApp.setTaskId(task.getTaskId());
			}
		}
		
		
		//封装数据
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("banners", banners);
		map.put("recommendApp", appAndVersionVos);
		map.put("task", taskApp);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(map);
		return resultInfo;
	}

	@Override
	public Object getMustApp(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int total=appMapper.countTotalMustApp(pagingTool);
		
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setTotal(total);
			resultInfo.setResult(null);
			return resultInfo;
		}
		
		List<AppAndVersionVos> mustApp=appMapper.selectMustApp(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setTotal(total);
		resultInfo.setResult(mustApp);
		
		return resultInfo;
	}

	
	
	/**
	 * 下载应用
	 * 1、查询下载的记录，同一个应用一个用户只能对应一条下载记录
	 * 2、app的下载次数增加一个
	 * 3、
	 */
	@Override
	public Object download(Integer userId, Integer appId)throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//如果App不存在，那么直接返回null
		App app=appMapper.selectByPrimaryKey(appId);
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		//根据appId获取最新安装包的下载路径
		AppVersion version=appVersionMapper.selectLastVersion(appId);
		
		//如果安装包信息为空，那么直接返回空即可
		if (version==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("安装包不存在");
			return resultInfo;
		}
		
		UserAppKey userAppKey=new UserAppKey();
		userAppKey.setAppId(appId);
		userAppKey.setUserId(userId);
		UserApp userApp=userAppMapper.selectByPrimaryKey(userAppKey);
		//如果不存在，那么就插入
		if (userApp==null) {
			userApp=new UserApp();
			userApp.setUserId(userId);
			userApp.setAppId(appId);
			userApp.setCreateDate(new Date());
			userApp.setVersionId(version.getAppVersionId());  //记录版本Id，用于升级
			int count=userAppMapper.insertSelective(userApp);
			//更新失败直接返回null
			if (count==0) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("下载失败");
				return resultInfo;
			}
		}else {   //如果存在，但是又点击了下载，那么肯定是卸载之后再安装的，此时就需要修改卸载的状态
			userApp.setIsUninstall(0);  //未卸载
			int count=userAppMapper.updateByPrimaryKeySelective(userApp);
			if (count==0) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("下载失败");
				return resultInfo;
			}
		}
		
		app.setDownloadCount(app.getDownloadCount()+1);  //下载次数+1
		app.setDownloadCountAdd(app.getDownloadCountAdd()+1);  //24小时增加的记录+1,用于飙升榜的统计
		int count=appMapper.updateByPrimaryKeySelective(app);   //更新记录
		if (count==0) {
			throw new RuntimeException();  //回滚数据
		}
		
		resultInfo.setMessage("下载成功");
		return resultInfo;
	}

	
	/**
	 * 根据传递过来的userId，查询是否用户的下载列表的应用是否有了更新
	 */
	@Override
	public Object getUpgradeList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//获取总数
		int total=appMapper.countTotalUpgrade(pagingTool);
		
		if (total==0) {
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			resultInfo.setMessage("获取成功");
			return resultInfo;
		}
		
		List<AppAndVersionVos> appAndVersionVos=appMapper.selectUpgradeList(pagingTool);
		
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(appAndVersionVos);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	
	/**
	 * 应用升级
	 * 1、更新下载的版本信息
	 */
	@Override
	public ResponseEntity<byte[]> upgrade(Integer userId, Integer appId)
			throws Exception {
		//如果App不存在，那么直接返回null
		App app = appMapper.selectByPrimaryKey(appId);
		if (app == null) {
			return null;
		}
		// 根据appId获取最新安装包的下载路径
		AppVersion version = appVersionMapper.selectLastVersion(appId);

		// 如果安装包信息为空，那么直接返回空即可
		if (version == null) {
			return null;
		}
		
		UserApp userApp=new UserApp();
		userApp.setUserId(userId);
		userApp.setAppId(appId);
		userApp.setVersionId(version.getAppVersionId());  //设置最新的安装包id
		int count=userAppMapper.updateByPrimaryKeySelective(userApp);
		//更新失败返回空即可
		if (count==0) {
			return null;
		}
		
		String downloadUrl=ApplicationMarketConstants.UPLOAD_URL+version.getDownloadUrl();
		//app/null/15387240969241533698707125.jpg
		String fileName = version.getDownloadUrl().split("/")[2];  //安装包的名称
		// 下载文件的全路径
		File file = new File(downloadUrl);
		HttpHeaders headers = new HttpHeaders();
		// 下载显示的文件名，解决中文名称乱码问题
		String downloadFielName = new String(fileName.getBytes("UTF-8"),
				"iso-8859-1");
		// 通知浏览器以attachment（下载方式）
		headers.setContentDispositionFormData("attachment", downloadFielName);
		// application/octet-stream ： 二进制流数据（最常见的文件下载）。
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.CREATED);
	}

	
	/**
	 * 需要返回是否安装的记录，如果安装了就显示卸载，未安装显示安装
	 */
	@Override
	public Object getInstallRecord(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int total=appMapper.countTotalInstallRecord(pagingTool);
		if (total==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(null);
			resultInfo.setTotal(total);
			return resultInfo;
		}
		
		List<AppAndVersionVos> appAndVersionVos=appMapper.selectInstallRecord(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(appAndVersionVos);
		resultInfo.setTotal(total);
		return resultInfo;
	}

	
	
	@Override
	public Object modifyUserApp(UserApp userApp) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int count=userAppMapper.updateByPrimaryKeySelective(userApp);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("成功");
		return resultInfo;
	}

	
	
	/**
	 * 根据安装包的名称查询应用Id
	 * 修改记录中的状态为已卸载
	 */
	@Override
	public Object uninstallApp(Integer userId, String packName) {
		ResultInfo resultInfo=new ResultInfo();
		
		//查询应用Id
		AppVersion version=appVersionMapper.selectAppVersionByPackName(packName);
		
		if (version==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该安装包不存在,暂时不能卸载");
			return resultInfo;
		}
		
		UserApp userApp=new UserApp();
		userApp.setUserId(userId);
		userApp.setAppId(version.getAppId());
		userApp.setIsUninstall(1);  //设置已经卸载了
		
		int count=userAppMapper.updateByPrimaryKeySelective(userApp);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("卸载失败");
			return resultInfo;
		}
		resultInfo.setMessage("卸载成功");
		return resultInfo;
	}

	@Override
	public Object modifyApp(App app) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=appMapper.updateByPrimaryKeySelective(app);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public Object deleteAppBatch(String[] appIds) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		int count=appMapper.deleteAppBatch(appIds);
		if (count!=appIds.length) {
			throw new RuntimeException();
		}
		
		resultInfo.setMessage("删除成功");
		return resultInfo;
		
	}

	@Override
	public Object modifyAppInfo(App app, AppVersion version) throws Exception {
		ResultInfo resultInfo=new ResultInfo();

		AppVersion version2=appVersionMapper.selectLastVersion(app.getAppId());
		if (version2==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("安装包不存在");
			return resultInfo;
		}
		version.setAppVersionId(version2.getAppVersionId());
		
		//修改应用信息
		int count1=appMapper.updateByPrimaryKeySelective(app);
		if (count1==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		
		int count2=appVersionMapper.updateByPrimaryKeySelective(version);
		if (count2==0) {
			throw new RuntimeException();
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public Object cancelRecommend(Integer appId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//根据Id获取应用信息
		App app=appMapper.selectByPrimaryKey(appId);
		
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		//获取推荐的分类
		RecommendType type=recommendTypeMapper.selectByPrimaryKey(app.getRecommendTypeId());
		if (type==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("对应的推荐分类不存在");
			return resultInfo;
		}
		
		//更新数量
		type.setCount(type.getCount()-1);
		int count1=recommendTypeMapper.updateByPrimaryKeySelective(type);
		
		if (count1==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("取消推荐失败");
			return resultInfo;
		}
		
		app.setRecommendTypeId(null);
		app.setIsRecommend(0);  //不推荐
		int count2=appMapper.updateByPrimaryKey(app);
		if (count2==0) {
			throw new RuntimeException();
		}
		resultInfo.setMessage("成功");
		return resultInfo;
	}
	
}
