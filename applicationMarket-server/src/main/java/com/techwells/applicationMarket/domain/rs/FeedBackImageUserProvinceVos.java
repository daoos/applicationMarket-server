package com.techwells.applicationMarket.domain.rs;

import java.util.Date;
import java.util.Set;

import com.techwells.applicationMarket.domain.FeedBackImage;

/**
 * 反馈的联合查询的值对象
 * @author Administrator
 *
 */
public class FeedBackImageUserProvinceVos {
	private String account;
	private String realName;
	private String mobile;
	private String address;
	private Date feedBackDate;  //反馈时间
	private String content;  //内容
	private Set<FeedBackImage> images;  //反馈的图片
	private Integer feedBackId;  //Id
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getFeedBackDate() {
		return feedBackDate;
	}
	public void setFeedBackDate(Date feedBackDate) {
		this.feedBackDate = feedBackDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Set<FeedBackImage> getImages() {
		return images;
	}
	public void setImages(Set<FeedBackImage> images) {
		this.images = images;
	}
	public Integer getFeedBackId() {
		return feedBackId;
	}
	public void setFeedBackId(Integer feedBackId) {
		this.feedBackId = feedBackId;
	}
	
}
