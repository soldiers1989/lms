package com.yniot.lms.db.entity;

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
 * @since 2018-12-04
 */
@TableName("biz_order_state_history")
public class OrderStateHistory extends Model<OrderStateHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private Integer state;

    private Integer modifier;

    private LocalDateTime createTime;

    private Boolean deleted;

    private Integer nextOperator;


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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
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

    public Integer getNextOperator() {
        return nextOperator;
    }

    public void setNextOperator(Integer nextOperator) {
        this.nextOperator = nextOperator;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderStateHistory{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", state=" + state +
        ", modifier=" + modifier +
        ", createTime=" + createTime +
        ", deleted=" + deleted +
        ", nextOperator=" + nextOperator +
        "}";
    }
}
