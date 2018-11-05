package com.techwells.applicationMarket.domain;

import java.util.Date;
import java.util.List;

import com.techwells.applicationMarket.domain.rs.AppAndVersionVos;

public class RecommendType {
    private Integer recommendTypeId;

    private String title;

    private Integer count;

    private Integer sort;

    private Integer top;

    private Date topTime;

    private Integer deleted;

    private Integer activated;

    private Date createDate;

    private Date updateDate;
    
    private List<App> apps;  //应用详情
    


	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	public Integer getRecommendTypeId() {
        return recommendTypeId;
    }

    public void setRecommendTypeId(Integer recommendTypeId) {
        this.recommendTypeId = recommendTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Date getTopTime() {
        return topTime;
    }

    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}