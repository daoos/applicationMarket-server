package com.techwells.applicationMarket.dao;

import java.util.List;

import jnr.ffi.Struct.int16_t;

import org.apache.ibatis.annotations.Param;

import com.techwells.applicationMarket.domain.App;
import com.techwells.applicationMarket.domain.rs.AppAdminVos;
import com.techwells.applicationMarket.domain.rs.AppAndVersionVos;
import com.techwells.applicationMarket.domain.rs.AppUserVos;
import com.techwells.applicationMarket.domain.rs.AppVersionImageVos;
import com.techwells.applicationMarket.util.PagingTool;

public interface AppMapper {
    int deleteByPrimaryKey(Integer appId);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appId);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);
    
    int countTotal(PagingTool pagingTool);
    
    /**
     * 分页获取用户信息
     * @param pagingTool
     * @return
     */
    List<AppAdminVos> selectAppAdminList(PagingTool pagingTool);
    
    /**
     * 根据推荐分类Id查询所有的软件
     * @param recommendTypeId   //分类Id
     * @return
     */
    List<App> selectAppListByRecommendType(Integer recommendTypeId);
    
    
    /**
     * 根据应用名称查询应用
     * @param appName
     * @return
     */
    List<AppAndVersionVos> selectAppAndVersionVosList(@Param("appName")String appName,@Param("platform")Integer platform);
    
    
    /**
     * 获取应用的历史版本
     * @param appId
     * @return
     */
    List<AppAndVersionVos> selectHistoryList(Integer appId);
    
    
    /**
     * 根据Id获取系统的权限
     * @param appId
     * @return
     */
    AppAndVersionVos selectAppAndVersionVo(Integer appId);
    
    /**
     * 根据Id获取应用的详细信息
     * @param appId
     * @return
     */
    AppVersionImageVos selectAppVersionImageVos(@Param("appId")Integer appId,@Param("platform")Integer platform);
    
    
    /**
     * 获取同一个分类下的应用数量
     * @param pagingTool
     * @return
     */
    int countTotalByType(PagingTool pagingTool);
    
    /**
     * 分页获取该分类下的应用
     * @param pagingTool
     * @return
     */
    List<AppAndVersionVos> selectAppAndVersionVos(PagingTool pagingTool);
    
    
    /**
     * 获取畅销榜
     * @param pagingTool
     * @return
     */
    List<AppAndVersionVos> selectHotSaleList(PagingTool pagingTool);
    
    /**
     * 获取畅销榜的应用总数
     * @param pagingTool
     * @return
     */
    int countHotSalTotal(PagingTool pagingTool);
    
    /**
     * 获取飙升榜的应用
     * @param pagingTool
     * @return
     */
    List<AppAndVersionVos> selectSoaringList(PagingTool pagingTool);
    
    /**
     * 获取飙升榜的总数
     * @param pagingTool
     * @return
     */
    int countSoaringTotal(PagingTool pagingTool);
    
    
    /**
     * 重置download_count_add这个字段的值为0
     * @return
     */
    int updateDownloadCountAdd();
    
    /**
     * 获取应用总数
     * @return
     */
    int countAppTotal();
    
    
    /**
     * 根据分类Id获取应用，排序输出
     * @param typeId
     * @return
     */
    List<App> selectAppsList(Integer typeId);

    /**
     * 通过应用名称进行模糊查找
     * @param appName  应用名称
     * @return
     */
	List<App> selectAppByAppName(@Param("appName")String appName,@Param("platform")Integer platform);
    
    
	
	/**
	 * 获取审核列表
	 * @param pagingTool
	 * @return
	 */
	List<AppUserVos> selectAppUserVos(PagingTool pagingTool);
	
	
	int countTotalAppUserVos(PagingTool pagingTool);
    
	
	/**
	 * 获取必备应用
	 * @return
	 */
	List<AppAndVersionVos> selectMustApp(PagingTool pagingTool);
	
	/**
	 * 获取必备应用的数量
	 * @param pagingTool
	 * @return
	 */
	int countTotalMustApp(PagingTool pagingTool);
	
	/**
	 * 获取待升级的列表
	 * @param pagingTool
	 * @return
	 */
	List<AppAndVersionVos> selectUpgradeList(PagingTool pagingTool);
	
	/**
	 * 获取待升级的总数
	 * @param pagingTool
	 * @return
	 */
	int countTotalUpgrade(PagingTool pagingTool);
	
	
	/**
	 * 分页获取安装记录
	 * @param pagingTool
	 * @return
	 */
	List<AppAndVersionVos> selectInstallRecord(PagingTool pagingTool);
	
	/**
	 * 获取安装记录的总数
	 * @param pagingTool
	 * @return
	 */
	int countTotalInstallRecord(PagingTool pagingTool);
	
	
	/**
	 * 批量删除应用
	 * @param appIds
	 * @return
	 */
	int deleteAppBatch(@Param("appIds")String[] appIds);
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    
    
    
    
}