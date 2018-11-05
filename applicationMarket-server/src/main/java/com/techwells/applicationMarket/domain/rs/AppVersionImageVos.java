package com.techwells.applicationMarket.domain.rs;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.techwells.applicationMarket.domain.AppImage;

/**
 * 应用、版本、图片的值对象，用于应用详情
 * @author Administrator
 */
public class AppVersionImageVos {
	private Integer appId;  //应用Id
	private Integer versionId;  //版本Id
	private String appName;  //应用名称
	private String logo;   //图标
	private String size;   //大小
	private String introduction;  //应用简介
	private Long downloadCount;  //下载次数
	private Double downloadMoney;  //下载所需要的钱
	private List<AppImage> appImages;   //应用图片
	private String primaryImage;  //主图 一张
	private String[] versionFeatures;  //版本 特性
	private String features;   //版本特性的返回结果，用来接受
	private String versionNum;  //当前版本号
	private Date createDate;   //版本更新的时间
	private String developCompany;  //开发商
	private String moneyName;  //代币名称
	private Integer platform;  //平台信息
	private String downloadUrl;  //下载链接
	
	
	
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public Integer getPlatform() {
		return platform;
	}
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getVersionId() {
		return versionId;
	}
	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Long getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}
	public List<AppImage> getAppImages() {
		return appImages;
	}
	public void setAppImages(List<AppImage> appImages) {
		this.appImages = appImages;
	}
	public String getPrimaryImage() {
		return primaryImage;
	}
	public void setPrimaryImage(String primaryImage) {
		this.primaryImage = primaryImage;
	}
	public String[] getVersionFeatures() {
		return versionFeatures;
	}
	public void setVersionFeatures(String[] versionFeatures) {
		this.versionFeatures = versionFeatures;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public String getDevelopCompany() {
		return developCompany;
	}
	public void setDevelopCompany(String developCompany) {
		this.developCompany = developCompany;
	}
	public String getMoneyName() {
		return moneyName;
	}
	public void setMoneyName(String moneyName) {
		this.moneyName = moneyName;
	}
	public Double getDownloadMoney() {
		return downloadMoney;
	}
	public void setDownloadMoney(Double downloadMoney) {
		this.downloadMoney = downloadMoney;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "AppVersionImageVos [appId=" + appId + ", versionId="
				+ versionId + ", appName=" + appName + ", logo=" + logo
				+ ", size=" + size + ", introduction=" + introduction
				+ ", downloadCount=" + downloadCount + ", downloadMoney="
				+ downloadMoney + ", appImages=" + appImages
				+ ", primaryImage=" + primaryImage + ", versionFeatures="
				+ Arrays.toString(versionFeatures) + ", features=" + features
				+ ", versionNum=" + versionNum + ", createDate=" + createDate
				+ ", developCompany=" + developCompany + ", moneyName="
				+ moneyName + "]";
	}
	
}
