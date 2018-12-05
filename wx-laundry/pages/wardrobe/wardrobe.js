var util = require('../../utils/util.js');
var api = require('../../config/api.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        totalPrice: 0.00,
        wardrobeList: [],
        checkedIndex: 0,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({totalPrice: decodeURIComponent(options.totalPrice)})
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        this.getWardrobe();
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    },
    getWardrobe: function () {
        let that = this;
        util.request(api.GetWardrobe).then(function (res) {
            if (res.result) {
                that.setData({
                    wardrobeList: res.data,
                });
            }
        });
    },

    isCellAvailable: function (wardrobeId) {
        let that = this;
        util.request(api.GetWardrobe).then(function (res) {
            if (res.result) {
                that.setData({
                    wardrobeList: res.data,
                });
            }
        });
    },
    commitOrder: function () {
        let that = this;
        wx.showModal({
            title: '',
            content: '确定要提交订单吗？',
            success: function (res) {
                if (res.confirm) {
                    let wardrobe = that.data.wardrobeList[that.data.checkedIndex];
                    util.request(api.CommitOrder, {wardrobeId: wardrobe.id}).then(function (res) {
                        console.log(res);
                    });
                }
            }
        });

    },
    checkedItem: function (event) {
        let itemIndex = event.target.dataset.itemIndex;
        let that = this;
        that.setData({
            checkedIndex: itemIndex == this.data.checkedIndex ? -1 : itemIndex
        });
    },
})