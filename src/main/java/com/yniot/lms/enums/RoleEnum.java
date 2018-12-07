package com.yniot.lms.enums;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-25 10:00
 **/
public enum RoleEnum {

    ADMIN(1, "管理员"),
    USER(2, "用户"),
    LAUNDRY(3, "洗衣店"),
    MAILMAN(4, "配送员");

    private Integer roleId;
    private String name;

    private RoleEnum(Integer state, String name) {
        this.roleId = state;
        this.name = name;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public static RoleEnum getEnumByRoleId(int roleId) {
        for (RoleEnum errorMsgEnum : values()) {
            if (errorMsgEnum.getRoleId() == roleId) {
                return errorMsgEnum;
            }
        }
        return null;
    }

}
