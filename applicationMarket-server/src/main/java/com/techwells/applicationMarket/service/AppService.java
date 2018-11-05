package com.techwells.applicationMarket.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.domain.App;
import com.techwells.applicationMarket.domain.AppImage;
import com.techwells.applicationMarket.domain.AppVersion;
import com.techwells.applicationMarket.domain.UserApp;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface AppService {
	
	/**
	 * 添加应用
	 * @param app  APP
	 * @param images  应用截图
	 * @param appVersion  版本安装包
	 * @return
	 * @throws Exception
	 */
	Object addApp(App app,List<AppImage> images,AppVersion appVersion)throws Exception;
	
	/**
	 * 分页获取数据
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getAppList(PagingTool pagingTool)throws Exception;
	
	
	/**
	 * 应用审核通过
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	Object examinPass(Integer appId)throws Exception;
	
	
	/**
	 * 应用上架
	 * @param appId APP的Id
	 * @return
	 * @throws Exception
	 */
	Object ground(Integer appId)throws Exception;
	
	
	/**
	 * 下架
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	Object under(Integer appId)throws Exception;
	
	
	/**
	 * 拒绝审核
	 * @param appId  应用Id
	 * @return
	 * @throws Exception
	 */
	Object examinFail(Integer appId)throws Exception;
	
	/**
	 * 推荐应用
	 * @param app  封装数据
	 * @return
	 * @throws Exception
	 */
	Object recommend(App app)throws Exception;
	
	
	/**
	 * 搜索应用 根据应用名称
	 * @param appName  
	 * @return
	 * @throws Exception
	 */
	Object searchApp(String appName,Integer platform)throws Exception;

	/**
	 * 根据应用Id获取历史版本
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	Object getHistory(Integer appId)throws Exception;

	/**
	 * 根据Id获取应用的权限详情
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	Object getPower(Integer appId)throws Exception;

	/**
	 * 获取应用的详细信息
	 * @param appId 应用Id
	 * @return
	 * @throws Exception
	 */
	Object getAppDetail(int appId)throws Exception;

	/**
	 * 分页获取对应分类下的应用
	 * @param pagingTool  分页数据
	 * @return
	 * @throws Exception
	 */
	Object getAppListByType(PagingTool pagingTool)throws Exception;
	
	

	/**
	 * 分页获取热销榜
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getHotSaleList(PagingTool pagingTool)throws Exception;

	
	/**
	 * 分页获取飙升榜
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getSoaringList(PagingTool pagingTool)throws Exception;

	/**
	 * 置顶
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	Object top(Integer appId)throws Exception;

	/**
	 * 获取应用详情，后台
	 * @param parseInt
	 * @return
	 * @throws Exception
	 */
	Object getAppDetailInfo(Integer appId)throws Exception;
	
	
	
	/**
	 * 更新安装包
	 * @param version  安装包
	 * @return
	 * @throws Exception
	 */
	Object updateApp(AppVersion version)throws Exception;

	/**
	 * 通过应用名称模糊查找
	 * @param appName  应用名称
	 * @return
	 * @throws Exception
	 */
	Object searchByAppName(String appName,Integer platform)throws Exception;

	/**
	 * 批量导入数据
	 * @param app
	 * @param version
	 * @param images
	 * @return
	 * @throws Exception
	 */
	Object export(App app, AppVersion version, String[] images)throws Exception;
	
	
	/**
	 * 根据应用Id获取应用的信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	App getAppById(Integer appId)throws Exception;

	/**
	 * 获取审核的应用
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getExaminList(PagingTool pagingTool)throws Exception;

	/**
	 * 批量审核应用
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	Object exmain(String[] ids, Integer status)throws Exception;

	/**
	 * 获取首页内容
	 * @return
	 * @throws Exception
	 */
	Object getHome(Integer platform)throws Exception;

	/**
	 * 分页获取必备应用
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getMustApp(PagingTool pagingTool)throws Exception;

	/**
	 * 下载应用
	 * @param userId
	 * @param appId
	 * @return
	 * @throws Exception 
	 */
	Object download(Integer userId, Integer appId) throws Exception;

	/**
	 * 获取升级应用列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Object getUpgradeList(PagingTool pagingTool)throws Exception;

	/**
	 * 应用升级
	 * @param userId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	ResponseEntity<byte[]> upgrade(Integer userId,Integer appId)throws Exception;

	/**
	 * 获取安装记录（包括安装之后卸载的）
	 * @param pagingTool
	 * @return
	 * @throws Exception
	 */
	Object getInstallRecord(PagingTool pagingTool)throws Exception;
	
	
	/**
	 * 修改UserApp表的信息
	 * @param userApp
	 * @return
	 * @throws Exception
	 */
	Object modifyUserApp(UserApp userApp)throws Exception;

	/**
	 * 卸载应用
	 * @param userId
	 * @param packName
	 * @return
	 */
	Object uninstallApp(Integer userId, String packName);
	
	
	/**
	 * 修改app的信息
	 * @param app
	 * @return
	 * @throws Exception
	 */
	Object modifyApp(App app)throws Exception;

	/**
	 * 批量删除
	 * @param appIds
	 * @return
	 * @throws Exception
	 */
	Object deleteAppBatch(String[] appIds)throws Exception;

	/**
	 * 修改应用信息
	 * @param app
	 * @param version
	 * @return
	 * @throws Exception
	 */
	Object modifyAppInfo(App app, AppVersion version)throws Exception;

	/**
	 * 取消推荐
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	Object cancelRecommend(Integer appId)throws Exception;

	
	
	
	
	
	
	
	
	
	
}
