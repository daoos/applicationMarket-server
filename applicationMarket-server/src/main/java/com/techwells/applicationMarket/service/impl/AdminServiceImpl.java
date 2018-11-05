package com.techwells.applicationMarket.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.techwells.applicationMarket.dao.AdminAuthorityMapper;
import com.techwells.applicationMarket.dao.AdminMapper;
import com.techwells.applicationMarket.domain.Admin;
import com.techwells.applicationMarket.domain.AdminAuthority;
import com.techwells.applicationMarket.service.AdminService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class AdminServiceImpl implements AdminService{

	@Resource
	private AdminMapper adminMapper;
	
	@Resource
	private AdminAuthorityMapper adminAuthorityMapper;
	
	@Override
	public Object login(String account, String password) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//根据管理员账号查找是否存在
		Admin admin=adminMapper.selectAdminByAccount(account,1);
		
		if (admin==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该账号不存在或者被禁用了");
			return resultInfo;
		}
		
		//该账号存在，比较密码
		if (!admin.getPassword().equals(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码错误");
			return resultInfo;
		}
		
		//密码相同，那么登录成功，返回权限列表
		AdminAuthority authority=adminAuthorityMapper.selectByPrimaryKey(admin.getAdminId());
		
		resultInfo.setMessage("登录成功");	
		resultInfo.setResult(authority);
		resultInfo.setTotal(1);
		return resultInfo;
	}

	@Override
	public Object addAdmin(Admin admin,AdminAuthority adminAuthority) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=adminMapper.insertSelective(admin);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加失败");
			return resultInfo;
		}
		
		//添加权限
		adminAuthority.setAdminId(admin.getAdminId());
		int count1=adminAuthorityMapper.insertSelective(adminAuthority);
		
		if (count1==0) {
			throw new RuntimeException();  //回滚数据
		}
		
		resultInfo.setMessage("添加成功");
		
		return resultInfo;
	}

	@Override
	public Object modifyAdmin(Admin admin) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		//修改数据库中的信息
		int count=adminMapper.updateByPrimaryKeySelective(admin);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public Object deleteAdmin(Integer adminId) throws Exception {
		
		ResultInfo resultInfo=new ResultInfo();
		int count=adminMapper.deleteByPrimaryKey(adminId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		
		//删除对应的权限列表
		
		int count1=adminAuthorityMapper.deleteByPrimaryKey(adminId);
		
		//如果删除失败，那么抛出异常回滚数据
		if (count1==0) {
			throw new Exception();
		}
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}

	@Override
	public Object getAdminByAccount(String account) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<Admin> admins=new ArrayList<Admin>();
		Admin admin=adminMapper.selectAdminByAccount(account, null);   //根据账号查询
		admins.add(admin);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(admins);
		resultInfo.setTotal(1);
		return resultInfo;
	}

	@Override
	public Object getAdminBatch(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<Admin> admins=adminMapper.selectAdminBatch(pagingTool);
		int count=adminMapper.countTotal(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setTotal(count);
		resultInfo.setResult(admins);
		return resultInfo;
	}

	@Override
	public Object getAdminById(Integer adminId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Admin admin=adminMapper.selectByPrimaryKey(adminId);
		
		if (admin==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取详细信息失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(admin);
		return resultInfo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
