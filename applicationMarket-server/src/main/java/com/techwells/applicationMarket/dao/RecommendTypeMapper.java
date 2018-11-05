package com.techwells.applicationMarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.IStabilityClassifier;

import com.techwells.applicationMarket.domain.RecommendType;
import com.techwells.applicationMarket.domain.rs.AppAndVersionVos;
import com.techwells.applicationMarket.util.PagingTool;

public interface RecommendTypeMapper {
    int deleteByPrimaryKey(Integer recommendTypeId);

    int insert(RecommendType record);

    int insertSelective(RecommendType record);

    RecommendType selectByPrimaryKey(Integer recommendTypeId);

    int updateByPrimaryKeySelective(RecommendType record);

    int updateByPrimaryKey(RecommendType record);
    
    /**
     * 根据当前的顺序查询推荐类别
     * @param sort  显示的顺序
     * @return
     */
    RecommendType selectRecommendTypeBySort(Integer sort);

    /**
     * 分页获取应用
     * @param pagingTool
     * @return
     */
	List<AppAndVersionVos> selectAppList(PagingTool pagingTool);

	/**
	 * 获取总数
	 * @param pagingTool
	 * @return
	 */
	int countTotal(PagingTool pagingTool);
	
	
	/**
	 * 分页获取推荐分类
	 * @param pagingTool
	 * @return
	 */
	List<RecommendType> selectRecommendTypes(PagingTool pagingTool);
	
	
	/**
	 * 获取推荐分类的总数
	 * @param pagingTool
	 * @return
	 */
	int countTotalRecommendType(PagingTool pagingTool);
	
	
	
	/**
	 * 根据Id获取分类详情
	 * @param typeId  分类Id
	 * @return
	 */
	RecommendType selectRecommendType(Integer typeId);
	
	
	/**
	 * 获取指定推荐分类下的应用信息
	 * @return
	 */
	List<AppAndVersionVos> selectAppAndVersionVos(Integer typeId);
	
	/**
	 * 限制个数
	 * @param typeId
	 * @param limit
	 * @return
	 */
	List<AppAndVersionVos> selectAppVersionVoList(@Param("typeId")Integer typeId,@Param("platform")Integer platform,@Param("limit")Integer limit);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
}