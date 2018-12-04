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
 * @since 2018-12-04
 */
@TableName("biz_income_history")
public class IncomeHistory extends Model<IncomeHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private BigDecimal profit;

    private LocalDateTime createTime;

    private Integer ownerId;

    private Integer orderId;

    private Boolean deleted;

    private Integer laundryId;

    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getLaundryId() {
        return laundryId;
    }

    public void setLaundryId(Integer laundryId) {
        this.laundryId = laundryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IncomeHistory{" +
        "id=" + id +
        ", profit=" + profit +
        ", createTime=" + createTime +
        ", ownerId=" + ownerId +
        ", orderId=" + orderId +
        ", deleted=" + deleted +
        ", laundryId=" + laundryId +
        ", description=" + description +
        "}";
    }
}
