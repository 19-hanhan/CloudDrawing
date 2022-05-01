import Vue from 'vue'
import Vuex from 'vuex'
import BaiduMap from 'vue-baidu-map'

Vue.use(Vuex)
Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  ak: 'LdozBwTuzbGLPLweujjpsp4bYO6rWuQT'
})

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
