module.exports = {
    productionSourceMap: false,
    devServer: {
        port:8090,
        proxy:  process.env.NODE_ENV == 'development' ?"https://localhost:443":"https://120.79.91.131:443"
    }
};
