package com.techwells.applicationMarket.domain;

import java.util.Date;

public class AppVersion {
    private Integer appVersionId;

    private String logo;

    private Integer appId;

    private String packageName;

    private String versionName;

    private String versionNumber;

    private String versionFeatures;

    private String supportSys;

    private String size;

    private Integer isHistoryVersion;

    private String introduction;

    private String downloadUrl;

    private Integer deleted;

    private Integer activated;

    private Date createDate;

    private Date updateDate;

    public Integer getAppVersionId() {
        return appVersionId;
    }

    public void setAppVersionId(Integer appVersionId) {
        this.appVersionId = appVersionId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber == null ? null : versionNumber.trim();
    }

    public String getVersionFeatures() {
        return versionFeatures;
    }

    public void setVersionFeatures(String versionFeatures) {
        this.versionFeatures = versionFeatures == null ? null : versionFeatures.trim();
    }

    public String getSupportSys() {
        return supportSys;
    }

    public void setSupportSys(String supportSys) {
        this.supportSys = supportSys == null ? null : supportSys.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public Integer getIsHistoryVersion() {
        return isHistoryVersion;
    }

    public void setIsHistoryVersion(Integer isHistoryVersion) {
        this.isHistoryVersion = isHistoryVersion;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
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