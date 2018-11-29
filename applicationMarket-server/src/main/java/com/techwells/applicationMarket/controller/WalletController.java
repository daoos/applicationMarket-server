package com.techwells.applicationMarket.controller;

import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.naming.spi.DirStateFactory.Result;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.techwells.applicationMarket.domain.Wallet;
import com.techwells.applicationMarket.domain.WalletDetail;
import com.techwells.applicationMarket.domain.WalletType;
import com.techwells.applicationMarket.service.WalletService;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.Base64Util;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.qrcode.QrCodeUtil;

/**
 * 钱包的controller
 * @author 陈加兵
 *
 */
@RestController
public class WalletController {
	
	@Resource
	private WalletService walletService;
	
	private Logger logger=Logger.getLogger(WalletController.class);
	
	/**
	 * 添加钱包
	 * 1、前台已经根据后台的设置做出了导入钱包的步骤，因此这里不需要控制，如果不填就是不需要的数据
	 * 2、导入钱包需要根据用户的地址查询用户的余额，将其添加到数据库中
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/addWallet")
	public Object addWallet(HttpServletRequest request,@RequestParam(value="keystore",required=false)MultipartFile keystore){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		String type=request.getParameter("type");  //钱包类型 1 墨客 2 井通
		String address=request.getParameter("address"); //钱包地址
		
		//可选,根据后台的选择填写
		String secret=request.getParameter("secret");  //秘钥
		String password=request.getParameter("password");  //密码
		
		//校验参数
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(type)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包类型不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(address)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包地址不能为空");
			return resultInfo;
		}
		
		
		if (!type.equals("1")&&!type.equals("2")) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请填写正确的钱包类型");
			return resultInfo;
		}
		
		if ("1".equals(type)&&keystore==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("请上传keystore文件");
			return resultInfo;
		}
		
		String keystoreUrl=null;
		//上传keystore文件
		if (keystore!=null) {
			String fileName=System.currentTimeMillis()+keystore.getOriginalFilename();  //文件名称
			String path=ApplicationMarketConstants.UPLOAD_PATH+"keystore/";  //文件的位置
			keystoreUrl=ApplicationMarketConstants.UPLOAD_URL+"keystore/"+fileName;
			File targetFile=new File(path,fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			
			try {
				keystore.transferTo(targetFile);
			} catch (Exception e) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件失败");
				return resultInfo;
			}
		}
		
		
		//封装数据
		Wallet wallet=new Wallet();
		wallet.setUserId(Integer.parseInt(userId));
		wallet.setAddress(address);
		wallet.setType(Integer.parseInt(type));  //钱包类型
		
		//如果秘钥不为空，表示需要填写，需要加密
		if (!StringUtils.isEmpty(secret)) {
			try {
				wallet.setSecret(Base64Util.encoder(secret));
			} catch (Exception e) {
				logger.error("秘钥加密异常",e);
			}
		}
		
		if (!StringUtils.isEmpty(password)) {
			wallet.setPassword(password);
		}
		
		if (keystoreUrl!=null) {
			wallet.setKeystoreUrl(keystoreUrl);
		}
		
		//添加钱包的二维码
		String qrcodeName=System.currentTimeMillis()+".png";  //二维码的名字
		String path=ApplicationMarketConstants.UPLOAD_PATH+"wallet/";   //二维码存放的地址
		File targetFile=new File(path,qrcodeName);  //目标路径
		String qrcodeUrl=ApplicationMarketConstants.UPLOAD_URL+"wallet/"+qrcodeName;  //访问路径
		
		//如果文件夹不存在，那么需要自动生成
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();  
		}
		
		try {
			QrCodeUtil.createZxingqrCode(address, targetFile, 240, 238, "png");
		} catch (Exception e1) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("生成二维码图片异常");
			return resultInfo;
		}
		
		wallet.setQrcode(qrcodeUrl);  //设置url
		
		//调用service层的方法
		try {
			Object object=walletService.addWallet(wallet);
			return object;
		} catch (Throwable e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 重置钱包
	 * 1、需要重置其中的所有信息
	 * 2、还是需要查询余额
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/reset")
	public Object reset(HttpServletRequest request,@RequestParam(value="keystore",required=false)MultipartFile keystore){
		ResultInfo resultInfo=new ResultInfo();
		String walletId=request.getParameter("walletId");  //钱包Id
		String address=request.getParameter("address"); //钱包地址
		
		//可选,根据后台的选择填写
		String secret=request.getParameter("secret");  //秘钥
		String password=request.getParameter("password");  //密码
		
		if (StringUtils.isEmpty(walletId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包Id不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(type)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("钱包类型不能为空");
//			return resultInfo;
//		}
		
		if (StringUtils.isEmpty(address)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包地址不能为空");
			return resultInfo;
		}
		
		
//		if (!"1".equals(type)||"2".equals(type)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("请填写正确的钱包类型");
//		}
		
		//查看当前的钱包是否存在
		Wallet wallet;
		try {
			wallet = walletService.getWallet(Integer.parseInt(walletId));
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取钱包信息异常");
			return resultInfo;
		}
		
		if (wallet==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该钱包不存在");
			return resultInfo;
		}
		
		if (wallet.getType().equals(1)&&keystore==null) {   //如果是墨客钱包
			resultInfo.setCode("-1");
			resultInfo.setMessage("请上传keystore文件");
			return resultInfo;
		}
		
		
		String keystoreUrl=null;
		//上传keystore文件
		if (keystore!=null) {
			String fileName=System.currentTimeMillis()+keystore.getOriginalFilename();  //文件名称
			String path=ApplicationMarketConstants.UPLOAD_PATH+"keystore/";  //文件的位置
			keystoreUrl=ApplicationMarketConstants.UPLOAD_URL+"keystore/"+fileName;
			File targetFile=new File(path,fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			try {
				keystore.transferTo(targetFile);
			} catch (Exception e) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传文件失败");
				return resultInfo;
			}
		}
		
		if (keystoreUrl!=null) {   //如果上传了keystore文件
			wallet.setKeystoreUrl(keystoreUrl);
		}
		
		//钱包存在，那么需要修改其中的信息
		wallet.setAddress(address); //地址
//		wallet.setType(Integer.parseInt(type));
		
		//秘钥和密码如果不为空，需要修改，如果为空，那么需要将其置为空
		if (!StringUtils.isEmpty(secret)) {
			try {
				wallet.setSecret(Base64Util.encoder(secret));
			} catch (Exception e) {
				logger.error("秘钥加密异常",e);
			}  
		}else {
			wallet.setSecret(null);  
		}
		wallet.setPassword(password);
		
		//生成二维码
		//添加钱包的二维码
		String qrcodeName = System.currentTimeMillis() + ".png"; // 二维码的名字
		String path = ApplicationMarketConstants.UPLOAD_PATH + "wallet/"; // 二维码存放的地址
		File targetFile = new File(path, qrcodeName); // 目标路径
		String qrcodeUrl = ApplicationMarketConstants.UPLOAD_URL + "wallet/"
				+ qrcodeName; // 访问路径
		
		
		
		// 如果文件夹不存在，那么需要自动生成
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		try {
			QrCodeUtil.createZxingqrCode(address, targetFile, 240, 238, "png");
		} catch (Exception e1) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("生成二维码图片异常");
			return resultInfo;
		}

		wallet.setQrcode(qrcodeUrl); // 设置url
		
		//调用service修改钱包
		try {
			Object object=walletService.resetWallet(wallet);
			return object;
		} catch (Throwable e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("重置异常");
			return resultInfo;
		}
		
	}
	
	/**
	 * 获取钱包详情，包括钱包明细（我的钱包页面）
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getWalletById")
	public Object getWalletById(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String walletId=request.getParameter("walletId");
		
		if (StringUtils.isEmpty(walletId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=walletService.getWalletById(Integer.parseInt(walletId));
			return object;
		} catch (Throwable e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 修改钱包
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/modifyWallet")
	public Object modifyWallet(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String walletId=request.getParameter("walletId");
		
		if (StringUtils.isEmpty(walletId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包Id不能为空");
			return resultInfo;
		}
		
		//封装数据
		Wallet wallet=new Wallet();
		wallet.setWalletId(Integer.parseInt(walletId));
		
		//调用service层的方法
		try {
			Object object=walletService.modifyWallet(wallet);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 删除钱包
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/deleteWallet")
	public Object deleteWallet(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String walletId=request.getParameter("walletId");
		
		if (StringUtils.isEmpty(walletId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包Id不能为空");
			return resultInfo;
		}
		
		//调用service层的方法
		try {
			Object object=walletService.deleteWallet(Integer.parseInt(walletId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 分页获取钱包列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getWalletList")
	public Object getWalletList(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
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
		
		pagingTool.setParams(params);
		
		//调用service方法
		try {
			Object object=walletService.getWalletList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 钱包转账
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/transfer")
	public Object transfer(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		//必传参数
		String toAddress=request.getParameter("toAddress");  //转账地址
		String money=request.getParameter("money");   //金额
		String walletId=request.getParameter("walletId");  //钱包的Id
		String pwd=request.getParameter("pwd");  //密码
		
		String hash=request.getParameter("hash"); //hash值     墨客转账的hash
		
		//参数校验
//		if (StringUtils.isEmpty(toAddress)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("转账地址不能为空");
//			return resultInfo;
//		}
//		
//		if (StringUtils.isEmpty(money)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("金额不能为空");
//			return resultInfo;
//		}
		
		if (StringUtils.isEmpty(walletId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包Id不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(pwd)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("密码不能为空");
//			return resultInfo;
//		}
		
		//封装参数
		WalletDetail detail=new WalletDetail();  
		detail.setWalletId(Integer.parseInt(walletId));
		detail.setToAddress(toAddress);
		detail.setMoney(money);
		detail.setHash(hash);
		
		//调用service层的
		try {
			Object object=walletService.transfer(detail,pwd);  //转账
			return object;
		} catch (Throwable e) {
			logger.error("转账异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("转账异常");
			return resultInfo;
		}
	}
	
	/**
	 * 获取明细详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getDetail")
	public Object getDetail(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String detailId=request.getParameter("detailId");
		
		//详情Id
		if (StringUtils.isEmpty(detailId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("详情Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=walletService.getDetail(Integer.parseInt(detailId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	/**
	 * 获取钱包明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getWalletDetail")
	public Object getWalletDetail(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String walletId=request.getParameter("walletId");
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		if (StringUtils.isEmpty(walletId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前数量不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("每页显示的数量不能为空");
			return resultInfo;
		}
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("walletId", Integer.parseInt(walletId));
		pagingTool.setParams(params);
		
		//调用service层的方法
		
		try {
			Object object=walletService.getWalletDetail(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 获取钱包管理（钱包管理页面，获取用户的两个钱包）
	 * 1、获取钱包，肯定是要获取每个钱包的余额的，需要和外界的同步，因为钱包不止是在这里转账
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getWalletManage")
	public Object getWalletManage(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=walletService.getWalletManage(Integer.parseInt(userId));
			return object;
		} catch (Throwable e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	/**
	 * 切换钱包
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/change")
	public Object change(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户Id
		String walletId=request.getParameter("walletId");  //钱包Id
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包Id不能为空");
			return resultInfo;
		}
		
		
		if (StringUtils.isEmpty(walletId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		
		try {
			Object object=walletService.change(Integer.parseInt(userId),Integer.parseInt(walletId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
		
	}
	
	
	/**
	 * 获取钱包明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getWalletDetailBack")
	public Object getWalletDetailBack(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId"); //用户Id
		String walletType=request.getParameter("walletType");  //钱包类型  1 MOAC 2 SWTC
		
		String pageNum=request.getParameter("pageNum");
		String pageSize=request.getParameter("pageSize");
		
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
//		if (StringUtils.isEmpty(walletType)) {
//			resultInfo.setCode("-1");
//			resultInfo.setMessage("用户Id不能为空");
//			return resultInfo;
//		}
		
		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		PagingTool pagingTool=new PagingTool(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", Integer.parseInt(userId));
		if (!StringUtils.isEmpty(walletType)) {
			params.put("walletType", Integer.parseInt(walletType));
		}
		pagingTool.setParams(params);
		
		try {
			Object object=walletService.getWalletDetailBack(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据用户Id和钱包类型获取钱包信息
	 * 1、一种类型的钱包每个用户只能拥有一个，因此这返回的不是列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getAddress")
	public Object getAddress(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户
		String walletType=request.getParameter("walletType"); //钱包类型
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		if (StringUtils.isEmpty(walletType)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包类型不能为空");
			return resultInfo;
		}
		
		try {
			Object object=walletService.getAddress(Integer.parseInt(userId),Integer.parseInt(walletType));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据用户Id返回moac钱包地址和管理员钱包的地址和秘钥
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getWalletInfo")
	public Object getWalletInfo(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String userId=request.getParameter("userId");  //用户
		if (StringUtils.isEmpty(userId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("用户Id不能为空");
			return resultInfo;
		}
		
		try {
			Object object=walletService.getWalletInfo(Integer.parseInt(userId));
			return object;
		} catch (Exception e) {
			logger.error("获取moac钱包异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 获取keystore文件
	 * @param request
	 * @return
	 */
	@RequestMapping("/wallet/getkeyStore")
	public Object getkeyStore(HttpServletRequest request) {
		ResultInfo resultInfo=new ResultInfo();
		String walletId=request.getParameter("walletId"); //钱包Id
		if (StringUtils.isEmpty(walletId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("钱包Id不能为空");
			return resultInfo;
		}
		
		try {
			Wallet wallet=walletService.getWallet(Integer.parseInt(walletId));
			resultInfo.setMessage("获取成功");
			resultInfo.setResult(wallet);
			return resultInfo;
		} catch (Exception e) {
			logger.error("获取钱包信息异常",e);
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
