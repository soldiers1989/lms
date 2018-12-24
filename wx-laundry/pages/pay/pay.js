var app = getApp();
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const pay = require('../../services/pay.js');

Page({
    data: {
        orderId: 0,
        ipAddress: "",
        orderCode: "",
        actualPrice: 0.00
    },
    onLoad: function (options) {
        let that = this;
        // 页面初始化 options为页面跳转所带来的参数
        this.setData({
            orderCode: options.orderCode,
            orderId: options.orderId,
            actualPrice: options.actTotalCost
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
        pay.payOrder(that.data.orderId,
            function (res) {
                console.log(res);
            }, function (res) {
                console.log(res);
            }, function (res) {
                console.log(res);
            });
    },
    startPay() {
        this.requestPayParam();
    }
})