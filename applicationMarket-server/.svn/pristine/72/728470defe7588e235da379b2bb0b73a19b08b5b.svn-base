package com.techwells.applicationMarket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.RecommendTypeMapper;
import com.techwells.applicationMarket.domain.App;
import com.techwells.applicationMarket.domain.RecommendType;
import com.techwells.applicationMarket.domain.rs.AppAndVersionVos;
import com.techwells.applicationMarket.service.RecommendTypeService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class RecommendTypeServiceImpl implements RecommendTypeService {

	@Resource
	private RecommendTypeMapper recommendTypeMapper;
	
	@Resource
	private AppMapper appMapper;

	@Override
	public Object addRecommendType(RecommendType recommendType) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		
		//根据首页展示的顺序查找当前的分类Id是否存在
		RecommendType recommendType2=recommendTypeMapper.selectRecommendTypeBySort(recommendType.getSort());
		
		//如果推荐状态不为null，表示已经存在，那么不能添加
		if (recommendType2!=null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该展示顺序已经存在类别");
			return resultInfo;
		}

		//如果为null，表示存在
		int count = recommendTypeMapper.insertSelective(recommendType);
		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("插入失败");
			return resultInfo;
		}

		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@Override
	public Object deleteRecommendType(Integer recommendTypeId) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		
		int count = recommendTypeMapper.deleteByPrimaryKey(recommendTypeId);

		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		
		//删除成功的话，那么所有的该分类下的所有软件都必须置为不推荐并且推荐分类类别为空
		
		List<App> apps=appMapper.selectAppListByRecommendType(recommendTypeId);
		
		//遍历修改
		for (App app : apps) {
			app.setIsRecommend(0);  //不推荐
			app.setRecommendTypeId(null);  //推荐Id置为空
			app.setTop(0);
			app.setTopTime(null);
			//修改数据
			int count1=appMapper.updateByPrimaryKey(app);
			
			//修改失败直接抛出异常，回滚数据
			if (count1==0) {
				throw new RuntimeException();
			}
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}

	@Override
	public Object getRecommendType(Integer recommendTypeId) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		RecommendType recommendType=recommendTypeMapper.selectRecommendType(recommendTypeId);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(recommendType);
		return resultInfo;
	}

	@Override
	public Object getRecommendTypeList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		int count=recommendTypeMapper.countTotalRecommendType(pagingTool);
		//如果数量为0，不用查询数据库了
		if (count==0) {
			resultInfo.setMessage("获取成功");
			resultInfo.setTotal(count);
			resultInfo.setResult(null);
		}
		
		
		List<RecommendType> recommendTypes=recommendTypeMapper.selectRecommendTypes(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(recommendTypes);
		resultInfo.setTotal(count);
		return resultInfo;
	}

	@Override
	public Object modifyRecommendType(RecommendType recommendType) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		
		//修改之前需要判断此次选择的展示顺序是否存在，如果存在了，那么不能修改
		
		RecommendType recommendType2=recommendTypeMapper.selectRecommendTypeBySort(recommendType.getSort());
		
		//如果存在，并且这个Id和当前正在修改的分类Id不同，那么表示已经存在，因此不可以插入
		if (recommendType2!=null&&!recommendType2.getRecommendTypeId().equals(recommendType.getRecommendTypeId())) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该首页展示的顺序已经存在了");
			return resultInfo;
		}

		//如果不存在，那么即可修改
		int count = recommendTypeMapper.updateByPrimaryKeySelective(recommendType);

		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public RecommendType countRecommendTypeByPageAndLoaction(Integer page, Integer location,
			Integer activated) throws Exception {
//		// 添加之前应该查看同一个页面的同一个位置的推荐分类是否已经存在这个推荐分类，如果存在，不能添加
//		RecommendType recommendType = recommendTypeMapper.countRecommendTypeByPageAndLocation(
//				page, location, activated);
//		// 如果num!=0,表示该推荐分类已经存在，那么不能插入
//		return recommendType;
		return null;
	}

	@Override
	public Object getAppList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//获取分页的数量
		int count=recommendTypeMapper.countTotal(pagingTool);
		List<AppAndVersionVos> appAndVersionVos=recommendTypeMapper.selectAppList(pagingTool);
		resultInfo.setTotal(count);
		resultInfo.setResult(appAndVersionVos);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	@Override
	public Object getDetailInfo(Integer typeId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		RecommendType recommendType=recommendTypeMapper.selectByPrimaryKey(typeId);  //根据Id获取信息
		
		if (recommendType==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该分类不存在");
			return resultInfo;
		}
		//根据分类id查询所有的应用
		
		List<App> apps=appMapper.selectAppsList(typeId);
		recommendType.setApps(apps);
		resultInfo.setResult(recommendType);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	
	//推荐分类的应用总数减少一个
	//应用变成不推荐的状态
	@Override
	public Object removeApp(Integer appId,Integer recommendTypeId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		App app=appMapper.selectByPrimaryKey(appId);
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该应用不存在");
			return resultInfo;
		}
		
		//获取推荐分类的信息
		RecommendType recommendType=recommendTypeMapper.selectByPrimaryKey(recommendTypeId);
		
		if (recommendType==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该推荐分类不存在");
			return resultInfo;
		}
		
		
		//修改状态
		app.setIsRecommend(0);  //不推荐
		app.setRecommendTypeId(null);  //推荐分类的Id为空
		app.setTop(null); //布置顶
		app.setTopTime(null);
		
		int count=appMapper.updateByPrimaryKey(app);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("移除失败");
			return resultInfo;
		}
		
		//数量-1
		recommendType.setCount(recommendType.getCount()-1);
		int count1=recommendTypeMapper.updateByPrimaryKeySelective(recommendType);
		if (count1==0) {
			throw new RuntimeException();
		}
		resultInfo.setMessage("移除成功");
		return resultInfo;
	}

}
