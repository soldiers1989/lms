package com.yniot.lms.db.pojo;

import java.math.BigDecimal;

public class RentTable {
    private Integer id;

    private BigDecimal price;

    private Integer laundryId;

    private Integer rentType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getLaundryId() {
        return laundryId;
    }

    public void setLaundryId(Integer laundryId) {
        this.laundryId = laundryId;
    }

    public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }
}