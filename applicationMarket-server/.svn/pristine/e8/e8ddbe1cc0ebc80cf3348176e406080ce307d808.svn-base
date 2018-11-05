package com.techwells.applicationMarket.service.impl;

import java.security.interfaces.RSAKey;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AaaMapper;
import com.techwells.applicationMarket.domain.Aaa;
import com.techwells.applicationMarket.service.AaaService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 模版的业务层的实现类
 * @author 陈加兵
 */
@Service
public class AaaServiceImpl implements AaaService{
	
	@Resource
	private AaaMapper aaaMapper;
	
	@Override
	public Object addAaa(Aaa aaa) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=aaaMapper.insertSelective(aaa);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@Override
	public Object getAaaById(Integer aaaId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Aaa aaa=aaaMapper.selectByPrimaryKey(aaaId);
		
		if (aaa==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该模版不存在");
			return resultInfo;
		}
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(aaa);
		return resultInfo;
	}

	@Override
	public Object modifyAaa(Aaa aaa) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=aaaMapper.updateByPrimaryKeySelective(aaa);
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public Object deleteAaa(Integer aaaId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=aaaMapper.deleteByPrimaryKey(aaaId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}

	@Override
	public Object getAaaList(PagingTool pagingTool) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
