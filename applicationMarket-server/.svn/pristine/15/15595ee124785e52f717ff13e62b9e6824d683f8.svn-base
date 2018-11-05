package com.techwells.applicationMarket.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AppTypeMapper;
import com.techwells.applicationMarket.domain.AppType;
import com.techwells.applicationMarket.service.AppTypeService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class AppTypeServiceImpl implements AppTypeService{
	
	@Resource
	private AppTypeMapper appTypeMapper;
	
	@Override
	public Object getAppTypeList() throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//获取所有的分类列表，包含热门与不是热门的
		List<AppType> appTypes=appTypeMapper.selectAppTypes();
		
		List<AppType> hotTypes=new ArrayList<AppType>();  //存储热门分类
		
		//遍历查询热门分类
		for (AppType appType : appTypes) {
			//如果是热门分类
			if (appType.getIsHot().equals(1)) {
				hotTypes.add(appType);
			}
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("hotTypes", hotTypes);
		map.put("allTypes", appTypes);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(map);
		resultInfo.setTotal(appTypes.size());
		return resultInfo;
	}

	@Override
	public Object hot(AppType appType) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		
		//查询数据，是否已经存在4个热门分类
		List<AppType> types=appTypeMapper.selectHotAppTypes();
		
		if (types.size()>4) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("已经有四个热门分类");
			return resultInfo;
		}
		
		//修改即可
		int count=appTypeMapper.updateByPrimaryKeySelective(appType);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("设置热门失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("设置成功");
		return resultInfo;
	}

	@Override
	public Object cancelHot(AppType appType) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//修改即可
		int count = appTypeMapper.updateByPrimaryKeySelective(appType);
		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("取消失败");
			return resultInfo;
		}

		resultInfo.setMessage("取消成功");
		return resultInfo;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
