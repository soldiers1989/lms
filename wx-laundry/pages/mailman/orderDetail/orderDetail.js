var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
let drawQrcode = require('../../../utils/weapp.qrcode.min.js');
Page({
    data: {
        showQRCode: false,
        orderId: 0,
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
        this.setData({
            orderId: options.id
        });
        this.getOrderDetail();
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
    getOrderShipment() {
        let that = this;
        util.request(api.GetOrderShipmentById, {
            orderId: that.data.orderId
        }).then(function (res) {
            if (res.result) {
                that.setData({
                    orderShipment: res.data,
                });
            }
        });
    },
    showStorageQRCode() {
        this.getOrderShipment();
        let that = this;
        if (this.data.showQRCode) {
            this.setData({
                showQRCode: false
            });
        } else {
            this.setData({
                showQRCode: true,
            });
            drawQrcode({
                width: 200,
                height: 200,
                canvasId: 'storageQRCode',
                text: that.data.orderShipment.password + ""
            });
        }
    },
    goodsSavedToCloset() {

    },
    getGoods() {

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