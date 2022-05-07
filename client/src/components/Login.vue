<template>
    <div class="login">
        <div class="box">
            <i class="el-icon-close" @click="close_login"></i>
            <div class="content">
                <div class="nav">
                    <span>登&nbsp;&nbsp;录</span>
                </div>
                <el-form>
                    <el-input
                            placeholder="用户名"
                            prefix-icon="el-icon-user"
                            v-model="username"
                            clearable>
                    </el-input>
                    <el-input
                            placeholder="密码"
                            prefix-icon="el-icon-key"
                            v-model="password"
                            clearable
                            @keyup.enter.native="triggerClick"
                            show-password>
                    </el-input>
                    <el-button href="" type="primary" ref="btn" @click="login_password">登录</el-button>
                </el-form>
                <div class="foot">
                    <span id="foot-right" @click="go_register">还没有账号 去注册</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import qs from 'qs';

    export default {
        name: "Login",
        data() {
            return {
                username: '',
                password: '',
            }
        },
        methods: {
            triggerClick() {
                this.$refs.btn.$emit('click')
            },
            close_login() {
                this.$emit('close')
            },
            go_register() {
                this.$emit('go')
            },
            login_password() {
                if (this.username && this.password) {  // 如果用户名和密码都有
                    // 携带数据发送请求
                    this.$axios.post('/api/user/login',
                        qs.stringify({
                            username: this.username,
                            password: this.password,
                        })).then(response => {
                            // console.log(response.data.code)
                            if (response.data.code === 200) {
                                this.$message({
                                    message: '登录成功，' + response.data.user.nickname,
                                    type: 'success',
                                    duration: 1500,
                                    center: true
                                })
                                // 把用户信息保存到store中
                                this.$store.commit('login', response.data.user)
                                this.$emit('close')  // 关闭登录模态框（子传父）
                                this.$emit('loginsuccess')
                            } else {
                                this.$message({
                                    message: response.data.msg.non_field_errors[0],
                                    type: 'error',
                                    duration: 1500,
                                    center: true,
                                    onClose: () => {
                                        this.username = ''
                                        this.password = ''
                                    }
                                })
                        }
                    }).catch(errors => {
                        this.$message({
                            message: '出错了，请重试',
                            type: 'warning',
                            center: true
                        })
                    })
                } else {
                    this.$message({
                        message: '用户名或密码不能为空',
                        type: 'warning',
                        center: true
                    });
                }
            }
        }
    }
</script>

<style scoped>
    .login {
        width: 100vw;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        z-index: 10;
        background-color: rgba(0, 0, 0, 0.3);
    }

    .box {
        width: 400px;
        height: 410px;
        background-color: white;
        border-radius: 10px;
        position: relative;
        top: calc(50vh - 240px);
        left: calc(50vw - 200px);
    }

    .el-icon-close {
        position: absolute;
        font-weight: bold;
        font-size: 20px;
        top: 10px;
        right: 10px;
        cursor: pointer;
    }

    .el-icon-close:hover {
        color: darkred;
    }

    .content {
        position: absolute;
        top: 40px;
        width: 280px;
        left: 60px;
    }

    .nav {
        font-size: 20px;
        height: 38px;
    }

    .nav > span {
        color: black;
        padding-bottom: 9px;
        font-size: larger;
    }

    .el-input, .el-button {
        margin-top: 40px;
    }

    .el-button {
        width: 100%;
        font-size: 18px;
    }

    #foot-right {
        float: right;
        margin-top: 20px;
        color: orange;
        cursor: pointer;
    }

    @media screen and (max-width: 414px) {
        .box .content {
            position: relative;
            width: 70%;
            left: 15%;
        }

        .box {
            width: 90%;
            margin-left: 5%;
            height: 410px;
            background-color: white;
            border-radius: 10px;
            position: relative;
            top: calc(50vh - 240px);
            left: 0;
        }
    }
</style>
