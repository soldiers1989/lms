package com.yniot.lms.enums;

/**
 * @Auther: lane
 * @Date: 2018/11/26 09:17
 * @Description:
 * @Version 1.0.0
 */
public enum ShipmentEnum {
    WAITING(0, "待存放"),
    PUT_USER(10, "已存放"),
    TOOK_MAILMAN(20, "已取走"),
    PUT_MAILMAN(30, "已放回"),
    TOOK_USER(40, "已取回"),
    CANCELED(41, "已取消");

    private Integer state;
    private String name;

    private ShipmentEnum(Integer state, String name) {
        this.state = state;
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
