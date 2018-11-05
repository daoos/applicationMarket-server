package com.techwells.applicationMarket.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AreaMapper;
import com.techwells.applicationMarket.dao.CityMapper;
import com.techwells.applicationMarket.dao.ProvincesMapper;
import com.techwells.applicationMarket.domain.Area;
import com.techwells.applicationMarket.domain.City;
import com.techwells.applicationMarket.domain.Provinces;
import com.techwells.applicationMarket.service.AddressService;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Resource
	private ProvincesMapper  provinceMapper;
	
	@Resource
	private CityMapper cityMapper;
	
	@Resource
	private AreaMapper areaMapper;
		
	@Override
	public Object getProvinces() throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<Provinces> provinces=new ArrayList<Provinces>();
		provinces=provinceMapper.selectProvincesList();
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(provinces);
		resultInfo.setTotal(provinces.size());
		return resultInfo;
	}

	@Override
	public Object getCities(String provinceCode) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<City> cities=new ArrayList<City>();
		cities=cityMapper.selectCityList(provinceCode);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(cities);
		resultInfo.setTotal(cities.size());
		return resultInfo;
	}

	@Override
	public Object getAreas(String cityCode) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<Area> areas=areaMapper.selectAreaList(cityCode);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(areas);
		resultInfo.setTotal(areas.size());
		return resultInfo;
	}

}
