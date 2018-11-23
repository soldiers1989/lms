package com.yniot.lms.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
@TableName("biz_order_goods")
public class OrderGoods {
    private Integer id;

    private Integer orderId;

    private Integer cellId;

    private Boolean deleted;

    private Integer wardrobeId;

    private Date createTime;

    private Integer launderType;

    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCellId() {
        return cellId;
    }

    public void setCellId(Integer cellId) {
        this.cellId = cellId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getWardrobeId() {
        return wardrobeId;
    }

    public void setWardrobeId(Integer wardrobeId) {
        this.wardrobeId = wardrobeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLaunderType() {
        return launderType;
    }

    public void setLaunderType(Integer launderType) {
        this.launderType = launderType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}