package com.yniot.lms.db.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanggl
 * @since 2018-12-05
 */
@TableName("biz_order_cost")
public class OrderCost extends Model<OrderCost> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    private LocalDateTime generateTime;

    private LocalDateTime confirmTime;

    private Integer expiredInMin;

    private LocalDateTime expiredTime;

    private Boolean confirmed;

    private Integer discount;

    private BigDecimal extCost;

    private Boolean asap;

    private BigDecimal estTotalCost;

    private BigDecimal actTotalCost;

    private BigDecimal actPaidCost;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(LocalDateTime generateTime) {
        this.generateTime = generateTime;
    }

    public LocalDateTime getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(LocalDateTime confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getExpiredInMin() {
        return expiredInMin;
    }

    public void setExpiredInMin(Integer expiredInMin) {
        this.expiredInMin = expiredInMin;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public BigDecimal getExtCost() {
        return extCost;
    }

    public void setExtCost(BigDecimal extCost) {
        this.extCost = extCost;
    }

    public Boolean getAsap() {
        return asap;
    }

    public void setAsap(Boolean asap) {
        this.asap = asap;
    }

    public BigDecimal getEstTotalCost() {
        return estTotalCost;
    }

    public void setEstTotalCost(BigDecimal estTotalCost) {
        this.estTotalCost = estTotalCost;
    }

    public BigDecimal getActTotalCost() {
        return actTotalCost;
    }

    public void setActTotalCost(BigDecimal actTotalCost) {
        this.actTotalCost = actTotalCost;
    }

    public BigDecimal getActPaidCost() {
        return actPaidCost;
    }

    public void setActPaidCost(BigDecimal actPaidCost) {
        this.actPaidCost = actPaidCost;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderCost{" +
        "id=" + id +
        ", generateTime=" + generateTime +
        ", confirmTime=" + confirmTime +
        ", expiredInMin=" + expiredInMin +
        ", expiredTime=" + expiredTime +
        ", confirmed=" + confirmed +
        ", discount=" + discount +
        ", extCost=" + extCost +
        ", asap=" + asap +
        ", estTotalCost=" + estTotalCost +
        ", actTotalCost=" + actTotalCost +
        ", actPaidCost=" + actPaidCost +
        "}";
    }
}
