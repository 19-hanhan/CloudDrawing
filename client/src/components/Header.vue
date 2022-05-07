<template>
    <div class="header">
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">

                <div class="navbar-header">
                    <!-- 新消息图标 -->
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <svg v-if="this.new_message" viewBox="0 0 8 8" width="8px" height="8px"
                             style="position: absolute; right: 0; top: 0;">
                            <circle cx='4' cy='4' r='4' fill="#f99709"></circle>
                        </svg>
                    </button>

                    <!-- Logo -->
                    <div class="navbar-brand">
                        <router-link to="/">
                            <img src="../assets/img/Head-logo.png" alt="" class="head_logo">
                        </router-link>
                    </div>

                    <!-- 功能键 -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="/home"><span class="glyphicon glyphicon-home"></span> 首页</a></li>
                            <!-- 登录后功能 -->
                            <li v-if="token"><a href="/NewWeibo"><span class="glyphicon glyphicon-edit"></span> 新日记</a>
                            </li>
                            <li v-if="token"><a href="/MyWeibo"><span class="glyphicon glyphicon-user"></span> {{ this.nickname }}</a></li>
                            <li v-if="token">
                                <a id="message_button" @click="read_message">
                                    <el-badge v-if="this.new_message" class="item"><span
                                            class="el-icon-message-solid"></span>消息
                                    </el-badge>
                                    <el-badge v-if="!this.new_message" class="item"><span class="el-icon-bell"></span>消息
                                    </el-badge>
                                    <svg v-if="this.new_message" viewBox="0 0 8 8" width="8px" height="8px"
                                         style="position: absolute; right: 0.6em; top: 0.6em">
                                        <circle cx='4' cy='4' r='4' fill="#f99709"></circle>
                                    </svg>
                                </a>
                            </li>
                            <li v-if="token" @click="login_out"><a href="/"><span
                                    class="glyphicon glyphicon-log-out"></span> 退出</a>
                            </li>
                            <!-- 未登录功能 -->
                            <li v-if="!token"><a href="#" @click="put_register"><span
                                    class="glyphicon glyphicon-plus"></span>
                                注册</a>
                            </li>
                            <li v-if="!token"><a href="#" @click="put_login"><span
                                    class="glyphicon glyphicon-log-in"></span>
                                登录</a>
                            </li>
                        </ul>
                        <Login v-if="isLogin" @close="close_login" @go="put_register" @loginsuccess="login_success"/>
                        <Register v-if="isRegister" @close="close_register" @go="put_login"/>
                    </div>
                </div>

                <!-- 消息中心抽屉 -->
                <el-drawer id="message_center" title="消息中心" :visible.sync="drawer">
                    <div class="read_and_delete_button" v-if="this.message_lis">
                        <el-button class="read_button" type="primary" plain size="small" @click="have_read_all">全部标为已读
                        </el-button>
                        <el-button class="delete_button" type="danger" plain size="small" @click="del_all_notice">全部删除
                        </el-button>
                    </div>
                    <div v-if="!this.message_lis" style="color: #868686; font-size: large; font-weight: 300;">暂无消息</div>
                    <el-col v-if="this.message_lis">
                        <div class="card_outer_div" v-for="message in this.message_lis" v-if="!message.notice.isRead">
                            <el-card class="message_card unread clearfix" shadow="never">
                                <div class="message_content">
                                    <a href="#"
                                       @click="other_user_page(message.sender.id)"><span>{{ message.sender.nickname }} -> </span></a>
                                    <span>{{ message.action.nickname }} | </span>
                                    <a href="#" v-if="message.notice.actionContent"
                                       @click="single_weibo_page(message.notice.diaryId)"><span> {{ message.notice.actionContent }}</span></a>
                                </div>
                                <div class="time_and_button">
                                    <span class="create_time">{{ message.notice.createTime }}</span>
                                    <el-button class="card_button have_read" type="text" style="color: #409eff"
                                               @click="have_read($event, message.notice.id)">
                                        标为已读
                                    </el-button>
                                    <el-button class="card_button" type="text" style="color: #f56c6c"
                                               @click="del_notice($event, message.notice.id)">删除
                                    </el-button>
                                </div>
                            </el-card>
                        </div>
                        <div class="card_outer_div" v-for="message in this.message_lis" v-if="message.notice.isRead">
                            <el-card class="message_card read clearfix" shadow="never">
                                <div class="message_content">
                                    <a href="#"
                                       @click="other_user_page(message.sender.id)"><span>{{ message.sender.nickname }} -> </span></a>
                                    <span>{{ message.action.nickname }} | </span>
                                    <a href="#" v-if="message.notice.actionContent"
                                       @click="single_weibo_page(message.notice.diaryId)"><span> {{ message.notice.actionContent }}</span></a>
                                </div>
                                <div class="time_and_button">
                                    <span class="create_time">{{ message.notice.createTime }}</span>
                                    <el-button class="card_button" type="text" style="color: #f56c6c"
                                               @click="del_notice($event, message.notice.id)">删除
                                    </el-button>
                                </div>
                            </el-card>
                        </div>
                    </el-col>
                </el-drawer>
            </div>
        </nav>
    </div>
</template>

<script>
    import Login from './Login'
    import Register from './Register'
    import qs from 'qs'

    export default {
        name: "Header",

        data() {
            return {
                isLogin: false,
                isRegister: false,
                token: '',
                drawer: false,
                new_message: false,
                message_lis: null,
            }
        },

        methods: {
            // 单个通知变为已读
            have_read(e, id) {
                this.$axios({
                    method: 'post',
                    url: '/api/notice/change_notice',
                    headers: {Authorization: this.token},
                    data: qs.stringify({
                        noticeId: id
                    })
                }).then(res => {
                    // 将通知内的文本变为灰色
                    // console.log(res.data);
                    let time_and_button = e.target.parentNode.previousElementSibling
                    time_and_button.style.color = '#a4a4a4'
                    let message_content = time_and_button.parentNode.previousSibling
                    message_content.children[0].firstElementChild.style.color = '#a4a4a4'
                    message_content.children[1].style.color = '#a4a4a4'
                    if (message_content.children[2]) {
                        message_content.children[2].firstElementChild.style.color = '#a4a4a4'
                    }
                    // 将通知标签移到最下面
                    let notice_ele = message_content.parentNode.parentNode.parentNode
                    notice_ele.parentNode.appendChild(notice_ele)
                    // 删除当前通知标签的标为未读按钮
                    e.target.parentNode.remove()
                }).catch(error => {

                })
            },

            // 全部变为已读
            have_read_all() {
                this.$axios({
                    method: 'post',
                    url: '/api/notice/change_notice',
                    headers: {Authorization: this.token},
                    data: qs.stringify({
                    })
                }).then(res => {
                    for (let e = 0; e < $('.unread').length; e++) {
                        // 将通知内的文本变为灰色
                        let ele = $('.unread')[e]
                        let message_content = $(ele).children().children().first()
                        $(message_content).find('span').css('color', '#a4a4a4')
                        $(message_content).next().children().first().css('color', '#a4a4a4')
                        // 删除所有通知标签的标为未读按钮
                        if ($('.have_read').length) {
                            $('.have_read').remove()
                        }
                    }
                }).catch(error => {
                })
            },

            // 删除单个消息
            del_notice(e, id) {
                this.$confirm('确认删除这条消息吗？', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        method: 'post',
                        url: '/api/notice/delete_notice',
                        headers: {Authorization: this.token},
                        data: {
                            noticeId: id,
                        }
                    }).then(res => {
                        // 删除当前消息标签
                        e.target.parentNode.parentNode.parentNode.parentNode.parentNode.remove()
                        // 检查是否还有消息标签，如果没有说明全删完了，清空消息列表，以显示暂无消息标签
                        if ($('.card_outer_div').length == 0) {
                            this.message_lis = null
                        }
                    }).catch(error => {
                    })
                }).catch(() => {
                })
            },

            // 删除全部消息
            del_all_notice() {
                this.$confirm('确认删除全部消息吗？', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        method: 'post',
                        url: '/api/notice/delete_notice',
                        headers: {Authorization: this.token},
                        data: {
                        }
                    }).then(res => {
                        // 删除所有消息标签
                        $('.card_outer_div').remove()
                        // 将消息列表置空，以显示暂无消息标签
                        this.message_lis = null
                    }).catch(error => {
                    })
                }).catch(() => {
                })
            },


            // 打开消息抽屉的点击事件
            read_message() {
                this.drawer = true;
                this.new_message = false
                this.$axios({
                    method: 'get',
                    url: '/api/notice/all_notice',
                    headers: {Authorization: this.token},
                    params: {}
                }).then(res => {
                    // console.log(res.data)
                    this.message_lis = res.data.notices
                    // console.log(this.message_lis)
                }).catch(error => {
                    console.log(error)
                })
            },

            // 打开新标签页，跳转到个人页面
            other_user_page(other_id) {
                if (this.userId === other_id) {  // 如果点击的是自己的头像，就进入 MyWeibo 的页面
                    let route = this.$router.resolve({
                        name: 'MyWeibo',
                    })
                    window.open(route.href, '_blank')
                } else {
                    let route = this.$router.resolve({
                        name: 'OtherWeibo',
                    })
                    sessionStorage.setItem('other_id', other_id);
                    window.open(route.href, '_blank')
                }
            },

            // 打开新标签页，跳转到单个微博页面
            single_weibo_page(actionId) {
                console.log(actionId)
                let route = this.$router.resolve({
                    name: 'SingleWeibo',
                })
                sessionStorage.setItem('single_weibo_id', actionId);
                window.open(route.href, '_blank')
            },

            // 变为登录页面
            put_login() {
                this.isLogin = true;
                this.isRegister = false;
            },

            // 变为注册页面
            put_register() {
                this.isLogin = false;
                this.isRegister = true;
            },

            // 用户登录成功后使用定时器延迟 1.5 秒刷新页面，为的是让登陆成功的提示成功显示并给首页微博显示小三角
            login_success() {
                clearTimeout(this.timer);
                this.timer = setTimeout(() => {
                    location.reload();
                }, 1500)
            },

            // 退出登录时删除 cookie 并将多个变量置为空
            login_out() {
                this.$store.commit('logout')
                this.$cookies.remove('token');
                this.token = ''
            },

            close_login() {
                this.isLogin = false;
            },

            close_register() {
                this.isRegister = false;
            }
        },

        computed: {
            nickname() {
                return this.$store.state.nickname
            }
        },

        components: {
            Login,
            Register
        },

        created() {
            // console.log(this.$store.state.id)
            if (this.$cookies.get('token') !== 'undefined')
                this.token = this.$cookies.get('token')

            // 页面创建时如果有 token 就去检查是否有未读消息
            if (this.token) {
                this.$axios({
                    method: 'get',
                    url: '/api/notice/new_notice',
                    headers: {Authorization: this.token},
                    params: {}
                }).then(res => {
                    this.new_message = res.data.code === 200;
                    // $('.navbar-toggle').css('border', '1px solid red')
                }).catch(error => {
                })
            } else {
            }
        }
    }
</script>


<style scoped>
    /* 消息中心样式>>> */
    ::v-deep .el-drawer__header {
        color: #1c1d1d;
        font-size: x-large;
        font-weight: 300;
        display: flex;
        padding: 20px 20px 22px 20px;
        flex-wrap: wrap;
        align-items: flex-start;
        justify-content: flex-start;
        flex-direction: column;
        border-bottom: 1px solid rgb(249, 151, 9);
        margin-left: 5%;
        margin-right: 5%;
        margin-bottom: 0.5em;
    }

    ::v-deep .el-card__body {
        padding: 15px 15px 0 15px;
    }

    .message_card {
        margin: 2px 10px;
        text-align: left;
        position: relative;
    }

    .message_card:hover {
        box-shadow: 0 4px 8px rgba(0, 0, 0, .12);
        margin: 5px 0;
    }

    .create_time {
        float: left;
        font-size: small;
        color: grey;
        margin-top: 0.8em;
    }

    .card_button {
        margin-left: 0.3em;
    }

    .read_and_delete_button {
        margin: 5px 0 15px 0;
    }

    .unread a {
        text-decoration: none;
        color: rgb(249, 151, 9);
    }

    .read a {
        text-decoration: none;
        color: #a4a4a4;
    }

    .read span {
        color: #a4a4a4;
    }

    .time_and_button {
        text-align: right;
    }

    /* <<<消息中心样式 */


    .navbar-header {
        width: 100%;
        position: relative;
    }

    .navbar-right {
        margin-right: 0 !important;
    }

    .head_logo {
        width: 33px;
        height: 33px;
    }

    .navbar-brand {
        padding-top: 10px;
        padding-left: 15px;
    }

    .navbar.navbar-default.navbar-fixed-top {
        border-top-width: 2px;
        border-top-color: rgb(249, 151, 9);
    }

    .navbar-default {
        background-color: rgb(59, 59, 59);
        border-color: rgb(59, 59, 59);
        margin-bottom: 0;
    }

    .navbar > .container .navbar-brand, .navbar > .container-fluid .navbar-brand {
        margin-left: 30px;
    }

    .navbar {
        border-radius: 0;
    }

    .navbar-default .navbar-nav > li > a {
        color: #cfcfcf;
    }

    .navbar-default .navbar-nav > li > a:hover {
        color: #ffffff;
        font-weight: bold;
    }

    .form-group input {
        width: 12em;
    }


    .btn.btn-default {
        margin-top: 1px; /* 修复手机端横屏时搜索按钮高于输入框的问题 */
    }

    #message_button {
        font-size: 15px;
        font-weight: 400;
        padding-top: 14px;
        line-height: 20px;
        color: #cfcfcf;
        cursor: pointer
    }


    #message_button:hover {
        color: white;
        font-weight: bold;
    }

    ::v-deep .el-drawer__close-btn {
        display: none;
    }

    @media screen and (max-width: 765px) {
        .navbar-toggle {
            border: 0;
            position: relative;
        }

        .navbar-header {
            margin-left: 0 !important;
            margin-right: 0 !important;
        }

        .navbar-brand {
            margin-left: 0 !important;
            padding: 10px 5px 0 10px !important;
        }

        .nav.navbar-nav.navbar-right {
            margin-left: 0 !important;
            float: right;
            margin-top: 0 !important;
            margin-bottom: 0 !important;
            padding-top: 7px !important;
        }

        .nav > li {
            display: inline-block;
        }

        .navbar-right span {
            display: none;
        }

        #bs-example-navbar-collapse-1 {
            border-top-width: 0;
        }

        .navbar-form.navbar-left {
            margin-top: 0;
            margin-bottom: 0;
            border-top-width: 0;
            border-bottom-width: 0;
            padding-left: 10px !important;
            padding-right: 10px !important;
        }

        .form-group {
            display: inline;
        }

        .form-group input {
            margin-bottom: 5px;
        }

        .form-control {
            width: 100% !important;
        }

        .nav.navbar-nav.navbar-right {
            padding-top: 0;
            float: none;
        }

        #message_button span {
            display: none;
        }

        .el-badge {
            vertical-align: unset;
        }
    }
</style>
