package com.techwells.applicationMarket.domain.rs;

import java.util.Date;

import com.techwells.applicationMarket.domain.Admin;

public class NoticeAdmin{
	
	private String account;
	private String title;
	private Integer noticeId;
	private Date createDate;
	@Override
	public String toString() {
		return "NoticeAdmin [account=" + account + ", title=" + title
				+ ", noticeId=" + noticeId + ", createDate=" + createDate + "]";
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
