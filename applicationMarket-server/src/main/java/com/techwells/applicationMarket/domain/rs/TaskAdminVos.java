package com.techwells.applicationMarket.domain.rs;

import java.util.Date;

/**
 * 任务和管理员的值对象
 * @author Administrator
 *
 */
public class TaskAdminVos {
	private Integer taskId;
	private String taskName;
	private Integer typeId;  //任务类别
	private String typeName;
	private Date publishDate;  //发布时间
	private String publishAccount;  //发布人账号
	private Integer poupLeve;  //弹窗级别
	private Integer status;  //状态
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishAccount() {
		return publishAccount;
	}
	public void setPublishAccount(String publishAccount) {
		this.publishAccount = publishAccount;
	}
	public Integer getPoupLeve() {
		return poupLeve;
	}
	public void setPoupLeve(Integer poupLeve) {
		this.poupLeve = poupLeve;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
