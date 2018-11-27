package com.yniot.lms.enums;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 10:00
 **/
public enum OrderStateEnum {


    COMMITTED(10, "订单提交"),

    //订单取消
    CANCELED(30, "已取消"),
    CANCELED_TIMEOUT(31, "超时取消"),
    CANCELED_CUSTOMER(32, "客户取消"),
    CANCELED_LAUNDRY(33, "商家取消"),

    ACCEPTED(40, "已接单"),

    WAITING(55, "等待确认金额"),

    COST_TIMEOUT(58, "付款超时,取消订单"),

    PAID(60, "已付款"),

    CLEANED(70, "已清洁"),

    FINISHED(100, "已完成");


    private Integer state;
    private String name;

    private OrderStateEnum(Integer state, String name) {
        this.state = state;
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public static OrderStateEnum getEnumByState(int state) {
        for (OrderStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

}
