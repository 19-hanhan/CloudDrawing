<template>
    <div class="follows_middle">
        <div id="top_block"></div>
        <div class="container">
            <div class="col-md-8 col-md-offset-2">
                <div id="d1">
                    <div id="d1_1">
                        <p>我的关注</p>
                    </div>
                    <el-divider></el-divider>  <!-- 分隔线 -->
                    <div v-if="follows_lis" id="d1_2">
                        <div v-for="follow in follows_lis" :key="follow.nickname" class="follow">
                            <a href="#" @click="other_user_page(follow.id)">
                                <img :src="follow.icon" alt="">
                                <span>{{ follow.nickname }}</span>
                            </a>
                            <el-button type="danger" round size="mini" @click="change_follow(userId, follow.id)">取消关注
                            </el-button>
                        </div>
                    </div>
                    <div v-if="!follows_lis" id="d1_3">
                        <p>没有关注</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import qs from 'qs';

    export default {
        name: "Follows_Middle",
        data() {
            return {
                userId: this.$cookies.get('userId'),
                follows_lis: null,
            }
        },
        methods: {
            change_follow(userId, unfollowId) {
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
                            userId: userId,
                            followId: unfollowId
                        })
                    }).then(response => {
                        // 修改成功显示成功提示，持续 1.5 秒
                        this.$message({
                            type: 'success',
                            message: response.data.msg,
                            duration: 1500,
                            center: true
                        });
                        // 设置定时器，1.5 秒以后刷新页面以便重新显示当前关注的用户
                        clearTimeout(this.timer);
                        this.timer = setTimeout(() => {
                            location.reload();
                        }, 1500)
                    }).catch(error => {
                        this.$message({
                            type: 'error',
                            message: '出现错误，请重试！',
                            duration: 1500,
                            center: true
                        });
                        clearTimeout(this.timer);
                        this.timer = setTimeout(() => {
                            location.reload();
                        }, 1500)
                    })
                }
            },

            // 去其他人的页面
            other_user_page(other_id) {
                let route = this.$router.resolve({
                    name: 'OtherWeibo',
                })
                sessionStorage.setItem('other_id', other_id);
                window.open(route.href, '_blank')
            },
        },
        created() {
            // 获取所有关注者
            this.$axios.get('/api/user/get_favorite',
                {params: {userId: this.userId}}
            ).then(response => {
                this.follows_lis = response.data.result;
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
        width: 58px;
        height: 58px;
        border-radius: 50%;
    }

    #d1_2 div span {
        margin-left: 20px;
    }

    .follow {
        position: relative;
    }

    .follow button {
        position: absolute;
        right: 2%;
        top: 20%;
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

        .follow button {
            position: absolute;
            /*right: 2%;*/
            top: 10%;
        }
    }
</style>
