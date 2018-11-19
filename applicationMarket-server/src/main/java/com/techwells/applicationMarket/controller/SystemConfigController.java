package com.techwells.applicationMarket.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techwells.applicationMarket.domain.SystemConfig;
import com.techwells.applicationMarket.service.SystemConfigService;
import com.techwells.applicationMarket.util.Base64Util;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.StringUtil;

/**
 * 系统配置的controller
 * @author 陈加兵
 */
@RestController
public class SystemConfigController {
	@Resource
	private SystemConfigService systemConfigService;
	
	private Logger logger=LoggerFactory.getLogger(SystemConfigController.class);  //日志
	
	
	/**
	 * 修改系统配置
	 *		1、对秘钥进行加密
	 * @param request
	 * @return
	 */
	@RequestMapping("/config/modifyInfo")
	public Object modifyInfo(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String configId=request.getParameter("configId");
		String isTransfer=request.getParameter("isTransfer");
		String isNeedSecret=request.getParameter("isNeedSecret");
		String isNeedPwd=request.getParameter("isNeedPwd");
		String aboutUs=request.getParameter("aboutUs");
		String copyRight=request.getParameter("copyRight");
		String companyPhone=request.getParameter("companyPhone");
		String companyAddress=request.getParameter("companyAddress");
		String moacAddress=request.getParameter("moacAddress"); //墨客钱包地址
		String moacSecret=request.getParameter("moacSecret"); //墨客钱包秘钥
		String swtcAddress=request.getParameter("swtcAddress"); //井通钱包地址
		String swtcSecret=request.getParameter("swtcSecret"); //井通钱包秘钥
		
		
		//校验参数
		if (StringUtils.isEmpty(configId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("配置Id不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(moacAddress)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("墨客钱包地址不能为空");
//			return resultInfo;
//		}
//		
//		if (StringUtils.isEmpty(moacSecret)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("墨客钱包秘钥不能为空");
//			return resultInfo;
//		}
//		
//		if (StringUtils.isEmpty(swtcAddress)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("井通钱包地址不能为空");
//			return resultInfo;
//		}
//
//		if (StringUtils.isEmpty(swtcSecret)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("井通钱包秘钥不能为空");
//			return resultInfo;
//		}
		
		
		
		SystemConfig config=null;//获取
		try {
			config = systemConfigService.getSystemConfigById(1);
		} catch (Exception e1) {
			e1.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取信息异常");
			return resultInfo;
		}  
		
		if (config==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该配置信息不存在");
			return resultInfo;
		}
		
		if (!StringUtil.isNumber(configId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("配置Id只能是数字");
			return resultInfo;
		}
		
		if (!StringUtils.isEmpty(isTransfer)) {
			if (!StringUtil.isNumber(isTransfer)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("用户是否可以转账格式错误");
				return resultInfo;
			}
			config.setIsRansfer(Integer.parseInt(isTransfer));
		}
		
		if (!StringUtils.isEmpty(isNeedSecret)) {
			if (!StringUtil.isNumber(isNeedSecret)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("用户是否需要导入私钥格式错误");
				return resultInfo;
			}
			config.setIsNeedSecret(Integer.parseInt(isNeedSecret));
		}
		
		if (!StringUtils.isEmpty(isNeedPwd)) {
			if (!StringUtil.isNumber(isNeedPwd)) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("用户是否需要输入密码格式错误");
				return resultInfo;
			}
			config.setIsNeedPwd(Integer.parseInt(isNeedPwd));
		}
		
		if (!StringUtils.isEmpty(aboutUs)) {
			config.setAboutUs(aboutUs);
		}
		
		if (!StringUtils.isEmpty(copyRight)) {
			config.setCopyRight(copyRight);
		}
		
		if (!StringUtils.isEmpty(companyPhone)) {
			config.setCompanyPhone(companyPhone);
		}
		
		if (!StringUtils.isEmpty(companyAddress)) {
			config.setCompanyAddress(companyAddress);
		}
		
		if (!StringUtils.isEmpty(moacAddress)) {
			config.setMoacAddress(moacAddress);
		}
		
		//秘钥加密
		if (!StringUtils.isEmpty(moacSecret)&&!config.getMoacSecret().equals(moacSecret)) {
			try {
				config.setMoacSecret(Base64Util.encoder(moacSecret));
			} catch (Exception e) {
				logger.error("墨客秘钥加密异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("墨客秘钥加密异常");
				return resultInfo;
			}
		}
		
		if (!StringUtils.isEmpty(swtcAddress)) {
			config.setSwtcAddress(swtcAddress);
		}
		
		if (!StringUtils.isEmpty(swtcSecret)&&!config.getSwtcSecret().equals(swtcSecret)) {
			try {
				config.setSwtcSecret(Base64Util.encoder(swtcSecret));
			} catch (Exception e) {
				logger.error("井通秘钥加密异常",e);
				resultInfo.setCode("-1");
				resultInfo.setMessage("井通秘钥加密异常");
				return resultInfo;
			}
		}
		
		//修改
		try {
			Object object=systemConfigService.modifyInfo(config);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改异常");
			return resultInfo;
		}
		
	}
	
	/**
	 * 获取配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/config/getConfigInfo")
	public Object getConfig(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		try {
			Object object=systemConfigService.getConfigInfo(1);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
}
