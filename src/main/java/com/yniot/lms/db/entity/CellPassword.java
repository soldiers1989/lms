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
 * @since 2018-12-07
 */
@TableName("biz_cell_password")
public class CellPassword extends Model<CellPassword> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getWardrobeId() {
        return wardrobeId;
    }

    public void setWardrobeId(Integer wardrobeId) {
        this.wardrobeId = wardrobeId;
    }

    private Integer wardrobeId;

    private Integer orderId;

    private Integer password;

    private Integer timeout;

    private LocalDateTime createTime;

    private LocalDateTime expireTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }


    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CellPassword{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", wardrobeId=" + wardrobeId +
                ", password=" + password +
                ", timeout=" + timeout +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                "}";
    }
}
