var util = require('../../utils/util.js');
var api = require('../../config/api.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        totalPrice: 0.00,
        wardrobeList: [],
        checkedIndex: -1,
        latitude: null,
        longitude: null,
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
                that.calculateDistance();
            }
        });
    },
    ///7627039.251710409
    calculateDistance: function () {
        let that = this;
        wx.getLocation({
            type: 'wgs84',
            success: function (res) {
                that.setData({
                    latitude: res.latitude,
                    longitude: res.longitude
                });
                let tempData = that.data.wardrobeList;
                for (let i in tempData) {
                    //lat1, lng1, lat2, lng2
                    let temp = util.getDistance(tempData[i].latitude, tempData[i].longitude,
                        that.data.latitude, that.data.longitude);
                    console.log(temp);
                    tempData[i].distance = temp;
                }
                that.setData({
                    wardrobeList: tempData,
                });
            }
        })
    },
    commitOrder: function () {
        if (this.data.checkedIndex < 0) {
            util.showErrorToast("没有选择柜子!");
            return;
        }
        let that = this;
        wx.showModal({
            title: '',
            content: '确定要提交订单吗？',
            success: function (res) {
                if (res.confirm) {
                    let wardrobe = that.data.wardrobeList[that.data.checkedIndex];
                    util.request(api.CommitOrder, {wardrobeId: wardrobe.id}).then(function (res) {
                        if (res.result) {
                            wx.redirectTo({
                                url: '/pages/ucenter/order/order'
                            })
                        }
                    });
                }
            }
        });

    },
    checkedItem: function (event) {
        let that = this;
        let itemIndex = event.target.dataset.itemIndex;
        let wardrobe = that.data.wardrobeList[itemIndex];
        if (wardrobe.avaCellNum > 0) {
            that.setData({
                checkedIndex: itemIndex == this.data.checkedIndex ? -1 : itemIndex
            });
        } else {
            util.showErrorToast("没有可用的格子!");
        }

    },
})