var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
    data: {
        navList: [],
        categoryList: [],
        currentCategory: {},
        scrollLeft: 0,
        scrollTop: 0,
        goodsCount: 0,
        latitude: 0,
        longitude: 0,
        scrollHeight: 0
    },
    onLoad: function (options) {
        this.getCatalog();
        this.getLocation();
    },
    getCatalog: function () {
        let that = this;
        wx.showLoading({
            title: '加载中...',
        });
        util.request(api.CatalogList).then(function (res) {
            if (res.data) {
                that.setData({
                    navList: res.data,
                    currentCategory: res.data.length > 0 ? res.data[0] : {}
                });
                that.getCurrentCategory(res.data[0].id);
            }
            wx.hideLoading();
        });
    },
    getLocation() {
        let that = this;
        wx.getLocation({
            type: 'wgs84',
            success: function (res) {
                that.setData({
                    latitude: res.latitude,
                    longitude: res.longitude
                })
            }
        })
    },
    addToCart(event) {
        let goodsId = event.currentTarget.dataset.id;
        util.request(api.CartAdd, {goodsId: goodsId})
            .then(function (res) {
            });
    },
    getCurrentCategory: function (id) {
        let that = this;
        util.request(api.CatalogCurrent, {catalogId: id})
            .then(function (res) {
                for (let i = 0; i < that.data.navList.length; i++) {
                    if (that.data.navList[i].id == id) {
                        that.setData({
                            currentCategory: that.data.navList[i]
                        });
                    }
                }
                that.setData({
                    categoryList: res.data
                });
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
    },
    switchCate: function (event) {
        let that = this;
        let currentTarget = event.currentTarget;
        if (this.data.currentCategory.id == event.currentTarget.dataset.id) {
            return false;
        }
        this.getCurrentCategory(event.currentTarget.dataset.id);
    }
})