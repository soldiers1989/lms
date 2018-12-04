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
 * @since 2018-12-04
 */
@TableName("biz_cell")
public class Cell extends Model<Cell> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer cellNo;

    private Boolean deleted;

    private Boolean activated;

    private Integer wardrobeId;

    private Boolean empty;

    private LocalDateTime createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCellNo() {
        return cellNo;
    }

    public void setCellNo(Integer cellNo) {
        this.cellNo = cellNo;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Integer getWardrobeId() {
        return wardrobeId;
    }

    public void setWardrobeId(Integer wardrobeId) {
        this.wardrobeId = wardrobeId;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Cell{" +
        "id=" + id +
        ", cellNo=" + cellNo +
        ", deleted=" + deleted +
        ", activated=" + activated +
        ", wardrobeId=" + wardrobeId +
        ", empty=" + empty +
        ", createTime=" + createTime +
        "}";
    }
}
