package com.techwells.applicationMarket.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AddressService {
	/**
	 * 获取所有的省的列表
	 * @return
	 * @throws Exception
	 */
	Object getProvinces()throws Exception;
	
	/**
	 * 根据省份的编码查找所有的市
	 * @param provinceCode  省份的编码
	 * @return
	 * @throws Exception
	 */
	Object getCities(String provinceCode)throws Exception;
	
	/**
	 * 根据市区的代码查找所有的区
	 * @param cityCode  城市的编码
	 * @return
	 * @throws Exception
	 */
	Object getAreas(String cityCode)throws Exception;
}
