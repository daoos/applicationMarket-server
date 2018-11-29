package com.techwells.applicationMarket.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.interfaces.RSAKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.abi.datatypes.Int;

import com.aliyuncs.http.HttpRequest;
import com.techwells.applicationMarket.domain.User;
import com.techwells.applicationMarket.domain.UserCode;
import com.techwells.applicationMarket.service.UserCodeService;
import com.techwells.applicationMarket.service.UserService;
import com.techwells.applicationMarket.service.impl.UserCodeServiceImpl;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.Base64Util;
import com.techwells.applicationMarket.util.CRCode;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.SendMailUtils;
import com.techwells.applicationMarket.util.SendSmsUtil;
import com.techwells.applicationMarket.util.StringUtil;
import com.techwells.applicationMarket.util.UploadFileUtils;

/**
 * 用户的controller
 * 
 * @author Administrator
 *
 */
@RestController
public class UserController {

	@Resource
	private UserService userService;
	
	@Resource
	private UserCodeService userCodeService;
	
	
	private Logger logger=LoggerFactory.getLogger(UserController.class);
	

	/**
	 * 用户注册
	 * 
	 * @param mobile
	 *            手机号码
	 * @param password
	 *            密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/regist")
	public Object regist(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String userName = request.getParameter("mobile");  //手机号码或者邮箱
		String password = request.getParameter("password");
		String code = request.getParameter("code"); // 验证码

		// 校验参数
		if (StringUtils.isEmpty(userName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码或者邮箱不能为空");
			return resultInfo;
		}

		// 如果密码为空，那么验证验证码
		if (StringUtils.isEmpty(password)) {

			// 验证验证码
			if (StringUtils.isEmpty(code)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("验证码不能为空");
				return resultInfo;
			}
			
			UserCode userCode=null;
			try {
				userCode=userCodeService.getCodeByMobile(userName);
			} catch (Exception e) {
				logger.error("获取验证码异常");
				resultInfo.setCode("-1");
				resultInfo.setMessage("获取验证码异常");
				return resultInfo;
			}
			
			//如果验证码为空
			if (userCode==null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("请先发送验证码");
				return resultInfo;
			}
			
			//验证码不为空
			if (!userCode.getCode().equals(code)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("验证码不正确");
				return resultInfo;
			}
			resultInfo.setMessage("验证码正确");
			return resultInfo;

		} else { // 如果密码不为空，表示到了最后一步
			if (password.length() < 6) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("密码至少六位");
				return resultInfo;
			}

			// 验证码完成验证，那么可以直接注册了
			try {
				Object object = userService.regist(userName, password);
				return object;
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("-1");
				resultInfo.setMessage("注册异常");
				return resultInfo;
			}
		}

	}

	/**
	 * 获取短信的验证码
	 *  ---- 手机中不能存储session，因此需要将数据保存在数据库中
	 * @param mobile
	 *            手机号码
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/getCode")
	public Object getCode(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String mobile = request.getParameter("mobile");  //手机号码或者邮箱
		
		logger.error("发送邮箱----------------->"+mobile);
		
		
		// 校验参数
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码或者邮箱不能为空");
			return resultInfo;
		}

		String code = SendSmsUtil.getSixRadam(); // 获取六位随机的验证码
		UserCode userCode=null;
		
		try {
			userCode=userCodeService.getCodeByMobile(mobile);
		} catch (Exception e) {
			logger.error("获取验证码异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取验证码异常");
			return resultInfo;
		}
		
		
		//如果为null，可以直接发送
		if (userCode==null) {
			if (mobile.contains("@")) {  //如果是邮箱
				try {
					SendMailUtils.sendEmail(mobile,"应用市场APP", "验证码："+code);
				} catch (Exception e) {
					logger.error("验证码发送异常",e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("验证码发送异常");
					return resultInfo;
				}
			}else {  //如果是手机号码
				try {
					SendSmsUtil.sendUserCrCode(mobile, code);
				} catch (Exception e) {
					logger.error("验证码发送异常",e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("验证码发送异常");
					return resultInfo;
				}
			}
			
			//发送成功之后，将验证码保存在数据库中
			int count=0;
			try {
				userCode=new UserCode();
				userCode.setMobile(mobile);
				userCode.setCode(code);
				count=userCodeService.addCode(userCode);
			} catch (Exception e) {
				logger.error("保存验证码异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("保存验证码异常");
				return resultInfo;
			}
			
			if ( count==0) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("验证码发送失败");
				return resultInfo;
			}
			
		}else {  //如果验证码在数据库中存在，那么需要更新保存的验证码
			
			if (mobile.contains("@")) {  //如果是邮箱
				logger.error("进入邮箱----------------->"+mobile);
				try {
					SendMailUtils.sendEmail(mobile,"应用市场APP", "验证码："+code);
				} catch (Exception e) {
					logger.error("验证码发送异常",e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("验证码发送异常");
					return resultInfo;
				}
			}else {  //如果是手机号码
				try {
					SendSmsUtil.sendUserCrCode(mobile, code);
				} catch (Exception e) {
					logger.error("验证码发送异常",e);
					resultInfo.setCode("-1");
					resultInfo.setMessage("验证码发送异常");
					return resultInfo;
				}
			}
			
			int count=0;
			try {
				userCode.setCode(code); //修改验证码
				count=userCodeService.modifyUserCode(userCode);
			} catch (Exception e) {
				logger.error("更新验证码异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("更新验证码异常");
				return resultInfo;
			}
			
			if (count==0) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("修改验证码失败");
				return resultInfo;
			}
		}
		
		resultInfo.setMessage("验证码发送成功");
		return resultInfo;
	}

	/**
	 * 用户登录
	 * 
	 * @param userName
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/login")
	public Object login(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String userName = request.getParameter("useName");  //手机号码或者邮箱
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(userName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户名不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(password)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("密码不能为空");
			return resultInfo;
		}

		try {
			Object object = userService.login(userName, password);
			ResultInfo rs = (ResultInfo) object;
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("登录异常");
			return resultInfo;
		}
	}

	/**
	 * 忘记密码
	 * 
	 * @param mobile
	 * @param code
	 *            验证码
	 * @param newPassword
	 *            新密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/forgetPassword")
	public Object forgetPassword(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String userName = request.getParameter("mobile");
		String code = request.getParameter("code");
		String newPwd = request.getParameter("newPassword");

		// 校验参数
		if (StringUtils.isEmpty(userName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(code)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("验证码不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(newPwd)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("新密码不能为空");
			return resultInfo;
		}

		UserCode userCode=null;
		try {
			userCode=userCodeService.getCodeByMobile(userName);
		} catch (Exception e) {
			logger.error("获取验证码异常");
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取验证码异常");
			return resultInfo;
		}
		
		//如果验证码为空
		if (userCode==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请先发送验证码");
			return resultInfo;
		}
		
		//验证码不为空
		if (!userCode.getCode().equals(code)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("验证码不正确");
			return resultInfo;
		}
		
		//验证码正确就能修改密码了
		try {
			Object object = userService.forgetPassword(userName, newPwd);
			return object;
		} catch (Exception e) {
			logger.error("修改密码异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改密码异常");
			return resultInfo;
		}
	}

	/**
	 * 修改用户信息
	 * 
	 * @param userIcon
	 *            头像 可选
	 * @param realName
	 *            用户名称 可选
	 * @param gender
	 *            性别 可选
	 * @param address
	 *            地址 可选
	 * @param userId
	 *            用户Id 必填
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/modifyInfo")
	public Object modifyInfo(HttpServletRequest request,@RequestParam(value="userIcon",required=false)MultipartFile userIcon) {
		ResultInfo resultInfo = new ResultInfo();
//		String userIcon = request.getParameter("userIcon"); // 头像 base64
		String realName = request.getParameter("realName");
		String gender = request.getParameter("gender");
		String address = request.getParameter("address");
		String userId = request.getParameter("userId");
		String provinceCode = request.getParameter("provinceCode"); // 省份的编码

		// 校验参数

		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}

		if (!StringUtil.isNumber(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id只能是数字");
			return resultInfo;
		}

		User user = new User(); // 创建对象存放数据
		user.setUserId(Integer.parseInt(userId));

		// 如果参数不为空，是需要修改的内容表示
		if (!StringUtils.isEmpty(address)) {
			if (StringUtils.isEmpty(provinceCode)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("省份的编码不能为空");
				return resultInfo;
			}
			user.setProvinceCode(provinceCode);
			user.setAddress(address);
		}

		if (!StringUtils.isEmpty(realName)) {
			user.setRealName(realName);
		}

		if (!StringUtils.isEmpty(gender)) {

			if (!StringUtil.isNumber(gender)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("性别只能是数字，1,2,3");
				return resultInfo;
			}
			user.setGender(Integer.parseInt(gender));
		}

		// 如果头像不为空，那么需要上传头像
		if (userIcon!=null) {
			String fileName = System.currentTimeMillis()+userIcon.getOriginalFilename();  //文件名称
			String path = ApplicationMarketConstants.UPLOAD_PATH
					+ "user-image/"; // 上传的路径
			File file = new File(path, fileName);
			String urlPath = ApplicationMarketConstants.UPLOAD_URL
					+ "user-image/" + fileName;

			// 如果文件夹不存在，那么创建一个
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs(); // 递归创建文件夹
			}

			
			try {
				userIcon.transferTo(file);
			} catch (Exception e) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传头像失败");
				return resultInfo;
			}
			user.setUserIcon(urlPath);
		}

		try {
			Object object = userService.modifyInfo(user);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改信息异常");
			return resultInfo;
		}

	}

	/**
	 * 用户退出的接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/exit")
	public Object exit(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		try {
			request.getSession().invalidate(); // 清除 session中存储信息即可
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("退出异常");
			return resultInfo;
		}
		resultInfo.setMessage("退出成功");
		return resultInfo;

	}

	/**
	 * 认证开发者
	 * 后台认证  
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/authentication")
	public Object authentication(
			HttpServletRequest request,
			@RequestParam(value = "idCardImage", required = false) MultipartFile idCardImage,
			@RequestParam(value = "businessLicense", required = false) MultipartFile businessLicense) {
		ResultInfo resultInfo = new ResultInfo();
		
		//接受的请求参数
		String type = request.getParameter("type"); // 1 是个人开发者 2 企业开发者
		String userName = request.getParameter("userName");// 用户账号
		String realName=request.getParameter("realName");//个人名称
		String enterpriseName=request.getParameter("enterpriseName"); //企业名称
		String publishName = request.getParameter("publishName"); // 出品人
		String number=request.getParameter("number");   //营业执照注册号
		String registerAddress=request.getParameter("registerAddress");   //营业执照的注册地址
		
		String contactPerson=request.getParameter("contactPerson");  //联系人姓名
		String contactMobile=request.getParameter("contactMobile");  //联系电话
		String contactAddress=request.getParameter("contactAddress");  //联系人地址，详细地址
		String email=request.getParameter("email");  //电子邮箱
		String qq=request.getParameter("qq");  //qq
		String website=request.getParameter("website");  //官网地址
//		String isAgree=request.getParameter("isAgree");  //是否同意协议  1 同意 0 不同意
		String provinceCode=request.getParameter("provinceCode"); //省份的编码，联系地址
		
		//校验参数
		if (StringUtils.isEmpty(userName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户账号不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(provinceCode)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("省份编码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(contactPerson)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("联系人姓名不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(contactMobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		if (StringUtils.isEmpty(contactAddress)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("联系地址不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(publishName)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("出品人不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(email)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("邮箱不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(qq)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("qq不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(isAgree)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("请先同意服务条款");
//			return resultInfo;
//		}
		
		//验证当前用户是否存在
		User user=null;
		try {
			user = userService.getUser(userName);
		} catch (Exception e1) {
			e1.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取用户信息异常");
			return resultInfo;
		}
		
		if (user==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该账号不存在");
			return resultInfo;
		}
		
		//如果此时认证的角色和当前的角色相同，那么已经认证过了
		if (user.getIsDeveloper().equals(Integer.parseInt(type))) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前账号已经认证过了，不要重复认证");
			return resultInfo;
		}
		
		//封装公共的数据
		User user1=new User();
		user1.setUserId(user.getUserId());  //设置用户Id
		//设置公共的数据
		user1.setContactName(contactPerson); //联系人
		user1.setContactMobile(contactMobile); //联系电话
		user1.setContactAddress(contactAddress);  //联系地址
		user1.setEmail(email); //邮箱
		user1.setQq(qq);
		user1.setPublisherName(publishName);  //出品人
		user1.setIsDeveloper(Integer.parseInt(type));  //设置认证类型
		user1.setExaminStatus(0);  //设置审核状态为未审核
		user1.setProvinceCode(provinceCode);  //设置省份编码
		user.setAuthDate(new Date());  //设置认证请求
		//如果官网地址不为空，那么需要设置
		if (!StringUtils.isEmpty(website)) {
			user1.setWebAddress(website);
		}
		
		//下面判断两类认证类型，1、个人开发者   2、企业开发者
		
		//如果是个人开发者
		if (type.equals("1")) {
			if(StringUtils.isEmpty(realName)){
				resultInfo.setCode("-1");
				resultInfo.setMessage("个人姓名不能为空");
				return resultInfo;
			}
			
			if (idCardImage==null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("身份证照片不能为空");
				return resultInfo;
			}
			
			//上传身份证照片
			String fileName=System.currentTimeMillis()+idCardImage.getOriginalFilename();  //文件名称
			String url="";   //文件的链接
			try {
				url=UploadFileUtils.uploadFile("user-info", fileName,idCardImage);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件失败");
				return resultInfo;
			}
			
			//封装数据
			user1.setRealName(realName);
			user1.setIdCardImage(url);  //设置身份证照片的url
			
			
			//调用service层的方法
			
			
		}else if(type.equals("2")){   //如果是企业开发者
			if(StringUtils.isEmpty(enterpriseName)){
				resultInfo.setCode("-1");
				resultInfo.setMessage("企业名称不能为空");
				return resultInfo;
			}
			
			if(StringUtils.isEmpty(number)){
				resultInfo.setCode("-1");
				resultInfo.setMessage("注册号不能为空");
				return resultInfo;
			}
			
			if(StringUtils.isEmpty(registerAddress)){
				resultInfo.setCode("-1");
				resultInfo.setMessage("注册地址不能为空");
				return resultInfo;
			}
			
			if (businessLicense==null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("营业执照不能为空");
				return resultInfo;
			}
			
			
			//上传营业执照
			String fileName=System.currentTimeMillis()+businessLicense.getOriginalFilename();  //文件名称
			String url="";   //文件的链接
			try {
				//执行上传的代码
				url=UploadFileUtils.uploadFile("user-businessLicense", fileName,businessLicense);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件失败");
				return resultInfo;
			}
			
			//封装数据
			user1.setEnterpriseName(enterpriseName);  //企业名称
			user1.setBusinessLicenseNumber(number);  //注册号
			user1.setRegistedAddress(registerAddress); //注册地址
			user1.setBusinessLicense("user-businessLicense/"+fileName);  //设置营业执照的相对路径，用来下载
		}
		
		//调用service的方法
		try {
			Object object=userService.modifyInfo(user1);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("注册异常");
			return resultInfo;
		}

	}
	
	/**
	 * 获取用户的待审核的列表(开发者用户)
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/getExaminList")
	public Object getExaminList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		//可选参数
		String userId=request.getParameter("userId");  //用户Id
		String userName=request.getParameter("userName");  //用户名
		String realName=request.getParameter("realName"); //用户名称
		String provinceCode=request.getParameter("provinceCode"); //省份的编码
		String authDate=request.getParameter("authDate"); //认证请求时间
		String status=request.getParameter("status");  //审核状态  0 未审核 1 审核通过 2 审核失败
		
		//校验数据
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
		
		//构造分页数据
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		//封装过滤条件
		Map<String, Object> params=new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(userId)) {
			params.put("userId", Integer.parseInt(userId));
		}
		
		if (!StringUtils.isEmpty(userName)) {
			params.put("userName", userName);
		}
		
		if (!StringUtils.isEmpty(realName)) {
			params.put("realName", realName);
		}
		
		if (!StringUtils.isEmpty(provinceCode)) {
			params.put("provinceCode", provinceCode);
		}
		
		if (!StringUtils.isEmpty(authDate)) {
			params.put("authDate", authDate);
		}
		
		if (!StringUtils.isEmpty(status)) {
			params.put("status", Integer.parseInt(status));
		}
		
		pagingTool.setParams(params);
		
		//调用service
		try {
			Object object=userService.getExaminList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	
	/**
	 * 审核用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/exmain")
	public Object exmain(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String[] ids=request.getParameterValues("ids");  //用户Id
		String status=request.getParameter("status"); //审核状态  0 未审核 1 审核通过 2 审核失败
		
		if (ids==null||ids.length==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(status)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("审核状态不能为空");
			return resultInfo;
		}
		
		
		//调用service层的方法
		try {
			Object object=userService.modifyBatch(ids,Integer.parseInt(status));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据用户Id获取用户详细信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/getUser")
	public Object getUser(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=userService.getUserById(Integer.parseInt(userId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 下载文件 包括营业执照
	 * @param request
	 * @param relativePath ：相对位置
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/user/download")
	public ResponseEntity<byte[]> downloadFile(@RequestParam("relativePath")String relativePath) throws IOException {
		String fileName = relativePath.split("/")[1];  //文件名称
		// 获取下载文件的路径，在文件中的真实路径
		String path = ApplicationMarketConstants.UPLOAD_PATH+relativePath;  //文件的绝对路径
		// 下载文件的全路径
		File file = new File(path);
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
	 * 修改手机号码
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/changeMobile")
	public Object changeMobile(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String userId=request.getParameter("userId");  //用户Id
		String mobile=request.getParameter("mobile");  //手机号码
		String code=request.getParameter("code");  //验证码
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(mobile)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("手机号码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(code)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("验证码不能为空");
			return resultInfo;
		}
		
		
		UserCode userCode=null;
		try {
			userCode=userCodeService.getCodeByMobile(mobile);
		} catch (Exception e) {
			logger.error("获取验证码异常");
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取验证码异常");
			return resultInfo;
		}
		
		//如果验证码为空
		if (userCode==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请先发送验证码");
			return resultInfo;
		}
		
		//验证码不为空
		if (!userCode.getCode().equals(code)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("验证码不正确");
			return resultInfo;
		}
		
		//根据用户手机号码查询是否这个用户已经存在
		try {
			User user1=userService.getUser(mobile);
			//如果用户存在，并且不是当前的用户，那么这个手机号码已经存在了
			if (user1!=null&&!user1.getUserId().equals(Integer.parseInt(userId))) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("该手机号码已经存在");
				return resultInfo;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取用户信息异常");
			return resultInfo;
		}
		
		//验证码相同，可以修改
		User user=new User();
		user.setUserId(Integer.parseInt(userId));
		user.setUserName(mobile);
		user.setMobile(mobile);
		
		try {
			Object object=userService.modifyInfo(user);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/modifyPwd")
	public Object modifyPwd(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");
		String oldPwd=request.getParameter("oldPwd");
		String newPwd=request.getParameter("newPwd");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(oldPwd)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前密码不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(newPwd)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("新密码不能为空");
			return resultInfo;
		}
		
		
		try {
			Object object=userService.modifyPwd(Integer.parseInt(userId),oldPwd,newPwd);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		
		
		
	}
	
	
	/**
	 * 后台获取用户信息  分页
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/getUserListBack")
	public Object getUserListBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		String userId=request.getParameter("userId");
		String userName=request.getParameter("userName");  //用户账号
		String realName=request.getParameter("realName"); //用户名称
		String gender=request.getParameter("gender"); //性别
		String isDeveloper=request.getParameter("isDeveloper");  //是否是开发者 0 不是 1是
		
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
		
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		Map<String, Object> params=new HashMap<String, Object>();
		
		if (!StringUtils.isEmpty(userId)) {
			params.put("userId", Integer.parseInt(userId));
		}
		
		if (!StringUtils.isEmpty(userName)) {
			params.put("userName",userName);
		}
		
		
		if (!StringUtils.isEmpty(realName)) {
			params.put("realName", realName);
		}
		
		
		if (!StringUtils.isEmpty(gender)) {
			params.put("gender", Integer.parseInt(gender));
		}

		
		if (!StringUtils.isEmpty(isDeveloper)) {
			params.put("isDeveloper", Integer.parseInt(isDeveloper));
		}
		
		pagingTool.setParams(params);
		
		try {
			Object object=userService.getUserListBack(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
		
	}
	
	
	/**
	 * 获取用户完成任务的列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/getTaskListBack")
	public Object getTaskListBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=userService.getTaskListBack(Integer.parseInt(userId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 删除用户
	 * @param userId  用户Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/deleteById")
	public Object deleteById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		User user=new User();
		user.setUserId(Integer.parseInt(userId));
		user.setDeleted(0);  //已经删除
		
		//调用service的方法，实际上就是修改状态
		try {
			Object object=userService.modifyInfo(user);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取个人中心的数据（已登录的）
	 * @param request
	 * @return
	 */
	@RequestMapping("/user/getCenterInfo")
	public Object getCenterInfo(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		String platform=request.getParameter("platform");  //平台
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(platform)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("平台不能为空");
			return resultInfo;
		}
		
		try {
			Object object=userService.getCenterInfo(Integer.parseInt(userId),Integer.parseInt(platform));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
