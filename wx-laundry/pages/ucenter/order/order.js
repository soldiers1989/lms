var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({
    data: {
        orderList: [],
        pageNum: 1,
        pageSize: 10,
        loadmoreText: '正在加载更多数据',
        nomoreText: '全部加载完成',
        nomore: false,
        totalPageNum: 1
    },
    onLoad: function (options) {
        // 页面初始化 options为页面跳转所带来的参数
        // 页面显示

        wx.showLoading({
            title: '加载中...',
            success: function () {

            }
        });
        this.getOrderList();
    },
    onPullDownRefresh: function () {
        this.setData({
            pageNum: 1,
            pageSize: 10
        });
        this.getOrderList();
    },
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        this.getOrderList();
    },

    getOrderList() {
        let that = this;

        if (that.data.totalPageNum <= that.data.pageNum - 1) {
            that.setData({
                nomore: true
            })
            return;
        }

        util.request(api.OrderList, {pageNum: this.data.pageNum, pageSize: this.data.pageSize}).then(function (res) {
            if (res.result) {
                that.setData({
                    orderList: res.pageNum > 1 ? that.data.orderList.concat(res.data) : res.data,
                    pageNum: res.pageNum + 1,
                    totalPageNum: res.totalPageNum
                });
                wx.hideLoading();
            }
        });
    },
    payOrder(event) {
        let that = this;
        let orderIndex = event.currentTarget.dataset.orderIndex;
        let order = that.data.orderList[orderIndex];
        wx.redirectTo({
            url: '/pages/pay/pay?orderId=' + order.id + '&actualPrice=' + order.actual_price,
        })
    },
    onReady: function () {
        // 页面渲染完成
    },
    onShow: function () {

    },
    onHide: function () {
        // 页面隐藏
    },
    onUnload: function () {
        // 页面关闭
    }
})