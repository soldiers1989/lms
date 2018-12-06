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
 * @since 2018-12-06
 */
@TableName("biz_goods_code")
public class GoodsCode extends Model<GoodsCode> {

    private static final long serialVersionUID = 1L;

    /**
     * 洗衣店用于标记衣物的编码
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer laundryId;

    private String uniqueCode;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    public Integer getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Integer orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    private Integer orderGoodsId;

    /**
     * 0.可用 1.使用中  1
     */
    private Boolean used;

    private Boolean deleted;

    private Integer orderId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLaundryId() {
        return laundryId;
    }

    public void setLaundryId(Integer laundryId) {
        this.laundryId = laundryId;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GoodsCode{" +
                "id=" + id +
                ", laundryId=" + laundryId +
                ", uniqueCode=" + uniqueCode +
                ", description=" + description +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", used=" + used +
                ", orderGoodsId=" + orderGoodsId +
                ", deleted=" + deleted +
                ", orderId=" + orderId +
                "}";
    }
}
