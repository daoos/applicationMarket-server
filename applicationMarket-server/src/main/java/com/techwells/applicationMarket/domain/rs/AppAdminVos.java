package com.techwells.applicationMarket.domain.rs;

import java.util.Date;

/**
 * App和Admin的值对象
 * 
 * @author Administrator
 */
public class AppAdminVos {
	private Integer appId;
	private String appName;
	private Integer plateform;
	private Date groundTime;
	private String account;
	private Integer isRecommend;
	private Integer examinStatus;
	private Integer groundStatus;

	private Date publishDate; // 上架时间

	private Integer isMust; // 是否必备

	public Integer getIsMust() {
		return isMust;
	}

	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getPlateform() {
		return plateform;
	}

	public void setPlateform(Integer plateform) {
		this.plateform = plateform;
	}

	public Date getGroundTime() {
		return groundTime;
	}

	public void setGroundTime(Date groundTime) {
		this.groundTime = groundTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getExaminStatus() {
		return examinStatus;
	}

	public void setExaminStatus(Integer examinStatus) {
		this.examinStatus = examinStatus;
	}

	public Integer getGroundStatus() {
		return groundStatus;
	}

	public void setGroundStatus(Integer groundStatus) {
		this.groundStatus = groundStatus;
	}

}
