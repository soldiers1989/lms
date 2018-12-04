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
 * @since 2018-12-04
 */
@TableName("biz_goods_type")
public class GoodsType extends Model<GoodsType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    /**
     * 1.件  2.双 3.套
     */
    private Integer unitId;

    private Boolean deleted;

    private LocalDateTime modifyTime;

    private Integer catalogId;

    private String bannerImgUrl;

    private BigDecimal avgPrice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
        "id=" + id +
        ", name=" + name +
        ", description=" + description +
        ", unitId=" + unitId +
        ", deleted=" + deleted +
        ", modifyTime=" + modifyTime +
        ", catalogId=" + catalogId +
        ", bannerImgUrl=" + bannerImgUrl +
        ", avgPrice=" + avgPrice +
        "}";
    }
}
