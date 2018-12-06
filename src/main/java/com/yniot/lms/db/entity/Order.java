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

    public Integer getWardrobeId() {
        return wardrobeId;
    }

    public void setWardrobeId(Integer wardrobeId) {
        this.wardrobeId = wardrobeId;
    }

    private Integer wardrobeId;

    private String userOpenId;

    private Integer laundryId;

    private Integer state;

    /**
     * 备注
     */
    private String description;

    /**
     * 0.未接单 1.已接单
     */
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
                ", wardrobeId=" + wardrobeId +
                ", description=" + description +
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
