package com.techwells.applicationMarket.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

import com.techwells.applicationMarket.service.AddressService;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 地址的controller
 * @author Administrator
 *
 */
@RestController
public class AddressController {
	@Resource
	private AddressService addressService;
	
	/**
	 * 获取所有省份的信息
	 * @return
	 */
	@RequestMapping("/address/getProvinces")
	public Object getProvinces(){
		ResultInfo resultInfo=new ResultInfo();
		
		try {
			Object object=addressService.getProvinces();
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	/**
	 * 根据省份的编码获取市
	 * @param provinceCode  省份的编码
	 * @param request
	 * @return
	 */
	@RequestMapping("/address/getCities")
	public Object getCities(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String provinceCode=request.getParameter("provinceCode");
		if (StringUtils.isEmpty(provinceCode)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("省份编码不能为空");
			return resultInfo;
		}
		
		try {
			Object object=addressService.getCities(provinceCode);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}
	
	
	/**
	 * 根据市的编码获取区
	 * @param cityCode  市的编码
	 * @param request
	 * @return
	 */
	@RequestMapping("/address/getAreas")
	public Object getAreas(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String cityCode=request.getParameter("cityCode");
		
		if (StringUtils.isEmpty(cityCode)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("市的编码不能为空");
			return resultInfo;
		}
		
		//调用servce的方法
		try {
			Object object=addressService.getAreas(cityCode);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
