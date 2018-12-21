/**
 * 支付相关服务
 */

const util = require('../utils/util.js');
const api = require('../config/api.js');

/**
 * 判断用户是否登录
 */
function payOrder(orderId) {
    return new Promise(function (resolve, reject) {
        // wx.request({
        //     url: 'http://ip-api.com/json',
        //     success: function (e) {
        //         that.setData({
        //             ipAddress: e.data.query
        //         })
        //     }
        // });

        util.request(api.PayPrepayId, {
            orderId: orderId
        }).then((res) => {
            if (res.result && res.data) {
                const payParam = res.data;
                console.log(payParam);
                wx.requestPayment({
                    'timeStamp': payParam.timeStamp,
                    'nonceStr': payParam.nonceStr,
                    'package': payParam.packageValue,
                    'signType': payParam.signType,
                    'paySign': payParam.paySign,
                    'success': function (res) {
                        resolve(res);
                    },
                    'fail': function (res) {
                        reject(res);
                    },
                    'complete': function (res) {
                        reject(res);
                    }
                });
            } else {
                reject(res);
            }
        });
    });
}

function closeOrder(orderId) {
    return new Promise(function (resolve, reject) {
        util.request(api.PayCloseOrder, {
            orderId: orderId
        }).then((res) => {
            if (res.result && res.data) {

            } else {
                reject(res);
            }
        });
    });
}

module.exports = {
    payOrder,
    closeOrder
};











