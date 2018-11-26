package com.yniot.lms.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderCost {
    private Integer id;

    private Date generateTime;

    private Date confirmTime;

    private Integer expiredInMin;

    private Date expiredTime;

    private Boolean confirmed;

    private Integer discount;

    private BigDecimal extCost;

    private Boolean asap;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Date generateTime) {
        this.generateTime = generateTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getExpiredInMin() {
        return expiredInMin;
    }

    public void setExpiredInMin(Integer expiredInMin) {
        this.expiredInMin = expiredInMin;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
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
}