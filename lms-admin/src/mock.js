const Mock = require("mockjs");

const signIn = () => ({
    token: ""
    // "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUzMDMyODY2MywiaWF0IjoxNTI5NzIzODYzfQ.cgmhRgLhy8P0U2l1oLhZqHwlsetp4tUGWjxaDRjHq2uKYOKSveZikhsl_r1drbNQ8lg8ErviShknFVgo-nXg1g"
});

const queryUserAuthority = () => ({
    menus: [
        {
            menuId: 1,
            menuName: "根节点",
            menuCode: "root",
            link: "/root",
            icon: "el-icon-menu",
            lft: 1,
            rgt: 14,
            depth: 0
        },
        {
            menuId: 2,
            menuName: "首页",
            menuCode: "dashboard",
            link: "/dashboard",
            icon: "el-icon-menu",
            lft: 2,
            rgt: 3,
            depth: 1
        },
        {
            menuId: 20,
            menuName: "系统管理",
            menuCode: "system",
            link: "/system",
            icon: "el-icon-menu",
            lft: 4,
            rgt: 9,
            depth: 1
        },
        {
            menuId: 21,
            menuName: "个人设定",
            menuCode: "profile",
            link: "/system/profile",
            icon: "el-icon-menu",
            lft: 5,
            rgt: 6,
            depth: 2
        },
        {
            menuId: 22,
            menuName: "用户管理",
            menuCode: "user",
            link: "/system/user",
            icon: "el-icon-menu",
            lft: 5,
            rgt: 6,
            depth: 2
        },
        {
            menuId: 23,
            menuName: "角色管理",
            menuCode: "role",
            link: "/system/role",
            icon: "el-icon-menu",
            lft: 7,
            rgt: 8,
            depth: 2
        }
        ,
        {
            menuId: 24,
            menuName: "微信配置",
            menuCode: "WeChat",
            link: "/system/WeChat",
            icon: "el-icon-menu",
            lft: 7,
            rgt: 8,
            depth: 2
        },
        {
            menuId: 30,
            menuName: "洗衣店",
            menuCode: "laundry",
            link: "/laundry",
            icon: "el-icon-menu",
            lft: 10,
            rgt: 13,
            depth: 1
        },
        {
            menuId: 31,
            menuName: "订单列表",
            menuCode: "orderList",
            link: "/laundry/orderList",
            icon: "el-icon-menu",
            lft: 11,
            rgt: 12,
            depth: 2
        },
        {
            menuId: 32,
            menuName: "标记列表",
            menuCode: "codeList",
            link: "/laundry/codeList",
            icon: "el-icon-menu",
            lft: 11,
            rgt: 12,
            depth: 2
        },
        {
            menuId: 33,
            menuName: "柜子列表",
            menuCode: "wardrobeList",
            link: "/laundry/wardrobeList",
            icon: "el-icon-menu",
            lft: 11,
            rgt: 12,
            depth: 2
        },
        {
            menuId: 34,
            menuName: "洗衣店列表",
            menuCode: "laundryList",
            link: "/laundry/laundryList",
            icon: "el-icon-menu",
            lft: 11,
            rgt: 12,
            depth: 2
        },
        {
            menuId: 35,
            menuName: "价格列表",
            menuCode: "priceTableList",
            link: "/laundry/priceTableList",
            icon: "el-icon-menu",
            lft: 11,
            rgt: 12,
            depth: 2
        },
        {
            menuId: 36,
            menuName: "衣物列表",
            menuCode: "goodsTypeList",
            link: "/laundry/goodsTypeList",
            icon: "el-icon-menu",
            lft: 11,
            rgt: 12,
            depth: 2
        },
        {
            menuId: 37,
            menuName: "申请列表",
            menuCode: "applicationList",
            link: "/laundry/applicationList",
            icon: "el-icon-menu",
            lft: 11,
            rgt: 12,
            depth: 2
        }
    ],
    authorities: [
        {authority_id: 1, authority_code: "AddUser", authority_name: "新增用户"},
        {authority_id: 2, authority_code: "EditUser", authority_name: "修改用户"},
        {
            authority_id: 3,
            authority_code: "DeleteUser",
            authority_name: "删除用户"
        },
        {
            authority_id: 4,
            authority_code: "QueryUser",
            authority_name: "查询用户"
        },
        {
            authority_id: 5,
            authority_code: "QueryReport",
            authority_name: "查询报告"
        }
    ]
});

const queryUser = () => ({
    total: 3,
    size: 2,
    current: 1,
    records: [
        {
            userId: 1,
            userName: "guest",
            password: "$2a$10$llOd5/mvInD.OTVkPr/hqeFaa6wX4DCbtKIJwRq5vhKLd3mJNtLqi",
            nation: 86,
            phone: 15985859933,
            avatar: null,
            email: "test@test.com",
            gender: null,
            birthday: "2018-07-16T00:50:31.000+0000",
            enabled: false,
            type: 1,
            createDate: "2018-07-16T00:50:31.000+0000",
            lastDate: "2018-07-16T00:50:31.000+0000",
            signInDate: "2018-07-16T00:50:31.000+0000"
        }
    ],
    pages: 2
});

Mock.mock("/passport/signIn", "post", signIn);
Mock.mock("/user/queryUserAuthority", "get", queryUserAuthority);
Mock.mock("/user", "get", queryUser);
