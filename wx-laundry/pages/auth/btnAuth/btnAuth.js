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
    bindGetUserInfo: function (e) {
        let that = this;
        wx.login({
            success: function (res) {
                if (res.code) {
                    //登录远程服务器
                    wx.setStorageSync("rawData",e.detail.rawData);
                    wx.setStorageSync("signature",e.detail.signature);
                    util.request(api.AuthLoginByWeixin, {
                        code: res.code,
                        userInfo: JSON.stringify(e.detail)
                    }).then(function (res) {
                        if (res.result) {
                            //存储用户信息
                            wx.setStorageSync('userInfo', res.data.userInfo);
                            wx.setStorageSync('token', res.data.token);
                            wx.setStorageSync('userId', res.data.userId);
                        } else {
                            wx.showModal({
                                title: '提示',
                                content: res.errmsg,
                                showCancel: false
                            });
                        }

                    }).catch(function (error) {
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
