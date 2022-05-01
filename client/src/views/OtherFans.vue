<template>
  <div class="other_fans">
    <Head/>
    <Other_Fans_Middle/>
    <div
        class="goTop iconfont ml-gotop"
        :class="isTop ? 'goTopAfter' : ''"
        @click="goTop()"
    >Top
    </div>
    <Footer/>
  </div>
</template>

<script>
import Head from "../components/Header";
import Footer from "../components/Footer";
import Other_Fans_Middle from "../components/Other_Fans_Middle";

export default {
  name: "OtherFans",
  components: {
    Other_Fans_Middle,
    Head,
    Footer
  },
  data() {
    return {
      scrollNum: 0, //滚动距离
      isTop: false, //是否显示回到顶部按钮
      myTimer: -1, //防止点击事件过度
    }
  },

  mounted() {
    window.addEventListener("scroll", () => {
      let top =
          document.documentElement.scrollTop ||
          document.body.scrollTop ||
          window.pageYOffset;
      this.scrollNum = top;
      if (top >= 100) {
        this.isTop = true;
      } else {
        this.isTop = false;
      }
    });
  },

  methods: {
    goTop() {
      if (this.myTimer == -1) {
        this.myTimer = setInterval(() => {
          this.scrollNum -= 60;
          if (this.scrollNum <= 0) {
            this.scrollNum = 0;
            window.clearInterval(this.myTimer); //停止执行
            this.myTimer = -1;
          }
          window.scrollTo(0, this.scrollNum); //离开网页顶部的距离
        }, 10);
      }
    },
  }
}
</script>

<style scoped>
.goTop {
  position: fixed;
  bottom: -100px;
  right: 12%;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  z-index: 100;
  background-color: rgba(249, 151, 9, 0.7);
  font-size: 1em;
  text-align: center;
  line-height: 50px;
  color: #ffffff;
  transition: 0.3s ease-in-out;
  cursor: pointer;
}

.goTop:hover {
  background-color: rgb(249, 151, 9);
  transition: 0.3s ease-in-out;
}

.goTopAfter {
  transition: 0.3s ease-in-out;
  bottom: 10%;
}
</style>
