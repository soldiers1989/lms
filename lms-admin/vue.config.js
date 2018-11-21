module.exports = {
    productionSourceMap: false,
    devServer: {
        port:8090,
        proxy:  process.env.NODE_ENV == 'development' ?"http://localhost:8080":"http://120.79.91.131:8080"
    }
};
