<template>
    <div class="fans_middle">
        <div id="top_block"></div>
        <div class="container">
            <div class="col-md-8 col-md-offset-2">
                <div id="d1">
                    <div id="d1_1">
                        <p>我的粉丝</p>
                    </div>
                    <el-divider></el-divider>
                    <div v-if="fans_lis" id="d1_2">
                        <div v-for="fans in fans_lis" :key="fans.user.nickname" class="fans">
                            <a href="#" @click="other_user_page(fans.user.id)">
                                <img :src="fans.user.avatar" alt="">
                                <span>{{ fans.user.nickname }}</span>
                            </a>
                            <el-button v-if="fans.isFollow" type="danger" round size="mini"
                                       @click="change_follow(userId, fans.user.fansId)">取消关注
                            </el-button>
                            <el-button v-if="!fans.isFollow" type="success" round size="mini"
                                       @click="change_follow(userId, fans.user.fansId)">关&nbsp;&nbsp;注
                            </el-button>
                        </div>
                    </div>
                    <div v-if="!fans_lis" id="d1_3">
                        <p>没有粉丝</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import qs from 'qs';

    export default {
        name: "Fans_Middle",
        data() {
            return {
                userId: this.$cookies.get('userId'),
                fans_lis: null,
                no_fans_msg: '',
            }
        },
        methods: {
            other_user_page(otherId) {
                let route = this.$router.resolve({
                    name: 'OtherWeibo',
                })
                sessionStorage.setItem('otherId', otherId);
                window.open(route.href, '_blank')
            },

            change_follow(userId, fansId) {
                if (!this.$cookies.get('userId')) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.$axios({
                        method: 'post',
                        url: '/api/user/change_follow',
                        headers: {Authorization: this.$cookies.get('token')},
                        data: qs.stringify({
                            followId: fansId  // 修改关注状态的用户
                        })
                    }).then(response => {
                        this.$message({
                            message: response.data.msg,
                            type: 'success',
                            center: true,
                            duration: 1500
                        });
                        // 设置定时器，1.5 秒以后刷新页面以便重新显示当前关注的用户
                        clearTimeout(this.timer);
                        this.timer = setTimeout(() => {
                            location.reload();
                        }, 1500)
                    }).catch(error => {
                        this.$message({
                            message: '出现错误，请重试！',
                            type: 'error',
                            center: true,
                            duration: 1500
                        });
                        clearTimeout(this.timer);
                        this.timer = setTimeout(() => {
                            location.reload();
                        }, 1500)
                    })
                }
            }
        },
        created() {
            this.userId = this.$cookies.get('userId');
            // 获取所有粉丝
            this.$axios.get('/api/user/get_fans',
                {params: {userId: this.userId}}
            ).then(response => {
                this.fans_lis = response.data.result;
                console.log(this.fans_lis)
            }).catch(error => {
                console.log(error);
            })
        }
    }
</script>

<style scoped>
    #top_block {
        margin-top: 5em;
    }

    .container {
        min-height: 1177px;
    }

    #d1_1 {
        padding-top: 5px;
    }

    #d1_1 p {
        font-size: xx-large;
        text-align: left;
    }

    .el-divider {
        margin-top: 6px;
    }

    #d1_2 div {
        text-align: left;
        margin-bottom: 25px;
    }

    #d1_2 div img {
        /*width: 8%;*/
        border-radius: 50%;
        width: 58px;
        height: 58px;
    }

    #d1_2 div span {
        margin-left: 3%;
    }

    .fans {
        position: relative;
    }

    .fans button {
        position: absolute;
        right: 2%;
        top: 20%;
    }

    .el-button--success {
        background-color: rgb(249, 151, 9);
        border-color: rgb(249, 151, 9);
    }

    .el-button--success:hover {
        background-color: rgb(246, 182, 88);
        border-color: rgb(246, 182, 88);
    }

    #d1_3 p {
        font-size: large;
        color: #909399;
    }

    a:hover {
        text-decoration: none;
        color: rgb(249, 151, 9);
    }

    @media screen and (max-width: 600px) {
        #top_block {
            margin-top: 62px;
        }

        #d1_2 div img {
            /*width: 12%;*/
            width: 45px;
            height: 45px;
        }

        .fans button {
            position: absolute;
            top: 10%;
        }
    }
</style>
