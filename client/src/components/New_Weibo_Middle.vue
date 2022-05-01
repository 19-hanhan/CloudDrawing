<template>
  <div class="new_weibo_middle">
    <div id="top_block"></div>
    <div class="container">
      <div class="col-md-2">
        <Middle_Left/>
      </div>
      <div class="col-md-7">
        <div class="new_weibo">
          <div id="new_weibo_top" class="clearfix">
            <span>有什么新鲜事想告诉大家？</span>
          </div>
          <div id="new_weibo_middle">
            <el-input
                id="ipt"
                ref="weibo"
                type="textarea"
                :autosize="{ minRows: 3}"
                placeholder=""
                v-model="textarea"
                maxlength="500"
                show-word-limit
                resize="none">
            </el-input>
          </div>
          <div id="new_weibo_bottom" class="clearfix">
            <div class="bottom_left clearfix">
              <el-popover
                  popper-class="biaoqing"
                  placement="bottom-start"
                  title=""
                  width="200"
                  trigger="click">
                <div class="faces">
                  <ul>
                    <li title="微笑" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/e3/2018new_weixioa02_org.png">
                    </li>
                    <li title="可爱" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/09/2018new_keai_org.png">
                    </li>
                    <li title="太开心" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/1e/2018new_taikaixin_org.png">
                    </li>
                    <li title="鼓掌" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/6e/2018new_guzhang_thumb.png">
                    </li>
                    <li title="嘻嘻" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/33/2018new_xixi_thumb.png">
                    </li>
                    <li title="哈哈" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/8f/2018new_haha_thumb.png">
                    </li>
                    <li title="笑cry" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/4a/2018new_xiaoku_thumb.png">
                    </li>
                    <li title="挤眼" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/43/2018new_jiyan_org.png">
                    </li>
                    <li title="馋嘴" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/fa/2018new_chanzui_org.png">
                    </li>
                    <li title="黑线" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a3/2018new_heixian_thumb.png">
                    </li>
                    <li title="汗" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/28/2018new_han_org.png">
                    </li>
                    <li title="挖鼻" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/9a/2018new_wabi_thumb.png">
                    </li>
                    <li title="哼" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/7c/2018new_heng_thumb.png">
                    </li>
                    <li title="怒" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/f6/2018new_nu_thumb.png">
                    </li>
                    <li title="委屈" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a5/2018new_weiqu_thumb.png">
                    </li>
                    <li title="可怜" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/96/2018new_kelian_org.png">
                    </li>
                    <li title="失望" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/aa/2018new_shiwang_thumb.png">
                    </li>
                    <li title="悲伤" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/ee/2018new_beishang_org.png">
                    </li>
                    <li title="泪" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/6e/2018new_leimu_org.png">
                    </li>
                    <li title="允悲" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/83/2018new_kuxiao_org.png">
                    </li>
                    <li title="害羞" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/c1/2018new_haixiu_org.png">
                    </li>
                    <li title="污" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/10/2018new_wu_thumb.png">
                    </li>
                    <li title="爱你" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/f6/2018new_aini_org.png">
                    </li>
                    <li title="亲亲" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/2c/2018new_qinqin_thumb.png">
                    </li>
                    <li title="色" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/9d/2018new_huaxin_org.png">
                    </li>
                    <li title="憧憬" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/c9/2018new_chongjing_org.png">
                    </li>
                    <li title="舔屏" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/3e/2018new_tianping_thumb.png">
                    </li>
                    <li title="坏笑" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/4d/2018new_huaixiao_org.png">
                    </li>
                    <li title="阴险" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/9e/2018new_yinxian_org.png">
                    </li>
                    <li title="笑而不语" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/2d/2018new_xiaoerbuyu_org.png">
                    </li>
                    <li title="偷笑" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/71/2018new_touxiao_org.png">
                    </li>
                    <li title="酷" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/c4/2018new_ku_org.png">
                    </li>
                    <li title="并不简单" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/aa/2018new_bingbujiandan_thumb.png">
                    </li>
                    <li title="思考" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/30/2018new_sikao_org.png">
                    </li>
                    <li title="疑问" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/b8/2018new_ningwen_org.png">
                    </li>
                    <li title="费解" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/2a/2018new_wenhao_thumb.png">
                    </li>
                    <li title="晕" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/07/2018new_yun_thumb.png">
                    </li>
                    <li title="衰" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a2/2018new_shuai_thumb.png">
                    </li>
                    <li title="骷髅" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a1/2018new_kulou_thumb.png">
                    </li>
                    <li title="嘘" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/b0/2018new_xu_org.png">
                    </li>
                    <li title="闭嘴" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/62/2018new_bizui_org.png">
                    </li>
                    <li title="傻眼" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/dd/2018new_shayan_org.png">
                    </li>
                    <li title="吃惊" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/49/2018new_chijing_org.png">
                    </li>
                    <li title="吐" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/08/2018new_tu_org.png">
                    </li>
                    <li title="感冒" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/2018new_kouzhao_thumb.png">
                    </li>
                    <li title="生病" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/3b/2018new_shengbing_thumb.png">
                    </li>
                    <li title="拜拜" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/fd/2018new_baibai_thumb.png">
                    </li>
                    <li title="鄙视" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/da/2018new_bishi_org.png">
                    </li>
                    <li title="白眼" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/ef/2018new_landelini_org.png">
                    </li>
                    <li title="左哼哼" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/43/2018new_zuohengheng_thumb.png">
                    </li>
                    <li title="右哼哼" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/c1/2018new_youhengheng_thumb.png">
                    </li>
                    <li title="抓狂" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/17/2018new_zhuakuang_org.png">
                    </li>
                    <li title="怒骂" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/87/2018new_zhouma_thumb.png">
                    </li>
                    <li title="打脸" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/cb/2018new_dalian_org.png">
                    </li>
                    <li title="顶" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/ae/2018new_ding_org.png">
                    </li>
                    <li title="互粉" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/86/2018new_hufen02_org.png">
                    </li>
                    <li title="钱" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a2/2018new_qian_thumb.png">
                    </li>
                    <li title="哈欠" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/55/2018new_dahaqian_org.png">
                    </li>
                    <li title="困" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/3c/2018new_kun_thumb.png">
                    </li>
                    <li title="睡" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/e2/2018new_shuijiao_thumb.png">
                    </li>
                    <li title="求饶" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/aa/moren_qiurao02_thumb.png">
                    </li>
                    <li title="吃瓜" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/01/2018new_chigua_thumb.png">
                    </li>
                    <li title="打call" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/39/moren_dacall02_thumb.png">
                    </li>
                    <li title="awsl" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/moren_awsl02_thumb.png">
                    </li>
                    <li title="苦涩" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/7e/2021_bitter_thumb.png">
                    </li>
                    <li title="裂开" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/1b/202011_liekai_thumb.png">
                    </li>
                    <li title="doge" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a1/2018new_doge02_org.png">
                    </li>
                    <li title="二哈" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/22/2018new_erha_org.png">
                    </li>
                    <li title="喵喵" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/7b/2018new_miaomiao_thumb.png">
                    </li>
                    <li title="酸" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/b3/hot_wosuanle_thumb.png">
                    </li>
                    <li title="中国赞" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/2018new_zhongguozan_org.png">
                    </li>
                    <li title="抱抱" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/42/2018new_baobao_thumb.png">
                    </li>
                    <li title="摊手" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/62/2018new_tanshou_org.png">
                    </li>
                    <li title="跪了" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/75/2018new_gui_org.png">
                    </li>
                    <li title="鲜花" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/d4/2018new_xianhua_org.png">
                    </li>
                    <li title="红灯笼" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/e8/2021_lantern_thumb.png">
                    </li>
                    <li title="烟花" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/96/2021_fireworks_thumb.png">
                    </li>
                    <li title="雪花" @click="input_faces($event)">
                      <img
                          src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/13/yunying2020_snowflakes_thumb.png">
                    </li>
                    <li title="丘比特" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/45/2021_qiubite_thumb.png">
                    </li>
                    <li title="单身狗" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/20/2021_alongdog_thumb.png">
                    </li>
                    <li title="抱一抱" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/af/2020_hug_thumb.png">
                    </li>
                    <li title="小丑" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/6f/2021_xiaochou_thumb.png">
                    </li>
                    <li title="杰瑞" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/da/2021_jerry_thumb.png">
                    </li>
                    <li title="汤姆" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/31/2021_tom_thumb.png">
                    </li>
                    <li title="奶瓶" @click="input_faces($event)">
                      <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/8c/2021_naiping_thumb.png">
                    </li>
                  </ul>
                </div>
                <el-button slot="reference">😊 表情</el-button>
              </el-popover>
            </div>
            <div class="bottom_right clearfix">
              <el-button type="warning" size="small" :disabled="!textarea" @click="post_on">发布</el-button>
            </div>
          </div>
        </div>
        <div class="upload_picture clearfix">
          <p>上传照片</p>
          <div class="pic clearfix">
            <el-upload
                id="ipt-pic"
                class="upload-demo"
                action=""
                multiple
                accept="image/gif, image/jpeg, image/jpg, image/png"
                :limit="9"
                :on-exceed="handleExceed"
                :auto-upload="false"
                :before-remove="beforeRemove"
                :on-remove="handleRemove"
                :on-change="handleChange"
                :file-list="fileList"
                list-type="picture">
              <el-button size="small" type="primary">选择图片</el-button>
              <div slot="tip" class="el-upload__tip">最多上传 9 张图片</div>
            </el-upload>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <Middle_Right/>
      </div>
    </div>
  </div>
</template>

<script>
import Middle_Left from "./Middle_Left";
import Middle_Right from "./Middle_Right";
import qs from "qs";

export default {
  name: "New_Weibo_Middle",
  components: {
    Middle_Left,
    Middle_Right
  },
  data() {
    return {
      textarea: '',
      fileList: [],
      token: this.$cookies.get('token'),
    }
  },
  methods: {
    // 点击表情时
    input_faces(e) {
      let text = document.getElementById('ipt').value;
      let faces_name = '[' + e.currentTarget.getAttribute('title') + ']';
      if (text.length + faces_name.length <= 500) {
        this.insertInputTxt('ipt', faces_name);
        this.textarea = document.getElementById('ipt').value
      }
    },
    // 将表情对应的名称输入 input 框中
    insertInputTxt(id, insertTxt) {
      let elInput = document.getElementById(id); // 获取dom
      let startPos = elInput.selectionStart;
      let endPos = elInput.selectionEnd;
      if (startPos === undefined || endPos === undefined) return;
      let txt = elInput.value;
      let result = txt.substring(0, startPos) + insertTxt + txt.substring(endPos);
      elInput.value = result;
      // 这里比较重要 **给最终绑定的参数 进行赋值
      this.ipt = result; // 赋值
      elInput.focus();
      this.$nextTick(() => {
        elInput.selectionStart = startPos + insertTxt.length;
        elInput.selectionEnd = startPos + insertTxt.length;
      });
    },

    // 文件状态改变时
    handleChange(file, fileList) {
      this.fileList = fileList
    },

    // 移除图片文件时
    handleRemove(file, fileList) {
      this.fileList = fileList
    },

    // 超出上传限制时的提醒
    handleExceed() {
      this.$message.warning(`最多只能上传 9 张图片`);
    },

    // 移除图片文件前
    beforeRemove(file) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    // 点击发布按钮发布微博
    post_on() {
      if (!this.token) {
        this.$message({
          message: '请先登录',
          type: 'warning',
          center: true
        });
        this.$router.push('/')
      } else {
        console.log(this.fileList)
        if (this.fileList.length === 0) {  // 只有文字没有图片的情况
          console.log(this.$cookies.get('userId'))
          console.log(this.textarea)
          this.$axios({
            method: 'post',
            url: '/api/diary/add_diary',
            headers: {Authorization: this.token},
            data: qs.stringify({
              userId: this.$cookies.get('userId'),
              content: this.textarea,
            }),
          }).then(response => {
            if (response.data.code === 200) {
              console.log(response.data.result)
              this.$message({
                message: '发布成功',
                type: 'success',
                center: true
              });
            } else {
              console.log(response.data.result)
              this.$message({
                message: '发布失败',
                type: 'error',
                center: true
              });
            }
            this.textarea = ''
            this.fileList = []
          }).catch(errors => {
            this.$message({
              message: '未知错误，请重试',
              type: 'error',
              center: true
            });
          })
        } else {  // 文字和图片都有的情况
          try {
            // 创建一个空的FormData对象:
            const formData = new FormData()
            this.fileList.forEach((file) => {
              // const isIMAGE = file.type === 'image/gif' || 'image/jpeg' || 'image/jpg' || 'image/bmp' || 'image/png';
              const isIMAGE = file.type === 'image/gif' || 'image/jpeg' || 'image/jpg' || 'image/png';
              const isLt5M = file.size / 1024 / 1024 < 5;
              if (!isIMAGE) {
                this.$message({
                  message: '上传图片只能是图片格式',
                  type: 'error',
                  center: true
                });
                throw new Error('EndIterative');  // 结束整体循环
              }
              if (!isLt5M) {
                this.$message({
                  message: '单张图片大小不能超过 5MB！',
                  type: 'warning',
                  center: true
                });
                throw new Error('EndIterative');  // 结束整体循环
              }
              // 使用FormData.append来添加键/值对到表单里面
              formData.append('file', file.raw)
            })
            formData.append('user', this.$cookies.get('userId'))
            formData.append('content', this.textarea)
            this.$axios({
              method: 'post',
              url: '/api/diary/add_diary',
              headers: {Authorization: this.token},
              data: formData
            }).then(response => {
              if (response.data.code === 200) {
                this.$message({
                  message: '发布成功',
                  type: 'success',
                  center: true
                });
              } else {
                this.$message({
                  message: '发布失败',
                  type: 'error',
                  center: true
                });
              }
              this.textarea = ''
              this.fileList = []
            }).catch(errors => {
              this.$message({
                message: '未知错误，请重试',
                type: 'error',
                center: true
              });
            })
          } catch (e) {
          }
        }
      }

    }
  },
}
</script>


<style scoped>

.clearfix:after { /*具有clearfix类属性的标签后面添加*/
  content: ''; /*添加的内容为空*/
  display: block; /*让空内容具有块级标签的属性：独占一行*/
  clear: both; /*空内容左右两侧不能有浮动的标签*/
}

#top_block {
  margin-top: 5em;
}

.container {
  min-height: 1177px;
}

.new_weibo {
  background-color: #f5f5f5;
}

#new_weibo_top {
  padding-top: 10px;
  padding-bottom: 10px;
}

#new_weibo_top span {
  float: left;
  padding-left: 2%;
}

.el-textarea {
  width: 96%;
  margin-bottom: 5px;
}

.el-button.el-button--default.el-popover__reference {
  border-left-width: 0;
  float: left;
  border-top-width: 0;
  border-right-width: 0;
  border-bottom-width: 0;
  background-color: #f5f5f5;
  padding-right: 0;
}

.faces ul {
  list-style: none;
  display: block;
}

.faces ul li {
  cursor: pointer;
  float: left;
  border: 1px solid #e8e8e8;
  height: 35px;
  width: 35px;
  overflow: hidden;
  margin: -1px 0 0 -1px;
  padding: 6px 4px;
  text-align: center;
  display: list-item;
}

.faces ul li:hover {
  background-color: #e8e8e8;
}

.faces ul li img {
  width: 22px;
  height: 22px;
}

.bottom_left {
  float: left;
}

.bottom_right {
  float: right;
  margin-right: 3%;
  margin-bottom: 10px;
}

.upload_picture.clearfix {
  padding-top: 25px;
}

.upload_picture p {
  text-align: left;
  background-color: rgb(245, 245, 245);
  padding: 10px 2%;
}

.pic.clearfix {
  background-color: #f5f5f5;
  padding-left: 2%;
  padding-right: 2%;
}

#ipt-pic {
  padding-bottom: 10px;
}

.col-md-3 {
  padding-right: 0;
  padding-left: 0;
}

@media screen and (max-width: 1200px) {
  .col-md-2 {
    display: none;
  }

  .col-md-3 {
    display: none;
  }

  .bottom_right {
    margin-right: 2%;
  }
}

@media screen and (max-width: 765px) {
  .container {
    padding-left: 0;
    padding-right: 0;
  }
}
</style>
