package com.yniot.lms.exportEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class StationImportEntity {
    private Integer id;
    @Excel(name = "基站名称", width = 30, isImportField = "true_st")
    private String stationName;
    @Excel(name = "基站编码", width = 30, isImportField = "true_st")
    private String stationNo;

    private String stationCoordinate;

    private Date createTime;

    private Integer creator;

    private Date modifyTime;

    private Integer modifier;

    private Integer status;
    @Excel(name = "备注", width = 30, isImportField = "true_st")
    private String remark;
    @Excel(name = "地址", width = 30, isImportField = "true_st")
    private String stationAddress;

    private Integer administrator;
    @Excel(name = "站点类型", width = 30, isImportField = "true_st")
    private String stationType;

    private Integer startVoltage;

    private String customerNo;

    private String unitNo;

    private String operatorNo;

    private Boolean deleted;
    @Excel(name = "经度", width = 30, isImportField = "true_st")
    private String stationLongitude;
    @Excel(name = "纬度", width = 30, isImportField = "true_st")
    private String stationLatitude;

    private Integer unitId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo == null ? null : stationNo.trim();
    }

    public String getStationCoordinate() {
        return stationCoordinate;
    }

    public void setStationCoordinate(String stationCoordinate) {
        this.stationCoordinate = stationCoordinate == null ? null : stationCoordinate.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress == null ? null : stationAddress.trim();
    }

    public Integer getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Integer administrator) {
        this.administrator = administrator;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public Integer getStartVoltage() {
        return startVoltage;
    }

    public void setStartVoltage(Integer startVoltage) {
        this.startVoltage = startVoltage;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo == null ? null : unitNo.trim();
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo == null ? null : operatorNo.trim();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getStationLatitude() {
        return stationLatitude;
    }

    public void setStationLatitude(String stationLatitude) {
        this.stationLatitude = stationLatitude == null ? null : stationLatitude.trim();
    }

    public String getStationLongitude() {
        return stationLongitude;
    }

    public void setStationLongitude(String stationLongitude) {
        this.stationLongitude = stationLongitude == null ? null : stationLongitude.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}