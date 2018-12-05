package com.yniot.lms.db.entity;

import java.math.BigDecimal;
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
@TableName("biz_order_goods")
public class OrderGoods extends Model<OrderGoods> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    /**
     * 用户存放的格子id
     */
    private Integer storageCellId;

    /**
     * 配送员存放的格子id
     */
    private Integer putCellId;

    private Boolean deleted;

    private Integer wardrobeId;

    private LocalDateTime createTime;

    private Integer launderType;

    private Integer count;

    private String code;

    /**
     * 与订单状态一致
     */
    private Integer state;

    private Boolean canceled;

    private Integer goodsId;

    private BigDecimal estTotalCost;

    private BigDecimal actTotalCost;

    private String description;

    private BigDecimal extCost;

    private BigDecimal estUnitPrice;

    private BigDecimal actUnitPrice;

    private String name;

    private String imgUrl;


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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
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
        this.code = code;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getExtCost() {
        return extCost;
    }

    public void setExtCost(BigDecimal extCost) {
        this.extCost = extCost;
    }

    public BigDecimal getEstUnitPrice() {
        return estUnitPrice;
    }

    public void setEstUnitPrice(BigDecimal estUnitPrice) {
        this.estUnitPrice = estUnitPrice;
    }

    public BigDecimal getActUnitPrice() {
        return actUnitPrice;
    }

    public void setActUnitPrice(BigDecimal actUnitPrice) {
        this.actUnitPrice = actUnitPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderGoods{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", storageCellId=" + storageCellId +
        ", putCellId=" + putCellId +
        ", deleted=" + deleted +
        ", wardrobeId=" + wardrobeId +
        ", createTime=" + createTime +
        ", launderType=" + launderType +
        ", count=" + count +
        ", code=" + code +
        ", state=" + state +
        ", canceled=" + canceled +
        ", goodsId=" + goodsId +
        ", estTotalCost=" + estTotalCost +
        ", actTotalCost=" + actTotalCost +
        ", description=" + description +
        ", extCost=" + extCost +
        ", estUnitPrice=" + estUnitPrice +
        ", actUnitPrice=" + actUnitPrice +
        ", name=" + name +
        ", imgUrl=" + imgUrl +
        "}";
    }
}
