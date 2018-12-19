package com.yniot.lms.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 10:00
 **/
public enum OrderStateEnum {

    /*..................注意:更改数值后,存储过程中相应的值也需要更改!..........*/
    /*..................注意:更改数值后,存储过程中相应的值也需要更改!..........*/
    /*..................注意:更改数值后,存储过程中相应的值也需要更改!..........*/
    COMMITTED(10, "订单提交"),
    //订单取消
    CANCELED(30, "已取消"),
    CANCELED_TIMEOUT(31, "超时取消"),
    CANCELED_CUSTOMER(32, "客户取消"),
    CANCELED_LAUNDRY(33, "商家取消"),
    PAY_TIMEOUT(34, "付款超时,取消订单"),
    PAY_REFUSE(35, "客户拒绝付款,取消订单"),
    ACCEPTED(40, "已接单"),
    WAITING_TO_PUT(42, "待存放"),
    PUT_USER(45, "用户已存放"),
    TOOK_MAILMAN(50, "配送员已取走"),
    WAITING_TO_PAY(55, "等待确认金额"),
    PAID(60, "已付款"),
    CLEANING(63, "清洁中"),
    CLEANED(65, "衣物已清洁"),
    LAUNDRY_SEND(67, "配送员已出发"),
    PUT_MAILMAN(70, "配送员已放回"),
    TOOK_USER(75, "用户已取回"),
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

    public static String toJson() {
        JSONArray jsonArray = new JSONArray();
        for (OrderStateEnum e : OrderStateEnum.values()) {
            JSONObject object = new JSONObject();
            object.put("state", e.getState());
            object.put("name", e.getName());
            jsonArray.add(object);
        }
        return jsonArray.toString();
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        object.put("state", state);
        object.put("name", name);
        return object.toString();
    }

}
