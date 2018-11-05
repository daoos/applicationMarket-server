package com.techwells.applicationMarket.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sun.util.logging.resources.logging;

import com.techwells.applicationMarket.domain.Banner;
import com.techwells.applicationMarket.service.BannerService;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

/**
 * 广告的controller
 * 
 * @author Administrator
 *
 */
@RestController
public class BannerController {
	@Resource
	private BannerService bannerService;
	private Logger logging = Logger.getLogger(BannerController.class);

	/**
	 * 添加广告
	 * 
	 * @param location
	 *            位置
	 * @param file
	 *            文件
	 * @param activated
	 *            状态
	 * @param appId
	 *            应用Id
	 * @param platform
	 *            平台 1 安卓 2 ios
	 * @return
	 */
	@RequestMapping("/banner/addBanner")
	public Object addBanner(Integer location, Integer appId, Integer platform,
			@RequestParam(value = "file", required = false) MultipartFile file,
			Integer activated) {
		ResultInfo resultInfo = new ResultInfo();

		// 校验参数

		if (StringUtils.isEmpty(location)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("位置不能为空");
			return resultInfo;
		}

		if (file == null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("图片不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(activated)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("状态不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(platform)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("平台不能为空");
			return resultInfo;
		}

		// 查看当前位置的广告是否存在
		try {
			Banner banner = bannerService.countBannerByPageAndLoaction(
					location, 1, platform);
			if (banner != null) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("该位置的广告已经存在，如果想要添加，请先删除或者禁用");
				return resultInfo;
			}
		} catch (Exception e1) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取数量异常");
			return resultInfo;
		}

		// 上传图片

		String fileName = System.currentTimeMillis()
				+ file.getOriginalFilename(); // 文件的名字
		String filePath = ApplicationMarketConstants.UPLOAD_PATH
				+ "banner-image/"; // 文件的上传路径

		// String filePath = ApplicationMarketConstants.UPLOAD_PATH
		// + "banner-image\\"; // 文件的上传路径

		String bannerUrl = ApplicationMarketConstants.UPLOAD_URL
				+ "banner-image/" + fileName; // 广告的url

		File targetFile = new File(filePath, fileName);

		// 递归创建文件夹
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs(); // 递归创建文件夹
		}

		// 保存图片
		try {
			file.transferTo(targetFile); // 保存图片
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("上传图片失败");
			return resultInfo;
		}

		// 封装数据
		Banner banner = new Banner();
		banner.setActivated(activated);
		banner.setCreatedDate(new Date());
		banner.setImageUrl(bannerUrl);
		banner.setAppId(appId);
		banner.setBannerLocation(location);
		try {
			Object object = bannerService.addBanner(banner);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("添加异常");
			return resultInfo;
		}
	}

	/**
	 * 删除
	 * 
	 * @param bannerId
	 *            id
	 * @return
	 */
	@RequestMapping("/banner/deleteBanner")
	public Object deleteBanner(String bannerId) {
		ResultInfo resultInfo = new ResultInfo();
		if (StringUtils.isEmpty(bannerId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("广告Id不能为空");
			return resultInfo;
		}

		try {
			Object object = bannerService.deleteBanner(Integer
					.parseInt(bannerId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除异常");
			return resultInfo;
		}

	}

	/**
	 * 获取广告的详细信息
	 * 
	 * @param bannerId
	 *            广告Id
	 * @return
	 */
	@RequestMapping("/banner/getBanner")
	public Object getBanner(Integer bannerId) {
		ResultInfo resultInfo = new ResultInfo();
		if (StringUtils.isEmpty(bannerId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("广告Id不能为空");
			return resultInfo;
		}

		try {
			Object object = bannerService.getBanner(bannerId);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}

	/**
	 * 修改广告信息
	 * 
	 * @param page
	 *            页面
	 * @param location
	 *            位置
	 * @param file
	 *            文件
	 * @param activated
	 *            状态
	 * @param bannerId
	 *            广告Id
	 * @param platform
	 *            平台
	 * @param appId
	 *            应用Id
	 * 
	 *            1、广告的图片没有限制 2、appId没有限制
	 * @return
	 */
	@RequestMapping("/banner/modifyBanner")
	public Object modifyBanner(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {

		String location = request.getParameter("location");
		String activated = request.getParameter("activated");
		String bannerId = request.getParameter("bannerId");
		String appId = request.getParameter("appId");
		String platform = request.getParameter("platform");
		
		ResultInfo resultInfo = new ResultInfo();

		// 校验参数
		if (StringUtils.isEmpty(bannerId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("广告Id不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(location)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("位置不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(activated)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("状态不能为空");
			return resultInfo;
		}

		// 查看当前位置的广告是否存在
		try {
			Banner banner = bannerService.countBannerByPageAndLoaction(
					Integer.parseInt(location), 1, Integer.parseInt(platform));

			// 如果此时位置已经存在并且不是这个广告，那么表示已经存在了，不能修改
			if (banner != null && !banner.getBannerId().equals(Integer.parseInt(bannerId))) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("该位置的广告已经存在，如果想要修改，请先删除或者禁用");
				return resultInfo;
			}
		} catch (Exception e1) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取数量异常");
			return resultInfo;
		}

		String bannerUrl = null;
		// 如果上传了图片
		if (file != null) {
			// 上传图片

			String fileName = System.currentTimeMillis()
					+ file.getOriginalFilename(); // 文件的名字
			String filePath = ApplicationMarketConstants.UPLOAD_PATH
					+ "banner-image/"; // 文件的上传路径

			// String filePath = ApplicationMarketConstants.UPLOAD_PATH
			// + "banner-image\\"; // 文件的上传路径

			bannerUrl = ApplicationMarketConstants.UPLOAD_URL + "banner-image/"
					+ fileName; // 广告的url

			File targetFile = new File(filePath, fileName);

			// 递归创建文件夹
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs(); // 递归创建文件夹
			}

			// 保存图片
			try {
				file.transferTo(targetFile); // 保存图片
			} catch (Exception e) {
				resultInfo.setCode("-1");
				resultInfo.setMessage("上传图片失败");
				return resultInfo;
			}
		}

		// 封装数据
		Banner banner = new Banner();
		banner.setActivated(Integer.parseInt(activated)); // 状态
		banner.setImageUrl(bannerUrl); // 广告图片 1、如果没有重新上传，那么为null
		banner.setBannerLocation(Integer.parseInt(location)); // 位置
		banner.setBannerId(Integer.parseInt(bannerId)); // 广告Id
		// 如果应用Id不为空，表示已经修改了应用
		if (!StringUtils.isEmpty(appId)) {
			banner.setAppId(Integer.parseInt(appId));
		}

		try {
			Object object = bannerService.modifyBanner(banner);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改异常");
			return resultInfo;
		}
	}

	/**
	 * 分页获取广告列表
	 * 
	 * @param pageNum
	 *            当前的页数
	 * @param pageSize
	 *            每页显示的数量
	 * @return
	 */
	@RequestMapping("/banner/getBannerList")
	public Object getBannerList(Integer pageNum, Integer pageSize,
			Integer platform) {
		ResultInfo resultInfo = new ResultInfo();

		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setMessage("当前页数不能为空");
			resultInfo.setCode("-1");
			return resultInfo;
		}

		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setMessage("每页显示的数量不能为空");
			resultInfo.setCode("-1");
			return resultInfo;
		}

		PagingTool pagingTool = new PagingTool(pageNum, pageSize);

		Map<String, Object> params = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(platform)) {
			params.put("platform", platform);
		}

		pagingTool.setParams(params);
		try {
			Object object = bannerService.getBannerList(pagingTool);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}

	}

}
