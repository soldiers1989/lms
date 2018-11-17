package com.yniot.lms.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Laundry {
    private Integer id;

    private String address;

    private String phone;

    private String qrCode;

    private Boolean deleted;

    private BigDecimal totalIncome;

    private Boolean asap;

    private Date modifyTime;

    private Date createTime;

    private String description;

    private Boolean contractSigned;

    private Boolean inBiz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Boolean getAsap() {
        return asap;
    }

    public void setAsap(Boolean asap) {
        this.asap = asap;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getContractSigned() {
        return contractSigned;
    }

    public void setContractSigned(Boolean contractSigned) {
        this.contractSigned = contractSigned;
    }

    public Boolean getInBiz() {
        return inBiz;
    }

    public void setInBiz(Boolean inBiz) {
        this.inBiz = inBiz;
    }
}