package com.yniot.lms.enums;

/**
 * @Auther: lane
 * @Date: 2018/11/26 09:17
 * @Description:
 * @Version 1.0.0
 */
public enum ShipmentEnum {
    WAITING(42, "待存放"),
    PUT_USER(45, "用户已存放"),
    TOOK_MAILMAN(50, "配送员已取走"),
    CLEANED(65, "衣物已清洁"),
    PUT_MAILMAN(70, "配送员已放回"),
    TOOK_USER(75, "用户已取回");
//    CANCELED(41, "已取消");

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
