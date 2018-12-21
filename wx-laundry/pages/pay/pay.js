var app = getApp();
var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
    data: {
        orderId: 0,
        ipAddress: "",
        orderCode: "",
        actualPrice: 0.00
    },
    onLoad: function (options) {
        let that = this;
        wx.request({
            url: 'http://ip-api.com/json',
            success: function (e) {
                that.setData({
                    ipAddress: e.data.query
                })
            }
        })
        // 页面初始化 options为页面跳转所带来的参数
        this.setData({
            orderCode: options.orderCode,
            orderId: options.orderId,
            actualPrice:  options.actTotalCost
        })
    },
    onReady: function () {

    },
    onShow: function () {
        // 页面显示

    },
    onHide: function () {
        // 页面隐藏

    },
    onUnload: function () {
        // 页面关闭

    },
    //向服务请求支付参数
    requestPayParam() {
        let that = this;
        util.request(api.PayPrepayId, {
            version: "1.0",
            body: "洗涤费用",
            outTradeNo: that.data.orderCode,
            totalFee: that.data.actualPrice,
            spbillCreateIp: that.data.ipAddress,
            notifyUrl: "/notify/order",
            tradeType: "NATIVE",
            openid: "ofJw-5UbxFGf_Z9dE1k1V9Mu1rX0"
            // orderId: that.data.orderId,
            // payType: 1
        },"application/json").then(function (res) {//
            if (res.data.result === 0) {
                let payParam = res.data;
                wx.requestPayment({
                    'timeStamp': payParam.timeStamp,
                    'nonceStr': payParam.timeStamp,
                    'package': payParam.nonceStr,
                    'signType': payParam.signType,
                    'paySign': payParam.paySign,
                    'success': function (res) {
                        wx.redirectTo({
                            url: '/pages/payResult/payResult?status=true',
                        });
                    },
                    'fail': function (res) {
                        wx.redirectTo({
                            url: '/pages/payResult/payResult?status=false',
                        })
                    }
                })
            }
        });
    },
    startPay() {
        this.requestPayParam();
    }
})