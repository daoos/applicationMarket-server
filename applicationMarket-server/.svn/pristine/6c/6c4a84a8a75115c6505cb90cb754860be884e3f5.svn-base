package com.techwells.applicationMarket.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.AppMapper;
import com.techwells.applicationMarket.dao.BannerMapper;
import com.techwells.applicationMarket.domain.App;
import com.techwells.applicationMarket.domain.Banner;
import com.techwells.applicationMarket.service.BannerService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class BannerServiceImpl implements BannerService {

	@Resource
	private BannerMapper bannerMapper;

	@Resource
	private AppMapper appMapper;
	
	@Override
	public Object addBanner(Banner banner) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		int count = bannerMapper.insertSelective(banner);
		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("插入失败");
			return resultInfo;
		}

		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@Override
	public Object deleteBanner(Integer bannerId) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		int count = bannerMapper.deleteByPrimaryKey(bannerId);

		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}

		resultInfo.setMessage("删除成功");
		return resultInfo;
	}

	@Override
	public Object getBanner(Integer bannerId) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		Banner banner = bannerMapper.selectByPrimaryKey(bannerId);
		
		if (banner == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该广告不存在");
			return resultInfo;
		}
		
		//获取广告的平台信息
		App app=appMapper.selectByPrimaryKey(banner.getAppId());  //获取应用信息
		
		if (app==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("对应的应用不存在");
			return resultInfo;
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("banner", banner);
		map.put("app", app);
		
		resultInfo.setResult(map);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	@Override
	public Object getBannerList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		List<Banner> banners=bannerMapper.selectBannerList(pagingTool);
		int count=bannerMapper.countTotal(pagingTool);
		resultInfo.setResult(banners);
		resultInfo.setTotal(count);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	@Override
	public Object modifyBanner(Banner banner) throws Exception {
		ResultInfo resultInfo = new ResultInfo();
		int count = bannerMapper.updateByPrimaryKeySelective(banner);

		if (count == 0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	@Override
	public Banner countBannerByPageAndLoaction(Integer location,
			Integer activated,Integer platform) throws Exception {
		// 添加之前应该查看同一个页面的同一个位置的广告是否已经存在这个广告，如果存在，不能添加
		Banner banner = bannerMapper.countBannerByPageAndLocation(location, activated,platform);
		return banner;
	}

}
