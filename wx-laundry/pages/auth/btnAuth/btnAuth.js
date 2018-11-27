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
                    util.request(api.AuthLoginByWeixin, {
                        code: res.code,
                        userInfo: e.detail
                    }, 'POST').then(res => {
                        let detail = e.detail;
                        detail.sessionKey = res.data.sessionKey;
                      console.log(detail);
                        util.request(api.AuthUserInfo, detail, 'POST').then(res => {
                            console.log(res.data);
                        });

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
