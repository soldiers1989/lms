package com.yniot.lms.db.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wanggl
 * @since 2018-12-14
 */
@TableName("biz_income")
public class Income extends Model<Income> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer dividePercent;

    private BigDecimal actIncome;

    private Integer laundryId;

    private Integer ownerId;

    private Integer ownerOpenId;

    private LocalDateTime createTime;

    private LocalDateTime withdrewTime;
    /**
     * 系统收入
     */
    private BigDecimal sysIncome;

    private BigDecimal actCost;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 是否已结算
     */
    private Boolean withdrew;

    private String laundryName;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getWithdrewTime() {
        return withdrewTime;
    }

    public void setWithdrewTime(LocalDateTime withdrewTime) {
        this.withdrewTime = withdrewTime;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDividePercent() {
        return dividePercent;
    }

    public void setDividePercent(Integer dividePercent) {
        this.dividePercent = dividePercent;
    }

    public BigDecimal getActIncome() {
        return actIncome;
    }

    public void setActIncome(BigDecimal actIncome) {
        this.actIncome = actIncome;
    }

    public Integer getLaundryId() {
        return laundryId;
    }

    public void setLaundryId(Integer laundryId) {
        this.laundryId = laundryId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOwnerOpenId() {
        return ownerOpenId;
    }

    public void setOwnerOpenId(Integer ownerOpenId) {
        this.ownerOpenId = ownerOpenId;
    }

    public BigDecimal getSysIncome() {
        return sysIncome;
    }

    public void setSysIncome(BigDecimal sysIncome) {
        this.sysIncome = sysIncome;
    }

    public BigDecimal getActCost() {
        return actCost;
    }

    public void setActCost(BigDecimal actCost) {
        this.actCost = actCost;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Boolean getWithdrew() {
        return withdrew;
    }

    public void setWithdrew(Boolean withdrew) {
        this.withdrew = withdrew;
    }

    public String getLaundryName() {
        return laundryName;
    }

    public void setLaundryName(String laundryName) {
        this.laundryName = laundryName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", dividePercent=" + dividePercent +
                ", actIncome=" + actIncome +
                ", laundryId=" + laundryId +
                ", ownerId=" + ownerId +
                ", ownerOpenId=" + ownerOpenId +
                ", sysIncome=" + sysIncome +
                ", actCost=" + actCost +
                ", customerId=" + customerId +
                ", orderCode=" + orderCode +
                ", withdrew=" + withdrew +
                ", createTime=" + createTime +
                ", withdrewTime=" + withdrewTime +
                ", laundryName=" + laundryName +
                "}";
    }
}
