package com.techwells.applicationMarket.domain;

import java.util.Date;

public class App {
    private Integer appId;

    private Integer typeId;

    private Integer userId;

    private String privacy;

    private String name;

    private String logo;

    private Integer plateform;

    private Integer examineStatus;

    private Date examinDate;

    private String examinPersonName;

    private String examineExplain;

    private Integer publishType;

    private Date publishDate;

    private String publishDay;

    private String publishHour;

    private Integer groundStatus;

    private Date groundTime;

    private Integer isRecommend;

    private Integer recommendTypeId;

    private String personalRecommend;

    private String license;

    private String introduction;

    private Integer supportLanguage;

    private Integer tariffType;

    private Double downloadMoney;

    private Long downloadCount;

    private Long downloadCountAdd;

    private String developCompany;

    private Integer moneyName;

    private Double score;

    private Integer top;

    private Date topTime;

    private Integer leve;

    private Integer isMust;

    private Integer deleted;

    private Integer activated;

    private Date createDate;

    private Date updateDate;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy == null ? null : privacy.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public Integer getPlateform() {
        return plateform;
    }

    public void setPlateform(Integer plateform) {
        this.plateform = plateform;
    }

    public Integer getExamineStatus() {
        return examineStatus;
    }

    public void setExamineStatus(Integer examineStatus) {
        this.examineStatus = examineStatus;
    }

    public Date getExaminDate() {
        return examinDate;
    }

    public void setExaminDate(Date examinDate) {
        this.examinDate = examinDate;
    }

    public String getExaminPersonName() {
        return examinPersonName;
    }

    public void setExaminPersonName(String examinPersonName) {
        this.examinPersonName = examinPersonName == null ? null : examinPersonName.trim();
    }

    public String getExamineExplain() {
        return examineExplain;
    }

    public void setExamineExplain(String examineExplain) {
        this.examineExplain = examineExplain == null ? null : examineExplain.trim();
    }

    public Integer getPublishType() {
        return publishType;
    }

    public void setPublishType(Integer publishType) {
        this.publishType = publishType;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishDay() {
        return publishDay;
    }

    public void setPublishDay(String publishDay) {
        this.publishDay = publishDay == null ? null : publishDay.trim();
    }

    public String getPublishHour() {
        return publishHour;
    }

    public void setPublishHour(String publishHour) {
        this.publishHour = publishHour == null ? null : publishHour.trim();
    }

    public Integer getGroundStatus() {
        return groundStatus;
    }

    public void setGroundStatus(Integer groundStatus) {
        this.groundStatus = groundStatus;
    }

    public Date getGroundTime() {
        return groundTime;
    }

    public void setGroundTime(Date groundTime) {
        this.groundTime = groundTime;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getRecommendTypeId() {
        return recommendTypeId;
    }

    public void setRecommendTypeId(Integer recommendTypeId) {
        this.recommendTypeId = recommendTypeId;
    }

    public String getPersonalRecommend() {
        return personalRecommend;
    }

    public void setPersonalRecommend(String personalRecommend) {
        this.personalRecommend = personalRecommend == null ? null : personalRecommend.trim();
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Integer getSupportLanguage() {
        return supportLanguage;
    }

    public void setSupportLanguage(Integer supportLanguage) {
        this.supportLanguage = supportLanguage;
    }

    public Integer getTariffType() {
        return tariffType;
    }

    public void setTariffType(Integer tariffType) {
        this.tariffType = tariffType;
    }

    public Double getDownloadMoney() {
        return downloadMoney;
    }

    public void setDownloadMoney(Double downloadMoney) {
        this.downloadMoney = downloadMoney;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Long getDownloadCountAdd() {
        return downloadCountAdd;
    }

    public void setDownloadCountAdd(Long downloadCountAdd) {
        this.downloadCountAdd = downloadCountAdd;
    }

    public String getDevelopCompany() {
        return developCompany;
    }

    public void setDevelopCompany(String developCompany) {
        this.developCompany = developCompany == null ? null : developCompany.trim();
    }

    public Integer getMoneyName() {
        return moneyName;
    }

    public void setMoneyName(Integer moneyName) {
        this.moneyName = moneyName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    public Integer getLeve() {
        return leve;
    }

    public void setLeve(Integer leve) {
        this.leve = leve;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
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