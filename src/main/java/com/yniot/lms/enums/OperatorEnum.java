package com.yniot.lms.enums;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 12:02
 **/
public enum OperatorEnum {

    USER(10, "用户"),
    MAIL_MAN(20, "配送员"),
    LAUNDRY(40, "洗衣店"),
    ADMIN(99, "管理员"),
    USER_LAUNDRY(50, "用户和洗衣店"),
    ALL(70, "用户、配送员、洗衣店"),
    USER_MAIL_MAN(60, "洗衣店和配送员");

    private Integer type;
    private String name;

    private OperatorEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }


}
