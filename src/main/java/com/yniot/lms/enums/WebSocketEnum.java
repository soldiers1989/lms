package com.yniot.lms.enums;

/**
 * @Auther: lane
 * @Date: 2018-12-10 09:42
 * @Description:
 * @Version 1.0.0
 */
public enum WebSocketEnum {


    Shipment("Shipment", "物流"),
    PAY("", "配送员");

    private String type;
    private String name;

    private WebSocketEnum(String state, String name) {
        this.type = state;
        this.name = name;
    }

    public String getType() {
        return type;
    }

}
