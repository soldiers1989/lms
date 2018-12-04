package com.yniot.lms.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("biz_cell_storage_history")
public class CellStorageHistory {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private String goodsUniqueCode;

    private Integer cellId;

    private Date createTime;

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

    public String getGoodsUniqueCode() {
        return goodsUniqueCode;
    }

    public void setGoodsUniqueCode(String goodsUniqueCode) {
        this.goodsUniqueCode = goodsUniqueCode == null ? null : goodsUniqueCode.trim();
    }

    public Integer getCellId() {
        return cellId;
    }

    public void setCellId(Integer cellId) {
        this.cellId = cellId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}