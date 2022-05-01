<template>
  <div class="register">
    <div class="box">
      <i class="el-icon-close" @click="close_register"></i>
      <div class="content">
        <div class="nav">
          <span class="active">注&nbsp;&nbsp;册</span>
        </div>
        <el-form>
          <el-input
              placeholder="微博昵称：最多10个字符"
              prefix-icon="el-icon-info"
              v-model="nickname"
              clearable>
          </el-input>
          <el-input
              placeholder="用户名：5-10个字符"
              prefix-icon="el-icon-user"
              v-model="username"
              clearable>
          </el-input>
          <el-input
              placeholder="密码：8-18个字符"
              prefix-icon="el-icon-key"
              v-model="password"
              clearable
              show-password>
          </el-input>
          <el-input
              placeholder="确认密码：8-18个字符"
              prefix-icon="el-icon-key"
              v-model="confirmPassword"
              clearable
              @keyup.enter.native="triggerClick"
              show-password>
          </el-input>
          <el-button @click="register" ref="btn" type="primary">注册</el-button>
        </el-form>
        <div class="foot">
          <span @click="go_login">已有账号 去登录</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      nickname: '',
      username: '',
      password: '',
      confirmPassword: ''
    }
  },
  methods: {
    triggerClick() {
      this.$refs.btn.$emit('click')
    },
    close_register() {
      this.$emit('close', false)
    },
    go_login() {
      this.$emit('go')
    },
    register() {
      if (this.nickname && this.username && this.password && this.confirmPassword) {
        this.$axios.post('/api/user/register', {
          nickname: this.nickname,
          username: this.username,
          password: this.password,
          confirmPassword: this.confirmPassword
        }).then(response => {
          if (response.data.code === 201) {
            this.$message({
              message: '注册成功，请登录',
              type: 'success',
              duration: 1500,
              center: true,
              onClose: () => {
                this.go_login()  // 子传父事件，跳转到登录界面
              }
            })
          } else if (response.data.code === 1) {
            this.$message({
              message: response.data.msg,
              type: 'error',
              duration: 1500,
              center: true,
              onClose: () => {  // 清空框中的内容
                this.nickname = ''
                this.username = ''
                this.password = ''
                this.confirmPassword = ''
              }
            })
          } else {
            this.$message({
              message: '未知错误，请重试',
              type: 'error',
              duration: 1500,
              center: true,
              onClose: () => {  // 清空框中的内容
                this.nickname = ''
                this.username = ''
                this.password = ''
                this.confirmPassword = ''
              }
            })
          }
        }).catch(errors => {
        })
      } else {
        this.$message({
          message: '是不是少了点什么啊',
          type: 'error',
          duration: 1500,
          center: true,
        })
      }
    }
  }
}
</script>

<style scoped>
.register {
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
  height: 550px;
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
  color: darkgrey;
  user-select: none;
  cursor: pointer;
  padding-bottom: 10px;
}

.nav > span.active {
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

.foot > span {
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
    height: 550px;
    background-color: white;
    border-radius: 10px;
    position: relative;
    top: calc(50vh - 240px);
    left: 0;
  }
}
</style>
