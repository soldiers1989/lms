package com.yniot.lms.db.entity;

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
@TableName("biz_wardrobe")
public class Wardrobe extends Model<Wardrobe> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime createTime;

    private Boolean deleted;

    private Boolean activated;

    private String qrCode;

    public String getWardrobeCode() {
        return wardrobeCode;
    }

    public void setWardrobeCode(String wardrobeCode) {
        this.wardrobeCode = wardrobeCode;
    }

    private String wardrobeCode;

    private Integer swVersion;

    private String description;

    private LocalDateTime modifyTime;

    private String address;

    private Integer laundryId;

    private Double latitude;

    private Double longitude;

    private Integer modifier;

    private Integer creator;

    private String laundryImgUrl;

    private String laundryName;

    private Integer avaCellNum;
    private Integer usedCellNum;
    private Integer totalCellNum;
    private Integer closedCellNum;

    public String getLaundryPhone() {
        return laundryPhone;
    }

    public void setLaundryPhone(String laundryPhone) {
        this.laundryPhone = laundryPhone;
    }

    private String laundryPhone;

    public Integer getActivatedCellNum() {
        return activatedCellNum;
    }

    public void setActivatedCellNum(Integer activatedCellNum) {
        this.activatedCellNum = activatedCellNum;
    }

    private Integer activatedCellNum;

    public Integer getUsedCellNum() {
        return usedCellNum;
    }

    public void setUsedCellNum(Integer usedCellNum) {
        this.usedCellNum = usedCellNum;
    }


    public Integer getTotalCellNum() {
        return totalCellNum;
    }

    public void setTotalCellNum(Integer totalCellNum) {
        this.totalCellNum = totalCellNum;
    }


    public Integer getClosedCellNum() {
        return closedCellNum;
    }

    public void setClosedCellNum(Integer closedCellNum) {
        this.closedCellNum = closedCellNum;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Integer getSwVersion() {
        return swVersion;
    }

    public void setSwVersion(Integer swVersion) {
        this.swVersion = swVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLaundryId() {
        return laundryId;
    }

    public void setLaundryId(Integer laundryId) {
        this.laundryId = laundryId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getLaundryImgUrl() {
        return laundryImgUrl;
    }

    public void setLaundryImgUrl(String laundryImgUrl) {
        this.laundryImgUrl = laundryImgUrl;
    }

    public String getLaundryName() {
        return laundryName;
    }

    public void setLaundryName(String laundryName) {
        this.laundryName = laundryName;
    }

    public Integer getAvaCellNum() {
        return avaCellNum;
    }

    public void setAvaCellNum(Integer avaCellNum) {
        this.avaCellNum = avaCellNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Wardrobe{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", deleted=" + deleted +
                ", activated=" + activated +
                ", qrCode=" + qrCode +
                ", swVersion=" + swVersion +
                ", description=" + description +
                ", modifyTime=" + modifyTime +
                ", address=" + address +
                ", wardrobeCode=" + wardrobeCode +
                ", usedCellNum=" + usedCellNum +
                ", laundryId=" + laundryId +
                ", totalCellNum=" + totalCellNum +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", modifier=" + modifier +
                ", creator=" + creator +
                ", laundryImgUrl=" + laundryImgUrl +
                ", laundryName=" + laundryName +
                ", avaCellNum=" + avaCellNum +
                "}";
    }
}
