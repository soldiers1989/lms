package com.yniot.lms.enums;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 10:00
 **/
public enum OrderStateEnum {

    // 3.接单
    // 状态：0.创建订单  10.提交订单、待接单 15.已接单.待入柜 20.已接单、待取货
    // 30.已取货、待到店  40.已到店、待确认金额 50.已确认金额并付款、待清洁
    // 60.清洁中 70.完成清洁、待送回  80.已送出、待放回
    // 90.已放回、待取回  100.已取回、待评价
    // 110.已评价

    /*!!!!!!!!!!!!注意!,定义的顺序将会影响排序!!!!!!!!!!!!*/
    /*!!!!!!!!!!!!注意!,定义的顺序将会影响排序!!!!!!!!!!!!*/
    /*!!!!!!!!!!!!注意!,定义的顺序将会影响排序!!!!!!!!!!!!*/
    /*!!!!!!!!!!!!for循环中为倒序!!!!!!!!!!!!*/
    COMMITTED(10, "订单提交"),
    PUT_USER(20, "已存放"),

    //订单取消
    CANCELED(30, "已取消"),
    CANCELED_TIMEOUT(31, "超时取消"),
    CANCELED_CUSTOMER(32, "客户取消"),
    CANCELED_LAUNDRY(33, "商家取消"),

    ACCEPTED(40, "已接单"),

    ARRIVED(50, "已到店"),

    CLEANED(60, "已清洁"),

    PUT_MAILMAN(70, "已放回"),

    TOOK_USER(90, "已取出"),

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

    public static OrderStateEnum getNextState(int state) {
        if (isCanceled(state)) {
            return getEnumByState(state);
        }
        OrderStateEnum temp = null;
        for (OrderStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return temp;
            } else {
                temp = stateEnum;
            }

        }
        return null;
    }

    private static boolean isCanceled(int state) {
        if (state > CANCELED.getState() && state < ACCEPTED.getState()) {
            return true;
        }
        return false;
    }

    public static OrderStateEnum getLastState(int state) {
        if (isCanceled(state)) {
            return getEnumByState(state);
        }
        boolean isLastOne = false;
        for (OrderStateEnum stateEnum : values()) {
            if (isLastOne) {
                return stateEnum;
            }
            if (stateEnum.getState() == state) {
                isLastOne = true;
            }
        }
        return null;
    }
}
