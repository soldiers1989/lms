const util = require('../../../utils/util.js');
const api = require('../../../config/api.js');

//获取应用实例
const app = getApp()
Page({
    data: {
        canIUse: wx.canIUse('button.open-type.getUserInfo'),
        navUrl: ''
    },

    onLoad: function (options) {
        let that = this;
        if (wx.getStorageSync("navUrl")) {
            that.setData(
                {
                    navUrl: wx.getStorageSync("navUrl")
                }
            )
        } else {
            that.setData(
                {
                    navUrl: '/pages/index/index'
                }
            )
        }
    },
    getUserUnionId: function () {
    },
    bindGetUserInfo: function (e) {
        let that = this;
        // console.log(e);
        wx.login({
            success: function (res) {
                if (res.code) {
                    //登录远程服务器
                    util.request(api.AuthLoginByWeixin, {
                        code: res.code,
                        userInfo: e.detail
                    }).then(res => {
                        //存储用户信息
                        wx.setStorageSync('userInfo', res.data.userInfo);
                        wx.setStorageSync('token', res.data.token);
                        wx.setStorageSync('userId', res.data.userId);


                        wx.getSetting({
                            success(res) {
                                if (res.authSetting['scope.userInfo']) {
                                    // 已经授权，可以直接调用 getUserInfo 获取头像昵称
                                    wx.getUserInfo({
                                        success: function (res) {
                                            // console.log(res.userInfo)
                                        }
                                    })
                                }
                            },
                          fail(res) {
                            // console.log(res);
                              
                            }
                        })

                    });
                }
            }
        });

        if (that.data.navUrl && that.data.navUrl == '/pages/index/index') {
            wx.switchTab({
                url: that.data.navUrl,
            })
        } else if (that.data.navUrl) {
            wx.redirectTo({
                url: that.data.navUrl,
            })
        }
    },
    onReady: function () {
        // 页面渲染完成
    },
    onShow: function () {
        // 页面显示
    },
    onHide: function () {
        // 页面隐藏
    },
    onUnload: function () {
        // 页面关闭
    }
})
