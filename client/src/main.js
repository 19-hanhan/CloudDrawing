import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import cookies from 'vue-cookies'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'


Vue.config.productionTip = false
Vue.prototype.$axios = axios;
Vue.prototype.$cookies = cookies;
Vue.use(ElementUI);

// 配置全局样式，@符号，代指src文件夹的路径
import '@/assets/css/global.css'

// 配置全局自定义设置
import settings from '@/assets/js/settings'

Vue.prototype.$settings = settings;


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
