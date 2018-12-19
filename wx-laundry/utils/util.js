var api = require('../config/api.js');

function formatTime(date) {
    let year = date.getFullYear()
    let month = date.getMonth() + 1
    let day = date.getDate()

    let hour = date.getHours()
    let minute = date.getMinutes()
    let second = date.getSeconds()


    return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
    n = n.toString()
    return n[1] ? n : '0' + n
}


function initWebsocket(onOpen, onMessage, onError, onClose) {
    let sessionId = wx.getStorageSync('token');
    let socketTask = wx.connectSocket({
        url: api.WebsocketUrl + sessionId,
    });
    if (!socketTask) {
        return null;
    }
    onMessage && socketTask.onMessage(onMessage(res));
    onClose && socketTask.onClose(onClose(res));
    onOpen && socketTask.onOpen(onOpen(res));
    onError && socketTask.onMessage(onError(res));
    return socketTask;
}



function request(url, data = {}, method = "POST", header = "application/x-www-form-urlencoded") {
    wx.showLoading({
        title: '加载中...',
    });
    return new Promise(function (resolve, reject) {
        let token = wx.getStorageSync('token');
        wx.request({
            url: url,
            data: data,
            method: method,
            header: {
                'Content-Type': header,
                'Authorization': token,//'Bearer '+
            },
            success: function (res) {
                wx.hideLoading();
                if (res.statusCode == 200) {
                    if (!token) {
                        wx.redirectTo({
                            url: '/pages/auth/login/login',
                        });
                    }
                    resolve(res.data);
                } else {
                    reject(res.errMsg);
                }

            },
            fail: function (err) {
                reject(err)
            }
        })
    });
}

/**
 * 检查微信会话是否过期
 */
function checkSession() {
    return new Promise(function (resolve, reject) {
        wx.checkSession({
            success: function () {
                resolve(true);
            },
            fail: function () {
                reject(false);
            }
        })
    });
}

/**
 * 调用微信登录
 */
function login() {
    return new Promise(function (resolve, reject) {
        wx.login({
            success: function (res) {
                if (res.code) {
                    resolve(res);
                } else {
                    reject(res);
                }
            },
            fail: function (err) {
                reject(err);
            }
        });
    });
}

function redirect(url) {
    //判断页面是否需要登录
    if (!wx.getStorageSync('token')) {
        wx.redirectTo({
            url: '/pages/auth/login/login'
        });
        return false;
    } else {
        wx.redirectTo({
            url: url
        });
    }
}

function showErrorToast(msg) {
    wx.showToast({
        title: msg,
        image: '/static/images/icon_error.png'
    })
}

function showSuccessToast(msg) {
    wx.showToast({
        title: msg,
    })
}

//计算两点位置距离
function getDistance(lat1, lon1, lat2, lon2) {
    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat2 - lat1);
    var dLon = deg2rad(lon2 - lon1);
    var a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c; // Distance in km
    return Math.floor(d * 1000);
}

function deg2rad(deg) {
    return deg * (Math.PI / 180)
}

module.exports = {
    formatTime,
    request,
    redirect,
    initWebsocket,
    showErrorToast,
    showSuccessToast,
    checkSession,
    login,
    getDistance,
}


