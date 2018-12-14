package com.yniot.lms.db.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
@TableName("biz_laundry")
public class Laundry extends Model<Laundry> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String address;

    private String phone;

    private String qrCode;

    private Boolean deleted;

    private BigDecimal totalIncome;

    private Boolean asap;

    private LocalDateTime modifyTime;

    private LocalDateTime createTime;

    private String description;

    private Boolean contractSigned;

    private Boolean inBiz;

    private Boolean autoAccept;

    private String name;

    private String imgUrl;

    private String openId;

    public Integer getWardrobeNum() {
        return wardrobeNum;
    }

    public void setWardrobeNum(Integer wardrobeNum) {
        this.wardrobeNum = wardrobeNum;
    }

    private Integer wardrobeNum;

    public Integer getDividePercent() {
        return dividePercent;
    }

    public void setDividePercent(Integer dividePercent) {
        this.dividePercent = dividePercent;
    }

    public Integer getDivideType() {
        return divideType;
    }

    public void setDivideType(Integer divideType) {
        this.divideType = divideType;
    }

    private Integer dividePercent;
    private Integer divideType;

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
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getAutoAccept() {
        return autoAccept;
    }

    public void setAutoAccept(Boolean autoAccept) {
        this.autoAccept = autoAccept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Laundry{" +
                "id=" + id +
                ", address=" + address +
                ", phone=" + phone +
                ", qrCode=" + qrCode +
                ", deleted=" + deleted +
                ", totalIncome=" + totalIncome +
                ", asap=" + asap +
                ", modifyTime=" + modifyTime +
                ", dividePercent=" + dividePercent +
                ", divideType=" + divideType +
                ", createTime=" + createTime +
                ", wardrobeNum=" + wardrobeNum +
                ", description=" + description +
                ", contractSigned=" + contractSigned +
                ", inBiz=" + inBiz +
                ", autoAccept=" + autoAccept +
                ", name=" + name +
                ", imgUrl=" + imgUrl +
                ", openId=" + openId +
                "}";
    }
}
