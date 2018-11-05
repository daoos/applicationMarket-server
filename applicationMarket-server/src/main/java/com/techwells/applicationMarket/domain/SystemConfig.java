package com.techwells.applicationMarket.domain;

import java.util.Date;

public class SystemConfig {
    private Integer configId;  //

    private Integer isRansfer;   //是否可以转账

    private Integer isNeedSecret;  //是否需要

    private Integer isNeedPwd; 

    private String copyRight;

    private String companyPhone;

    private String companyAddress;

    private String moacAddress;

    private String moacSecret;

    private String swtcAddress;

    private String swtcSecret;

    private Integer activated;

    private Integer deleted;

    private Date createDate;

    private Date updateDate;

    private String aboutUs;

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Integer getIsRansfer() {
        return isRansfer;
    }

    public void setIsRansfer(Integer isRansfer) {
        this.isRansfer = isRansfer;
    }

    public Integer getIsNeedSecret() {
        return isNeedSecret;
    }

    public void setIsNeedSecret(Integer isNeedSecret) {
        this.isNeedSecret = isNeedSecret;
    }

    public Integer getIsNeedPwd() {
        return isNeedPwd;
    }

    public void setIsNeedPwd(Integer isNeedPwd) {
        this.isNeedPwd = isNeedPwd;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight == null ? null : copyRight.trim();
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone == null ? null : companyPhone.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getMoacAddress() {
        return moacAddress;
    }

    public void setMoacAddress(String moacAddress) {
        this.moacAddress = moacAddress == null ? null : moacAddress.trim();
    }

    public String getMoacSecret() {
        return moacSecret;
    }

    public void setMoacSecret(String moacSecret) {
        this.moacSecret = moacSecret == null ? null : moacSecret.trim();
    }

    public String getSwtcAddress() {
        return swtcAddress;
    }

    public void setSwtcAddress(String swtcAddress) {
        this.swtcAddress = swtcAddress == null ? null : swtcAddress.trim();
    }

    public String getSwtcSecret() {
        return swtcSecret;
    }

    public void setSwtcSecret(String swtcSecret) {
        this.swtcSecret = swtcSecret == null ? null : swtcSecret.trim();
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
        this.activated = activated;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs == null ? null : aboutUs.trim();
    }
}