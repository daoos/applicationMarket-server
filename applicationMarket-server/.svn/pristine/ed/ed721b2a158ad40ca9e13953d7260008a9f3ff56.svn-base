package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

import com.techwells.applicationMarket.dao.AdminMapper;
import com.techwells.applicationMarket.domain.Admin;
import com.techwells.applicationMarket.domain.AdminAuthority;
import com.techwells.applicationMarket.util.PagingTool;

@Transactional
public interface AdminService {
	/**
	 * 管理员登录
	 * @param accoumt 管理员账号
	 * @param password  密码
	 * @return
	 */
	public Object login(String account,String password) throws Exception;
	
	/**
	 * 添加管理员
	 * @param admin
	 * @return
	 */
	public Object addAdmin(Admin admin,AdminAuthority adminAuthority)throws Exception;
	
	
	/**
	 * 修改管理员
	 * @param admin  管理员对象，其中封装了需要修改的内容
	 * @return
	 * @throws Exception
	 */
	Object modifyAdmin(Admin admin)throws Exception;
	
	/**
	 * 删除管理员
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	Object deleteAdmin(Integer adminId)throws Exception;
	
	/**
	 * 根据账号查找管理员
	 * @param account  账号
	 * @return
	 * @throws Exception
	 */
	Object getAdminByAccount(String account)throws Exception;
	
	/**
	 * 分页获取管理员信息
	 * @param pagingTool 分页的封装
	 * @return
	 * @throws Exception
	 */
	Object getAdminBatch(PagingTool pagingTool)throws Exception;
	
	/**
	 * 根据Id获取详细信息
	 * @param adminId
	 * @return
	 * @throws Exception
	 */
	Object getAdminById(Integer adminId)throws Exception;
	
	
	
}
