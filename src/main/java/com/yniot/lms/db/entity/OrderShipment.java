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
 * @since 2018-12-05
 */
@TableName("biz_order_shipment")
public class OrderShipment extends Model<OrderShipment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    private Integer state;

    private LocalDateTime modifyTime;

    private Integer modifier;

    private String address;

    private Integer wardrobeId;

    private String phone;

    private Integer password;

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public LocalDateTime getPswExpireTime() {
        return pswExpireTime;
    }

    public void setPswExpireTime(LocalDateTime pswExpireTime) {
        this.pswExpireTime = pswExpireTime;
    }

    private LocalDateTime pswExpireTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getWardrobeId() {
        return wardrobeId;
    }

    public void setWardrobeId(Integer wardrobeId) {
        this.wardrobeId = wardrobeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderShipment{" +
                "id=" + id +
                ", state=" + state +
                ", modifyTime=" + modifyTime +
                ", createTime=" + createTime +
                ", password=" + password +
                ", pswExpireTime=" + pswExpireTime +
                ", modifier=" + modifier +
                ", address=" + address +
                ", wardrobeId=" + wardrobeId +
                ", phone=" + phone +
                "}";
    }
}
