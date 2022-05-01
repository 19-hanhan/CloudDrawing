const webpack = require("webpack")

module.exports = {
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                "window.jQuery": "jquery",
                "window.$": "jquery",
                Popper: ['popper.js', 'default']
            })
        ]
    },
    devServer: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'http://localhost:4000', // 请求的API地址
                // target: 'http://sagwun.natappfree.cc', // 请求的API地址
                changeOrigin: true, // 跨域
                ws: true,
                pathRewrite: { // 请求地址重写  http://client-path/api/login ⇒ http://server-path/login
                    '^/api': ''
                }
            }
        }
    }
};
