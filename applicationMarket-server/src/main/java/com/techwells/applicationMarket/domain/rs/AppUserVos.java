package com.techwells.applicationMarket.domain.rs;

import java.util.Date;

/**
 * app、user表的值对象，用于保存查询后台应用审核的页面
 * @author 陈加兵
 *
 */
public class AppUserVos {
	private Integer appId;  //应用Id
	private String userName;   //用户账号
	private String realName;   //用户姓名
	private String address;   //地址
	private Date authDate;    //请求时间
	private Integer examinStatus;  //审核状态
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getAuthDate() {
		return authDate;
	}
	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}
	public Integer getExaminStatus() {
		return examinStatus;
	}
	public void setExaminStatus(Integer examinStatus) {
		this.examinStatus = examinStatus;
	}
	
}
