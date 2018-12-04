package com.yniot.lms.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
@TableName("biz_order_goods")
public class OrderGoods {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private Integer storageCellId;

    private Integer putCellId;

    private Boolean deleted;

    private Integer wardrobeId;

    private Date createTime;

    private Integer launderType;

    private Integer count;

    private String code;

    private Integer state;

    private Boolean canceled;

    private Integer goodsId;

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

    public Integer getStorageCellId() {
        return storageCellId;
    }

    public void setStorageCellId(Integer storageCellId) {
        this.storageCellId = storageCellId;
    }

    public Integer getPutCellId() {
        return putCellId;
    }

    public void setPutCellId(Integer putCellId) {
        this.putCellId = putCellId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
}