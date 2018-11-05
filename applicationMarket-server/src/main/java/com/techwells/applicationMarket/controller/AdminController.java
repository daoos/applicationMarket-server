package com.techwells.applicationMarket.controller;

import java.security.interfaces.RSAKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techwells.applicationMarket.domain.Admin;
import com.techwells.applicationMarket.domain.AdminAuthority;
import com.techwells.applicationMarket.domain.Authority;
import com.techwells.applicationMarket.service.AdminService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.RandomCodeUtils;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.StringUtil;

/**
 * 管理员的controller
 * 
 * @author Administrator
 *
 */
@RestController
public class AdminController {
	
	@Resource
	private AdminService adminService;
	
	/**
	 * 生成图片验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/admin/getImageCode")
	public Object getImageCode(HttpServletRequest request,HttpServletResponse response){
		ResultInfo resultInfo=new ResultInfo();
		RandomCodeUtils.createRandomCode(request,response );
		return resultInfo;
	}
	
	
	/**
	 * 管理员登录
	 * @param code 验证码
	 * @param account  账号
	 * @param password  密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/login")
	public Object login(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String account=request.getParameter("account");
//		String code=request.getParameter("code");
		String password=request.getParameter("password");
		
		//校验参数
		if (StringUtils.isEmpty(account)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员账号不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(code)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("验证码不能为空");
//			return resultInfo;
//		}
		
		if (StringUtils.isEmpty(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码不能为空");
			return resultInfo;
		}
		
		
		//校验验证码
		
//		String str=(String) request.getSession().getAttribute(RandomCodeUtils.RANDOM_CODE_KEY);
//		request.getSession().setAttribute("hello", "hello");
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("sessioon",str );
//		map.put("code", code);
//		map.put("hello", request.getSession().getAttribute("hello"));
//		if (!code.equals(str)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("验证码不正确，请重新输入");
//			resultInfo.setResult(map);
//			return resultInfo;
//		}
		
		//实行登录
		try {
			Object object=adminService.login(account, password);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("登录异常");
			
			return resultInfo;
		}
	}
	
	
	/**
	 * 添加管理员
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/add")
	public Object addAdmin(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String account=request.getParameter("account");
		String mobile=request.getParameter("mobile");
		String activated=request.getParameter("activated");
		String authoritys=request.getParameter("authoritys");  //权限列表，用逗号分割
//		String deptId=request.getParameter("deptId");
		String description=request.getParameter("description");  
		
		//校验参数
		if (StringUtils.isEmpty(email)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("邮箱不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(description)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("权限描述不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(account)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("账号不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(activated)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("状态不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(authoritys)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("权限不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(deptId)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("所属部门不能为空");
//			return resultInfo;
//		}
		
		//校验格式
		
		if (!StringUtil.isEmail(email)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("邮箱格式错误");
			return resultInfo;
		}
		
		
		if (!StringUtil.isMobile(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码格式错误");
			return resultInfo;
		}
		
//		if (!StringUtil.isNumber(deptId)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("部门Id只能是数字");
//			return resultInfo;
//		}
		
		Admin admin=new Admin();
		admin.setAccount(account);
		admin.setEmail(email);
		admin.setCreatedDate(new Date());
//		admin.setDeptId(Integer.parseInt(deptId));
		admin.setDescription(description);
		admin.setMobile(mobile);
		admin.setActivated(Integer.parseInt(activated));
		admin.setPassword(password);
		
		AdminAuthority adminAuthority=new AdminAuthority();
		adminAuthority.setAuthoritys(authoritys);
		
		try {
			Object object=adminService.addAdmin(admin, adminAuthority);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改管理员
	 * @param email  邮箱
	 * @param newPwd  新密码
	 * @param account  账号
	 * @param mobile  手机号码
	 * @param activated  账号状态
	 * @param deptId  部门Id
	 * @param adminId  管理员Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/modifAdmin")
	public Object modifyAdmin(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String email=request.getParameter("email");
		String password=request.getParameter("newPwd");
		String account=request.getParameter("account");
		String mobile=request.getParameter("mobile");
		String activated=request.getParameter("activated");
//		String authoritys=request.getParameter("authoritys");  //权限列表，用逗号分割
//		String deptId=request.getParameter("deptId");
		String adminId=request.getParameter("adminId");
		
		//校验参数
		if (StringUtils.isEmpty(adminId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(email)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("邮箱账号不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(password)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("新密码不能为空");
//			return resultInfo;
//		}
		
		if (StringUtils.isEmpty(account)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("账号不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(activated)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("账号状态不能为空");
			return resultInfo;
		}
		
		
		//封装参数
		Admin admin=new Admin();
		admin.setAdminId(Integer.parseInt(adminId));
		admin.setEmail(email);
		
		//密码可以不修改，如果不为空，那么表示用户输入了密码，因此需要修改
		if (!StringUtils.isEmpty(password)) {
			admin.setPassword(password);
		}
		
		admin.setMobile(mobile);
		admin.setAccount(account);
		admin.setActivated(Integer.parseInt(activated));
		
		//调用service中的方法
		try {
			Object object=adminService.modifyAdmin(admin);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改异常");
			return resultInfo;
		}
	}
	
	/**
	 * 删除管理员
	 * @param adminId  管理员Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/deleteAdmin")
	public Object deleteAdmin(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String adminId=request.getParameter("adminId");
		
		//校验参数
		if (StringUtils.isEmpty(adminId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=adminService.deleteAdmin(Integer.parseInt(adminId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 分页获取数据
	 * @param pageNum  当前的页数
	 * @param pageSize 每页显示的数量
	 * @param account  账号      可选
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/getAdminBatch")
	public Object getAdminBatch(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String account=request.getParameter("account");  //账号
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		//校验参数
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
		
		if (!StringUtil.isNumber(pageNum)||!StringUtil.isNumber(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前页数和每页显示的数量只能是数字");
			return resultInfo;
		}
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		Map<String, Object> params=new HashMap<String, Object>();
		//如果account不为空，那么设置成过滤条件
		if (!StringUtils.isEmpty(account)) {
			params.put("account", account);
		}
		
		pagingTool.setParams(params);
		
		//调用service层中的方法
		
		try {
			Object object=adminService.getAdminBatch(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据Id获取详细信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/getAdminById")
	public Object getAdminById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String adminId=request.getParameter("adminId");
		
		if (StringUtils.isEmpty(adminId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("管理员Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=adminService.getAdminById(Integer.parseInt(adminId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
