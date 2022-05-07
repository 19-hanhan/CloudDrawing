<template>
  <div class="other_follows_middle">
    <div id="top_block"></div>
    <div class="container">
      <div class="col-md-8 col-md-offset-2">
        <div id="d1">
          <div id="d1_1">
            <p>Ta的关注</p>
          </div>
          <el-divider></el-divider>  <!-- 分隔线 -->
          <div id="d1_2">
            <div v-for="follow in follows_lis" :key="follow.user.nickname" class="follow">
              <a href="#" @click="other_user_page(follow.user.id)">
                <img :src="follow.user.avatar" alt="">
                <span>{{ follow.user.nickname }}</span>
              </a>
              <el-button v-if="follow.isFollow == 1" type="danger" round size="mini"
                         @click="change_follow(userId, follow.user.id)">取消关注
              </el-button>
              <el-button v-if="follow.isFollow == 0" type="success" round size="mini"
                         @click="change_follow(userId, follow.user.id)">关&nbsp;&nbsp;注
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Other_Follows_Middle",

  data() {
    return {
      follows_lis: [],
      userId: '',
    }
  },

  methods: {
    other_user_page(otherId) {
      if (this.userId === otherId) {
        let route = this.$router.resolve({
          name: 'MyWeibo',
        })
        window.open(route.href, '_blank')
      } else {
        let route = this.$router.resolve({
          name: 'OtherWeibo',
        })
        sessionStorage.setItem('otherId', otherId);
        window.open(route.href, '_blank')
      }
    },

    change_follow(userId, fansId) {
      this.$axios({
        method: 'post',
        url: '/api/user/change_follow',
        headers: {Authorization: this.$cookies.get('token')},
        data: {
          followId: fansId  // 修改关注状态的用户
        }
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
  },

  created() {
    this.userId = this.$cookies.get('userId')
    // 向后端发请求，获取基于当前登录用户和查看用户两人关系的关注列表
    this.$axios.get('/api/user/get_follow', {
      params: {
        userId: sessionStorage.getItem('otherId')
      }
    }).then(response => {
      this.follows_lis = response.data.result
    }).catch(error => {
      this.$message({
        type: 'error',
        message: '请重试',
        duration: 1500,
        center: true,
      });
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

.el-button--success {
  background-color: rgb(249, 151, 9);
  border-color: rgb(249, 151, 9);
}

.el-button--success:hover {
  background-color: rgb(246, 182, 88);
  border-color: rgb(246, 182, 88);
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
    top: 10%;
  }
}
</style>
