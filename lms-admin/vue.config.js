module.exports = {
    productionSourceMap: false,
    devServer: {
        port:8090,
        // proxy:  process.env.NODE_ENV == 'development' ?"https://localhost:8443":"https://120.79.91.131:8443"
        proxy:  "https://120.79.91.131:443"//正式环境

    }
};
