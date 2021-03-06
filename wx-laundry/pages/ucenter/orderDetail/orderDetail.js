var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
let drawQrcode = require('../../../utils/weapp.qrcode.min.js');
Page({
    data: {
        showQRCode: false,
        orderId: 0,
        orderCode: "",
        orderInfo: {},
        goodsList: [],
        orderCost: {},
        orderShipment: {},
        codeExpireTime: "暂无",
        socketTask: null,
        handleOption: {}
    },
    onLoad: function (options) {
        // 页面初始化 options为页面跳转所带来的参数
        console.log(options);
        this.setData({
            orderId: options.id,
            orderCode: options.orderCode
        });
        this.getOrderDetail();
        this.getOrderCost();
        this.getOrderGoodsList();
        this.getOrderShipment();
        // this.initSaveListener();
    },
    initSaveListener() {
        let socketTask = util.initWebsocket(
            function (res) {
            },
            function (res) {
                if (res.type == "Shipment") {
                    res.data;
                }
            },
            function (res) {
            },
            function (res) {
            });
        if (socketTask != null) {
            this.setData({
                socketTask: socketTask
            });
        }
    },
    gotoPay() {
        let that = this;
        wx.redirectTo({
            url: '/pages/pay/pay?' + "orderId=" + that.data.orderId
                + "&actTotalCost=" + that.data.orderCost.actTotalCost
                + "&orderCode=" + that.data.orderCode,
        })
    },
    getOrderDetail() {
        let that = this;
        util.request(api.OrderDetail, {
            orderId: that.data.orderId
        }).then(function (res) {
            if (res.result) {
                that.setData({
                    orderInfo: res.data,
                    // goodsList: res.data.goodsList,
                    // handleOption: res.data.handleOption
                });
            }
        });
    },
    payTimer() {
        let that = this;
        let orderInfo = that.data.orderInfo;

        setInterval(() => {
            orderInfo.add_time -= 1;
            that.setData({
                orderInfo: orderInfo,
            });
        }, 1000);
    },
    getOrderCost() {
        let that = this;
        util.request(api.GetOrderCostById, {
            orderId: that.data.orderId
        }).then(function (res) {
            if (res.result) {
                that.setData({
                    orderCost: res.data,
                });
            }
        });
    },
    getOrderGoodsList() {
        let that = this;
        util.request(api.GetOrderGoodsById, {
            orderId: that.data.orderId
        }).then(function (res) {
            if (res.result) {
                that.setData({
                    goodsList: res.data,
                });
            }
        });
    },
    getOrderShipment(callback) {
        let that = this;
        util.request(api.GetOrderShipmentById, {
            orderId: that.data.orderId
        }).then(function (res) {
            if (res.result) {
                that.setData({
                    orderShipment: res.data,
                });
                callback && callback();
            }
        });
    },
    cancelOrder() {
        let that = this;
        let orderInfo = that.data.orderInfo;

        var order_status = orderInfo.order_status;

        var errorMessage = '';
        switch (order_status) {
            case 300: {
                errorMessage = '订单已发货';
                break;
            }
            case 301: {
                errorMessage = '订单已收货';
                break;
            }
            case 101: {
                errorMessage = '订单已取消';
                break;
            }
            case 102: {
                errorMessage = '订单已删除';
                break;
            }
            case 401: {
                errorMessage = '订单已退款';
                break;
            }
            case 402: {
                errorMessage = '订单已退货';
                break;
            }
        }

        if (errorMessage != '') {
            util.showErrorToast(errorMessage);
            return false;
        }

        wx.showModal({
            title: '',
            content: '确定要取消此订单？',
            success: function (res) {
                if (res.confirm) {

                    util.request(api.OrderCancel, {
                        orderId: orderInfo.id
                    }).then(function (res) {
                        if (res.errno === 0) {
                            wx.showModal({
                                title: '提示',
                                content: res.data,
                                showCancel: false,
                                confirmText: '继续',
                                success: function (res) {
                                    //  util.redirect('/pages/ucenter/order/order');
                                    wx.navigateBack({
                                        url: 'pages/ucenter/order/order',
                                    });
                                }
                            });
                        }
                    });

                }
            }
        });
    },
    showStorageQRCode() {
        let that = this;
        if (this.data.showQRCode) {
            this.setData({
                showQRCode: false
            });
        } else {
            this.getOrderShipment(function () {
                drawQrcode({
                    width: 200,
                    height: 200,
                    canvasId: 'storageQRCode',
                    text: that.data.orderShipment.password + ""
                });
            });
            this.setData({
                showQRCode: true,
            });

        }
    },
    goodsSavedToCloset() {

    },
    getGoods() {

    },
    confirmOrder() {
//确认收货
        let that = this;
        let orderInfo = that.data.orderInfo;

        var order_status = orderInfo.order_status;

        var errorMessage = '';
        switch (order_status) {
            // case 300: {
            //   errorMessage = '订单已发货';
            //   break;
            // }
            case 301: {
                errorMessage = '订单已收货';
                break;
            }
            case 101: {
                errorMessage = '订单已取消';
                break;
            }
            case 102: {
                errorMessage = '订单已删除';
                break;
            }
            case 401: {
                errorMessage = '订单已退款';
                break;
            }
            case 402: {
                errorMessage = '订单已退货';
                break;
            }
        }

        if (errorMessage != '') {
            util.showErrorToast(errorMessage);
            return false;
        }

        wx.showModal({
            title: '',
            content: '确定已经收到商品？',
            success: function (res) {
                if (res.confirm) {

                    util.request(api.OrderConfirm, {
                        orderId: orderInfo.id
                    }).then(function (res) {
                        if (res.errno === 0) {
                            wx.showModal({
                                title: '提示',
                                content: res.data,
                                showCancel: false,
                                confirmText: '继续',
                                success: function (res) {
                                    //  util.redirect('/pages/ucenter/order/order');
                                    wx.navigateBack({
                                        url: 'pages/ucenter/order/order',
                                    });
                                }
                            });
                        }
                    });

                }
            }
        });
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