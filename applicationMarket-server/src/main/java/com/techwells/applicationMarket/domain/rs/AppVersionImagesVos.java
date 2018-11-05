package com.techwells.applicationMarket.domain.rs;

import java.util.List;

import com.techwells.applicationMarket.domain.App;
import com.techwells.applicationMarket.domain.AppImage;
import com.techwells.applicationMarket.domain.AppType;
import com.techwells.applicationMarket.domain.AppVersion;

/**
 * 应用详情的值对象
 * @author Administrator
 *
 */
public class AppVersionImagesVos {
	private App app;  //应用信息
	private AppVersion appVersion;  //最新的软件安装包
	private List<AppVersion> historyVersions;  //历史安装包
	private List<AppImage> images;  //图片
	private AppType appType; //软件分类
	
	public AppType getAppType() {
		return appType;
	}
	public void setAppType(AppType appType) {
		this.appType = appType;
	}
	public App getApp() {
		return app;
	}
	public void setApp(App app) {
		this.app = app;
	}
	public AppVersion getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}
	public List<AppVersion> getHistoryVersions() {
		return historyVersions;
	}
	public void setHistoryVersions(List<AppVersion> historyVersions) {
		this.historyVersions = historyVersions;
	}
	public List<AppImage> getImages() {
		return images;
	}
	public void setImages(List<AppImage> images) {
		this.images = images;
	}
	
}
