package com.yniot.lms.db.pojo;

public class LaunderType {
    private Integer id;

    private String launderName;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLaunderName() {
        return launderName;
    }

    public void setLaunderName(String launderName) {
        this.launderName = launderName == null ? null : launderName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}