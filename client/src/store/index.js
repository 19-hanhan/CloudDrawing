import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import BaiduMap from 'vue-baidu-map'

Vue.use(Vuex)
Vue.use(BaiduMap, {
    // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
    ak: 'LdozBwTuzbGLPLweujjpsp4bYO6rWuQT'
})

export default new Vuex.Store({
    state: {
        id: null,
        nickname: null,
        avatar: null,
        email: null,
        fans: null,
        follow: null
    },
    mutations: {
        login(state, user) {
            state.id = user.id;
            state.nickname = user.nickname;
            state.avatar = user.avatar;
            state.email = user.email;
            state.fans = user.fans;
            state.follow = user.follow;
        },
        logout(state) {
            state.id = null;
            state.nickname = null;
            state.avatar = null;
            state.email = null;
            state.fans = null;
            state.follow = null;
        },
        setAvatar(state, avatar) {
            state.avater = avatar;
        },
        setNickname(state, nickname) {
            state.nickname = nickname;
        }
    },
    actions: {},
    modules: {},
    plugins: [
        createPersistedState({
            storage: window.sessionStorage
        })
    ]
})
