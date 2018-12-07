var util = require('../../utils/util.js');
var api = require('../../config/api.js');

var app = getApp();

Page({
    data: {
        cartGoods: [],
        cartTotal: {
            "goodsCount": 0,
            "goodsAmount": 0.00,
            "checkedGoodsCount": 0,
            "checkedGoodsAmount": 0.00
        },
        isEditCart: false,
        checkedAllStatus: true,
        totalPrice: 0.00,
        editCartList: []
    },
    onLoad: function (options) {
        // 页面初始化 options为页面跳转所带来的参数

    },
    onReady: function () {
        // 页面渲染完成

    },
    onShow: function () {
        // 页面显示
        this.getCartList();
    },
    onHide: function () {
        // 页面隐藏

    },
    onUnload: function () {
        // 页面关闭

    },
    getCartList: function () {
        let that = this;
        util.request(api.CartList).then(function (res) {
            if (res.result) {
                let cartTotal = 0;
                let tempTotalPrice = 0.00;
                for (let i = 0; i < res.data.length; i++) {
                    tempTotalPrice += (res.data[i].count * res.data[i].price);
                    cartTotal += res.data[i].count;
                }
                that.setData({
                    totalPrice: tempTotalPrice,
                    cartGoods: res.data,
                    cartTotal: cartTotal
                });
            }
        });
    },
    toIndexPage: function () {
        wx.switchTab({
            url: "/pages/index/index"
        });
    },
    toWardrobePage: function () {
        wx.navigateTo({
            url: '/pages/wardrobe/wardrobe?totalPrice='+this.data.totalPrice
        })
    },
    updateCart: function (goodsId, add) {
        let that = this;
        let url = add ? api.CartAdd : api.CartDel;
        util.request(url, {
            goodsId: goodsId,
        }).then(function (res) {
            if (res.result) {

            }
        });
    },
    cutNumber: function (event) {
        let goodsId = event.target.dataset.goodsId;
        this.updateCart(goodsId, false);
    },
    addNumber: function (event) {
        let goodsId = event.target.dataset.goodsId;
        this.updateCart(goodsId, true);
    },
    //下单
    checkoutOrder: function () {
        //获取已选择的商品
        //跳转到洗衣店列表
        // var checkedGoods = this.data.cartGoods.filter(function (element, index, array) {
        //     if (element.checked == true) {
        //         return true;
        //     } else {
        //         return false;
        //     }
        // });

        // if (checkedGoods.length <= 0) {
        //     return false;
        // }
        // wx.navigateTo({
        //     url: '../shopping/checkout/checkout'
        // })
    }
})