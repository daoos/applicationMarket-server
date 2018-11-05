package com.techwells.applicationMarket.domain;

import java.util.Date;

public class ReportImage {
    private Integer reportImageId;

    private String imageUrl;

    private Integer reportId;

    private Integer deleted;

    private Integer activated;

    private Date createDate;

    private Date updateDate;

    public Integer getReportImageId() {
        return reportImageId;
    }

    public void setReportImageId(Integer reportImageId) {
        this.reportImageId = reportImageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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