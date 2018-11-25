package com.yniot.lms.enums;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 10:00
 **/
public enum OrderStatusEnum {

    // 3.接单
    // 状态：0.创建订单  10.提交订单、待接单 15.已接单.待入柜 20.已接单、待取货
    // 30.已取货、待到店  40.已到店、待确认金额 50.已确认金额并付款、待清洁
    // 60.清洁中 70.完成清洁、待送回  80.已送出、待放回
    // 90.已放回、待取回  100.已取回、待评价
    // 110.已评价


    COMMITTED(10, "订单提交"),

    //订单取消
    CANCELED_TIMEOUT(31, "超时取消"),
    CANCELED_CUSTOMER(32, "客户取消"),
    CANCELED_LAUNDRY(33, "商家取消"),

    ACCEPTED(40, "已接单"),

    ARRIVED(50, "已到店"),

    CLEANED(60, "已清洁"),

    PUT(70, "已放回"),

    TOOK(90, "已取出"),

    FINISHED(100, "已完成");


    private Integer status;
    private String name;

    private OrderStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public static OrderStatusEnum getNameByStatus(int status) {
        for (OrderStatusEnum statusEnum : values()) {
            if (statusEnum.getStatus() == status) {
                return statusEnum;
            }
        }
        return null;
    }
}
