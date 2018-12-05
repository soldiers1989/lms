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
 * @since 2018-12-05
 */
@TableName("biz_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String code;

    private Integer userId;

    private String userOpenId;

    private Integer laundryId;

    /**
     * 状态：0.创建订单  10.提交订单、未入柜 20.已入柜、待取货  30.已取货、待到店  40.已到店、待确认金额 50.已确认金额并付款、待清洁  60.清洁中 70.完成清洁、待送回  80.已送出、待放回  90.已放回、待取回  100.已取回、待评价 110.已评价（完成订单）
     */
    private Integer state;

    /**
     * 备注
     */
    private String description;

    /**
     * 0.未接单 1.已接单
     */
    private Boolean accepted;

    private Boolean canceled;

    private Boolean expired;

    private Integer expireInMin;

    private LocalDateTime createTime;

    private LocalDateTime commitTime;

    private LocalDateTime acceptedTime;

    private LocalDateTime canceledTime;

    private LocalDateTime expiredTime;

    private LocalDateTime finishedTime;

    private Integer canceledBy;

    private Boolean deleted;

    private Integer count;

    private String laundryOpenId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public Integer getLaundryId() {
        return laundryId;
    }

    public void setLaundryId(Integer laundryId) {
        this.laundryId = laundryId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Integer getExpireInMin() {
        return expireInMin;
    }

    public void setExpireInMin(Integer expireInMin) {
        this.expireInMin = expireInMin;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(LocalDateTime commitTime) {
        this.commitTime = commitTime;
    }

    public LocalDateTime getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(LocalDateTime acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    public LocalDateTime getCanceledTime() {
        return canceledTime;
    }

    public void setCanceledTime(LocalDateTime canceledTime) {
        this.canceledTime = canceledTime;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public LocalDateTime getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(LocalDateTime finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Integer getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(Integer canceledBy) {
        this.canceledBy = canceledBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLaundryOpenId() {
        return laundryOpenId;
    }

    public void setLaundryOpenId(String laundryOpenId) {
        this.laundryOpenId = laundryOpenId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Order{" +
        "id=" + id +
        ", code=" + code +
        ", userId=" + userId +
        ", userOpenId=" + userOpenId +
        ", laundryId=" + laundryId +
        ", state=" + state +
        ", description=" + description +
        ", accepted=" + accepted +
        ", canceled=" + canceled +
        ", expired=" + expired +
        ", expireInMin=" + expireInMin +
        ", createTime=" + createTime +
        ", commitTime=" + commitTime +
        ", acceptedTime=" + acceptedTime +
        ", canceledTime=" + canceledTime +
        ", expiredTime=" + expiredTime +
        ", finishedTime=" + finishedTime +
        ", canceledBy=" + canceledBy +
        ", deleted=" + deleted +
        ", count=" + count +
        ", laundryOpenId=" + laundryOpenId +
        "}";
    }
}
