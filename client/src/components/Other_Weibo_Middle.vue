<template>
    <div class="other_weibo_middle">
        <div id="top_block"></div>
        <div class="container">
            <div class="col-md-8 col-md-offset-2">
                <div class="jumbotron">
          <span class="avatar">
            <img :src=this.avatar>
          </span>
                    <p>{{ nickname }}</p>
                    <div class="follow_and_fans">
                        <a href="/OtherFollows"><span>关注 {{ this.followed_num }}</span></a>
                        <span id="s2"> | </span>
                        <a href="/OtherFans"><span>粉丝 {{ this.fans_num }}</span></a>
                    </div>
                    <div class="follow_or_unfollow">
                        <el-button type="warning" round size="mini"
                                   v-if="this.userId && !this.is_follow"
                                   @click="change_follow">关注 Ta
                        </el-button>
                        <el-button type="danger" round size="mini" v-if="this.userId && this.is_follow"
                                   @click="change_follow">取消关注
                        </el-button>
                    </div>
                </div>
                <div class="tabs">
                    <el-tabs v-model="activeName" stretch>
                        <el-tab-pane label="微博" name="first">
                            <div class="weibo-content" v-if="this.all_weibo_lis">
                                <div class="single-weibo" v-for="single_weibo in all_weibo_lis">
                                    <div class="single-weibo-up">
                                        <div class="avatar-name-time clearfix">
                                            <a href="#">
                        <span class="single-weibo-avatar">
                          <img :src="single_weibo.user.avatar">
                        </span>
                                                <ul class="single-weibo-writer">
                                                    <li class="writer-name">{{ single_weibo.user.nickname }}</li>
                                                    <li class="write-time">{{ single_weibo.diary.createTime }}</li>
                                                </ul>
                                            </a>
                                        </div>

                                    </div>
                                    <div class="single-weibo-middle">
                                        <div class="single-weibo-content">
                                            加载中...
                                        </div>
                                        <div class="weibo-media clearfix">
                                            <div class="single-weibo-media" v-for="image in single_weibo.imgs">
                                                <el-image :src="image"
                                                          :preview-src-list="single_weibo.imgs"></el-image>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 评论和点赞 >>> -->
                                    <div class="single-weibo-bottom">
                                        <el-button
                                                @click="show_comment($event, single_weibo.diary.id,)"
                                                type="text"
                                                icon="el-icon-chat-dot-square">{{ single_weibo.diary.commentNum }}
                                        </el-button>
                                        <span class="line">｜</span>
                                        <el-button type="text" icon="glyphicon glyphicon-thumbs-up"
                                                   @click="like_or_unlike($event, single_weibo.diary.id)">{{
                                            single_weibo.diary.likeNum }}
                                        </el-button>
                                    </div>
                                    <!-- <<< 评论和点赞 -->
                                    <!-- 隐藏的评论标签 >>> -->
                                    <div style="display: none" class="comment">
                                        <!-- 评论输入框 >>> -->
                                        <div class="comment_input clearfix">
                                            <el-input
                                                    id="ipt"
                                                    ref="comment"
                                                    type="textarea"
                                                    :autosize="{ minRows: 1}"
                                                    placeholder="写下你的评论..."
                                                    maxlength="255"
                                                    show-word-limit
                                                    v-model="textarea"
                                                    resize="none">
                                            </el-input>
                                            <div class="faces">
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
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/09/2018new_keai_org.png">
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
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/33/2018new_xixi_thumb.png">
                                                            </li>
                                                            <li title="哈哈" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/8f/2018new_haha_thumb.png">
                                                            </li>
                                                            <li title="笑cry" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/4a/2018new_xiaoku_thumb.png">
                                                            </li>
                                                            <li title="挤眼" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/43/2018new_jiyan_org.png">
                                                            </li>
                                                            <li title="馋嘴" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/fa/2018new_chanzui_org.png">
                                                            </li>
                                                            <li title="黑线" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a3/2018new_heixian_thumb.png">
                                                            </li>
                                                            <li title="汗" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/28/2018new_han_org.png">
                                                            </li>
                                                            <li title="挖鼻" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/9a/2018new_wabi_thumb.png">
                                                            </li>
                                                            <li title="哼" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/7c/2018new_heng_thumb.png">
                                                            </li>
                                                            <li title="怒" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/f6/2018new_nu_thumb.png">
                                                            </li>
                                                            <li title="委屈" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a5/2018new_weiqu_thumb.png">
                                                            </li>
                                                            <li title="可怜" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/96/2018new_kelian_org.png">
                                                            </li>
                                                            <li title="失望" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/aa/2018new_shiwang_thumb.png">
                                                            </li>
                                                            <li title="悲伤" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/ee/2018new_beishang_org.png">
                                                            </li>
                                                            <li title="泪" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/6e/2018new_leimu_org.png">
                                                            </li>
                                                            <li title="允悲" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/83/2018new_kuxiao_org.png">
                                                            </li>
                                                            <li title="害羞" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/c1/2018new_haixiu_org.png">
                                                            </li>
                                                            <li title="污" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/10/2018new_wu_thumb.png">
                                                            </li>
                                                            <li title="爱你" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/f6/2018new_aini_org.png">
                                                            </li>
                                                            <li title="亲亲" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/2c/2018new_qinqin_thumb.png">
                                                            </li>
                                                            <li title="色" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/9d/2018new_huaxin_org.png">
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
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/4d/2018new_huaixiao_org.png">
                                                            </li>
                                                            <li title="阴险" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/9e/2018new_yinxian_org.png">
                                                            </li>
                                                            <li title="笑而不语" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/2d/2018new_xiaoerbuyu_org.png">
                                                            </li>
                                                            <li title="偷笑" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/71/2018new_touxiao_org.png">
                                                            </li>
                                                            <li title="酷" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/c4/2018new_ku_org.png">
                                                            </li>
                                                            <li title="并不简单" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/aa/2018new_bingbujiandan_thumb.png">
                                                            </li>
                                                            <li title="思考" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/30/2018new_sikao_org.png">
                                                            </li>
                                                            <li title="疑问" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/b8/2018new_ningwen_org.png">
                                                            </li>
                                                            <li title="费解" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/2a/2018new_wenhao_thumb.png">
                                                            </li>
                                                            <li title="晕" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/07/2018new_yun_thumb.png">
                                                            </li>
                                                            <li title="衰" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a2/2018new_shuai_thumb.png">
                                                            </li>
                                                            <li title="骷髅" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a1/2018new_kulou_thumb.png">
                                                            </li>
                                                            <li title="嘘" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/b0/2018new_xu_org.png">
                                                            </li>
                                                            <li title="闭嘴" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/62/2018new_bizui_org.png">
                                                            </li>
                                                            <li title="傻眼" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/dd/2018new_shayan_org.png">
                                                            </li>
                                                            <li title="吃惊" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/49/2018new_chijing_org.png">
                                                            </li>
                                                            <li title="吐" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/08/2018new_tu_org.png">
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
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/fd/2018new_baibai_thumb.png">
                                                            </li>
                                                            <li title="鄙视" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/da/2018new_bishi_org.png">
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
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/87/2018new_zhouma_thumb.png">
                                                            </li>
                                                            <li title="打脸" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/cb/2018new_dalian_org.png">
                                                            </li>
                                                            <li title="顶" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/ae/2018new_ding_org.png">
                                                            </li>
                                                            <li title="互粉" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/86/2018new_hufen02_org.png">
                                                            </li>
                                                            <li title="钱" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a2/2018new_qian_thumb.png">
                                                            </li>
                                                            <li title="哈欠" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/55/2018new_dahaqian_org.png">
                                                            </li>
                                                            <li title="困" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/3c/2018new_kun_thumb.png">
                                                            </li>
                                                            <li title="睡" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/e2/2018new_shuijiao_thumb.png">
                                                            </li>
                                                            <li title="求饶" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/aa/moren_qiurao02_thumb.png">
                                                            </li>
                                                            <li title="吃瓜" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/01/2018new_chigua_thumb.png">
                                                            </li>
                                                            <li title="打call" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/39/moren_dacall02_thumb.png">
                                                            </li>
                                                            <li title="awsl" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/14/moren_awsl02_thumb.png">
                                                            </li>
                                                            <li title="苦涩" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/7e/2021_bitter_thumb.png">
                                                            </li>
                                                            <li title="裂开" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/1b/202011_liekai_thumb.png">
                                                            </li>
                                                            <li title="doge" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/a1/2018new_doge02_org.png">
                                                            </li>
                                                            <li title="二哈" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/22/2018new_erha_org.png">
                                                            </li>
                                                            <li title="喵喵" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/7b/2018new_miaomiao_thumb.png">
                                                            </li>
                                                            <li title="酸" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/b3/hot_wosuanle_thumb.png">
                                                            </li>
                                                            <li title="中国赞" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/2018new_zhongguozan_org.png">
                                                            </li>
                                                            <li title="抱抱" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/42/2018new_baobao_thumb.png">
                                                            </li>
                                                            <li title="摊手" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/62/2018new_tanshou_org.png">
                                                            </li>
                                                            <li title="跪了" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/75/2018new_gui_org.png">
                                                            </li>
                                                            <li title="鲜花" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/d4/2018new_xianhua_org.png">
                                                            </li>
                                                            <li title="红灯笼" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/e8/2021_lantern_thumb.png">
                                                            </li>
                                                            <li title="烟花" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/96/2021_fireworks_thumb.png">
                                                            </li>
                                                            <li title="雪花" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/13/yunying2020_snowflakes_thumb.png">
                                                            </li>
                                                            <li title="丘比特" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/45/2021_qiubite_thumb.png">
                                                            </li>
                                                            <li title="单身狗" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/20/2021_alongdog_thumb.png">
                                                            </li>
                                                            <li title="抱一抱" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/af/2020_hug_thumb.png">
                                                            </li>
                                                            <li title="小丑" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/6f/2021_xiaochou_thumb.png">
                                                            </li>
                                                            <li title="杰瑞" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/da/2021_jerry_thumb.png">
                                                            </li>
                                                            <li title="汤姆" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/31/2021_tom_thumb.png">
                                                            </li>
                                                            <li title="奶瓶" @click="input_faces($event)">
                                                                <img
                                                                        src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/8c/2021_naiping_thumb.png">
                                                            </li>
                                                        </ul>
                                                    </div>
                                                    <el-button slot="reference">😊 表情</el-button>
                                                </el-popover>
                                            </div>
                                            <div class="bottom_right clearfix">
                                                <el-button type="warning" size="small" :disabled="!textarea"
                                                           @click="post_comment(single_weibo.diary.id)">评论
                                                </el-button>
                                            </div>
                                        </div>
                                        <!-- <<< 评论输入框 -->
                                    </div>
                                    <!-- <<< 隐藏的评论标签 -->
                                </div>
                                <p class="more_p" v-if="!loading && !noMore">上划加载更多</p>
                                <p class="more_p" v-if="loading&&!noMore">加载中...</p>
                                <p class="more_p" v-if="noMore">没有更多了</p>
                            </div>
                        </el-tab-pane>
                        <el-tab-pane label="相册" name="second">
                            <div class="photo-content clearfix" v-if="this.photo_lis">
                                <div class="photo-div" v-for="photo in photo_lis">
                                    <el-image :src="photo" :preview-src-list="photo_lis"></el-image>
                                </div>
                            </div>
                            <div class="no_photo" v-if="!this.photo_lis">
                                还没有上传过图片哦
                            </div>
                        </el-tab-pane>
                    </el-tabs>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import qs from 'qs'

    export default {
        name: "Other_Weibo_Middle",
        data() {
            return {
                avatar: '',
                nickname: '',
                token: this.$cookies.get('token'),
                followed_num: '',
                fans_num: '',
                activeName: 'first',
                all_weibo_lis: [],
                textarea: '',
                is_follow: '',
                photo_lis: null,
                count: 0,  // 向后端请求加载的页数，初始为 0，请求时会 +1
                loading: false,
                totalPages: 0,  // 取后端返回内容的总页数
                noMore: false,
                isScroll: true,  // 是否可以滚动
            };
        },

        methods: {
            load() {
                this.isScroll = false  // 当前的滚动不能影响加载数据
                if (this.count < this.totalPages - 1) {
                    this.count += 1;  // 页数 +1
                    this.getWeibo();  // 查询更多微博
                } else {
                    this.noMore = true
                }
            },

            // 向后端请求加载新微博
            getWeibo() {
                this.loading = true;
                this.$axios.get('/api/diary/all_diary', {
                    params: {
                        userId: this.otherId,
                        pageNumber: this.count,
                        pageSize: 10  // 每页查询条数
                    }
                }).then(res => {
                    if (res.data.diaries) {
                        this.totalPages = res.data.totalPages;
                        this.all_weibo_lis = this.all_weibo_lis.concat(res.data.diaries);  // 将新微博拼在现有的微博列表后面
                        // 设置定时器 给网页一点渲染的时间 然后再执行微博内容替换方法
                        clearTimeout(this.timer);
                        this.timer = setTimeout(() => {
                            this.replaceWeiboContent()
                        }, 10)
                    } else {
                        this.noMore = true
                    }

                    this.loading = false;
                }).catch(err => {
                    this.loading = false;
                })
            },

            scrollMoreData() {
                const scrollTopHeight = document.documentElement.scrollTop || document.body.scrollTop //滚动高度
                const clientHeight = document.documentElement.clientHeight || window.screen.availHeight //屏幕可用工作区高度
                const offsetHeight = document.documentElement.offsetHeight || document.body.offsetHeight //网页可见区域高(包括边线的宽)
                // console.log(scrollTopHeight, clientHeight, scrollTopHeight + clientHeight + 50, offsetHeight)

                if ((scrollTopHeight + clientHeight + 5) >= offsetHeight && this.isScroll) {
                    this.load()
                    this.isScroll = true
                }
            },

            // 将后端返来的微博表情的URL变为img标签
            replaceWeiboContent() {
                // 依次拿到类名是 single-weibo-content 的 div 标签（微博内容在内部显示）
                for (let e = 0; e < $('.single-weibo-content').length; e++) {
                    let ele = $('.single-weibo-content')[e]
                    // 清空标签原内容
                    ele.innerText = '';
                    // 使用后端传来的数据替换
                    let content_lis = this.all_weibo_lis[e].diary.content;  // 拿到后端传来的对应微博内容
                    for (let j = 0; j < content_lis.length; j++) {
                        let reg1 = /https:/  // 定义正则匹配规则 匹配以 https: 开头的
                        if (reg1.test(content_lis[j])) {
                            // 若是 url，新建 img 标签并添加 src 属性和类名（加类名是为了调样式）
                            let imgEle = document.createElement('img')
                            imgEle.src = content_lis[j]
                            imgEle.className = 'faceImage'
                            // 将 img 标签添加到 div 标签中
                            ele.append(imgEle)
                        } else {
                            // 若是文本则直接添加
                            ele.append(content_lis[j])
                        }
                    }
                }
            },

            // 点击微博下的评论图标时，显示微博下隐藏的发布评论输入框和已发布的评论
            show_comment(event, diaryId) {
                // 拿到当前点击的标签 定位到评论标签 改变 display 样式
                let current_target = event.currentTarget
                $(current_target.parentNode.parentNode).attr('id', diaryId)  // 给当前微博加上 id 方便其他函数操作
                let comment = $(current_target).parent().next()
                this.current_comment = comment  // 将当前打开的评论标签存到全局，方便发布评论时将新评论拼在后面

                if (comment.css('display') === 'none') {
                    this.textarea = ''  // 清空可能已有的输入内容
                    $(current_target).css('color', 'rgb(249, 151, 9)')
                    comment.slideDown(150)  // 落下效果 0.15 秒
                    // 当点击评论的图标时，如果评论数不是 0
                    // 则向后端发送 get 请求所有评论，生成标签将评论内容放入
                    if (current_target.innerText === '0') {
                        console.log('不发请求')
                    } else {
                        this.$axios.get('/api/comment/get_comment', {
                            params: {
                                diaryId: diaryId,
                            }
                        }).then(response => {
                            // 生成新的标签，将后端返回的评论内容渲染出来
                            let result_lis = response.data.comments

                            for (let i = 0; i < result_lis.length; i++) {
                                let single_comment_dic = result_lis[i]
                                // 先判断当前页面是否已经有了要创建的标签
                                let already_have = document.getElementById('comment' + single_comment_dic.comment.id)
                                if (!already_have) {  // 如果标签不存在，就创建
                                    // 先造评论外层的标签
                                    let outer_div = $('<div class="outer_div"></div>')
                                    comment.append(outer_div)
                                    // 造评论标签
                                    let parent_comment = $('<div class="parent_comment clearfix"></div>')
                                    parent_comment.attr('id', 'comment' + single_comment_dic.comment.id)
                                    parent_comment.attr('comment_userId', single_comment_dic.user.id)
                                    parent_comment.attr('style', 'border-top: 1px solid #cacaca; margin-left: 4%; margin-right: 4%; padding-top: 8px; padding-bottom: 8px;')
                                    // 评论的用户头像标签
                                    let comment_user_icon = $('<a href="#" class="comment_user_icon"></a>')
                                    // 给用户头像添加点击事件
                                    comment_user_icon.on('click', function () {
                                        other_user_page(single_comment_dic.user.id)
                                    })
                                    let icon = $('<img>').attr('src', single_comment_dic['comment_icon'])
                                    icon.attr('style', 'width: 40px; height: 40px; float: left; margin-top: 0.1em;')
                                    comment_user_icon.append(icon)
                                    parent_comment.append(comment_user_icon)
                                    // 评论用户名和内容和时间外的 div
                                    let comment_outer_div = $('<div class="comment_outer_div clearfix"></div>')
                                    comment_outer_div.attr('style', 'margin-left: 2.9em;')  // 因为左侧头像宽 9%，这里整体向右偏 2.9em
                                    // 评论用户名标签
                                    let comment_user_name = $('<a href="#" class="comment_user_name"></a>')
                                    comment_user_name.attr('comment_username', single_comment_dic.user.nickname)
                                    comment_user_name.text(single_comment_dic.user.nickname + '：')
                                    comment_user_name.attr('style', 'color: #353535; float: left;')
                                    // 给用户名标签加上点击事件
                                    comment_user_name.on('click', function () {
                                        other_user_page(single_comment_dic.user.id)
                                    })
                                    // 给用户名 a 标签加上 hover 样式
                                    comment_user_name.hover(function () {
                                        $(this).css('color', 'rgb(249, 151, 9)')
                                        $(this).css('text-decoration', 'none')
                                    }, function () {
                                        $(this).css('color', '#353535')
                                    })
                                    // 评论内容标签
                                    let comment_user_content = $('<div class="comment_user_content"></div>')
                                    // 如果当前评论是子评论
                                    if (single_comment_dic.comment.parentId) {
                                        // 先拿到根评论的用户名
                                        let parent_ele = document.getElementById('comment' + single_comment_dic.comment.parentId)
                                        let parent_userId = $(parent_ele).attr('comment_userId')
                                        let parent_username = $(parent_ele).children().first().next().children().first().text()
                                        // 再拼接
                                        let comment_replay = $('<a href="#" class="comment_replay"></a>')
                                        comment_replay.text('@' + parent_username)
                                        comment_replay.on('click', function () {
                                            other_user_page(parent_userId)
                                        })
                                        // 给 @用户名 加上 hover 样式
                                        comment_replay.hover(function () {
                                            $(this).css('color', 'rgb(249, 151, 9)')
                                            $(this).css('text-decoration', 'none')  // 去掉 a 标签的下划线
                                        }, function () {
                                            $(this).css('color', '#353535')
                                        })
                                        comment_user_content.append('回复')
                                        comment_user_content.append(comment_replay)
                                        for (let i = 0; i < single_comment_dic.comment.content.length; i++) {
                                            let reg1 = /https:/
                                            if (reg1.test(single_comment_dic.comment.content[i])) {
                                                let imgEle = $('<img>')
                                                imgEle.attr('src', single_comment_dic.comment.content[i])
                                                imgEle.attr('style', 'width: 1.2em; margin-left: 0.2em; position: relative; bottom: 0.1em;')
                                                comment_user_content.append(imgEle)
                                            } else {
                                                comment_user_content.append(single_comment_dic.comment.content[i])
                                            }
                                        }
                                    } else {
                                        for (let i = 0; i < single_comment_dic.comment.content.length; i++) {
                                            let reg1 = /https:/
                                            if (reg1.test(single_comment_dic.comment.content[i])) {
                                                let imgEle = $('<img>')
                                                imgEle.attr('src', single_comment_dic.comment.content[i])
                                                imgEle.attr('style', 'width: 1.2em; margin-left: 0.2em; position: relative; bottom: 0.1em;')
                                                comment_user_content.append(imgEle)
                                            } else {
                                                comment_user_content.append(single_comment_dic.comment.content[i])
                                            }
                                        }
                                    }
                                    comment_user_content.attr('style', 'text-align: left; color: #353535; line-height: 1.5em;')
                                    comment_outer_div.append(comment_user_name)
                                    comment_outer_div.append(comment_user_content)
                                    // 评论时间和回复按钮标签
                                    let time_and_replay = $('<div class="time_and_replay clearfix"></div>')
                                    time_and_replay.attr('style', 'text-align: left;')
                                    let comment_time = $('<span class="comment_time"></span>').text(single_comment_dic.comment.commentTime)
                                    comment_time.attr('style', 'font-size: 88%; color: grey;')
                                    time_and_replay.append(comment_time)
                                    if (this.$store.state.id == single_comment_dic.user.id) {
                                        // 如果当前评论是当前登录的用户发表的，就显示删除按钮
                                        let delete_button = $('<button type="button" class="el-button el-button--text"><span>删除</span></button>')
                                        delete_button.attr('style', 'padding: 0; float: right; color: rgb(249, 151, 9)')
                                        delete_button.attr('commentId', single_comment_dic.comment.id)  // 给删除按钮绑定评论 id
                                        delete_button.on('click', function () {
                                            delete_comment(single_comment_dic.user.id, single_comment_dic.comment.id, single_comment_dic.comment.diaryId)
                                        })
                                        time_and_replay.append(delete_button)
                                    } else {
                                        // 如果是其他人发表的，就显示回复按钮
                                        let replay_button = $('<button type="button" class="el-button el-button--text"><span>回复</span></button>')
                                        replay_button.attr('style', 'padding: 0; float: right;')
                                        replay_button.attr('commentId', single_comment_dic.comment.id)  // 给回复按钮绑定评论 id
                                        replay_button.attr('comment_username', single_comment_dic.user.nickname)  // 给回复按钮绑定评论用户名
                                        replay_button.on('click', function () {
                                            replay_comment(single_comment_dic.user.nickname, single_comment_dic.comment.id, single_comment_dic.comment.diaryId)
                                        })
                                        time_and_replay.append(replay_button)
                                    }
                                    comment_outer_div.append(time_and_replay)
                                    parent_comment.append(comment_outer_div)
                                    // 最后将根评论的标签放入外层标签中
                                    outer_div.append(parent_comment)
                                } else {  // 标签存在，则什么都不干，跳过此次循环
                                    console.log('标签已存在')
                                }
                            }
                        }).catch(error => {
                            console.log(error)
                        })
                    }
                } else {
                    comment.slideUp(150)  // 升起效果 0.15 秒
                    $(current_target).css('color', '#464646')
                }
            },

            // 点击点赞按钮时
            like_or_unlike(e, diaryId) {
                if (!this.userId) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.$axios({
                        method: 'post',
                        url: '/api/diary/click_like',
                        headers: {Authorization: this.token},
                        data: {
                            diaryId: diaryId
                        }
                    }).then(response => {
                        if (response.data.code === 200) {
                            // 根据用户点到的标签不同，确保取到相同的标签内容
                            if (e.target.nodeName == 'I') {
                                e.target.nextElementSibling.innerText = parseInt(e.target.nextElementSibling.innerText) + 1
                                $(e.target.parentNode).css('color', 'rgb(249, 151, 9)')
                            } else if (e.target.nodeName == 'BUTTON') {
                                e.target.children[1].innerText = parseInt(e.target.children[1].innerText) + 1
                                $(e.target).css('color', 'rgb(249, 151, 9)')
                            } else {
                                e.target.innerText = parseInt(e.target.innerText) + 1
                                $(e.target.parentNode).css('color', 'rgb(249, 151, 9)')
                            }

                        } else if (response.data.code === 100) {
                            if (e.target.nodeName == 'I') {
                                e.target.nextElementSibling.innerText = parseInt(e.target.nextElementSibling.innerText) - 1
                                // 点赞 将当前标签颜色改变
                                $(e.target.parentNode).css('color', '#464646')
                            } else if (e.target.nodeName == 'BUTTON') {
                                e.target.children[1].innerText = parseInt(e.target.children[1].innerText) - 1
                                $(e.target).css('color', '#464646')
                            } else {
                                e.target.innerText = parseInt(e.target.innerText) - 1
                                $(e.target.parentNode).css('color', '#464646')
                            }
                        } else {
                            this.$message({
                                message: response.data.msg,
                                type: 'warning',
                                duration: 1500,
                                center: true,
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },

            // 点击表情时
            input_faces(e) {
                let text = document.getElementById('ipt').value;
                let faces_name = '[' + e.currentTarget.getAttribute('title') + ']';
                if (text.length + faces_name.length <= 255) {
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

            // 点击发表评论时
            post_comment(diaryId) {
                // 先验证当前是否登录
                if (!this.userId) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true,
                    })
                } else {
                    if (this.parentID) {  // 如果是子评论，将要发送内容的 @用户名：去除
                        let indexNum = this.textarea.indexOf('：') + 1;
                        this.textarea = this.textarea.slice(indexNum)
                    }
                    this.$axios({
                        method: 'post',
                        url: '/api/comment/add_comment',
                        headers: {Authorization: this.token},
                        data: {
                            comment: this.textarea,
                            diaryId: diaryId,
                            parentId: this.parentID
                        }
                    }).then(response => {
                        if (response.data.code === 200) {
                            this.$message({
                                message: response.data.msg,
                                type: 'success',
                                duration: 1500,
                                center: true,
                            })
                            // 将显示评论数按钮的值 +1
                            let current_weibo = document.getElementById('diary' + diaryId)
                            let comment_number = current_weibo.children[2].children[0].children[1].innerText
                            current_weibo.children[2].children[0].children[1].innerText = parseInt(comment_number) + 1
                            let single_comment_dic = response.data.comments
                            // 创建一个渲染评论的标签，添加到评论楼的最下方（查看更多的上方）
                            // 先造评论外层的标签
                            let outer_div = $('<div class="outer_div"></div>')
                            // 造评论标签
                            let parent_comment = $('<div class="parent_comment clearfix"></div>')
                            parent_comment.attr('id', 'comment' + single_comment_dic.comment.id)
                            parent_comment.attr('comment_user_id', single_comment_dic.user.id)
                            parent_comment.attr('style', 'border-top: 1px solid #cacaca; margin-left: 4%; margin-right: 4%; padding-top: 8px; padding-bottom: 8px;')
                            this.textarea = '';
                            this.parentID = null;
                            // 评论的用户头像标签
                            let comment_user_icon = $('<a href="#" class="comment_user_icon"></a>')
                            // 给用户头像添加点击事件
                            comment_user_icon.on('click', function () {
                                other_user_page(single_comment_dic.user.id)
                            })
                            let icon = $('<img>').attr('src', single_comment_dic['comment_icon'])
                            icon.attr('style', 'width: 40px; height: 40px; float: left; margin-top: 0.1em;')
                            comment_user_icon.append(icon)
                            parent_comment.append(comment_user_icon)
                            // 评论用户名和内容和时间外的 div
                            let comment_outer_div = $('<div class="comment_outer_div clearfix"></div>')
                            comment_outer_div.attr('style', 'margin-left: 2.9em;')  // 因为左侧头像宽 9%，这里整体向右偏 2.9em
                            // 评论用户名标签
                            let comment_user_name = $('<a href="#" class="comment_user_name"></a>')
                            comment_user_name.attr('comment_username', single_comment_dic.user.nickname)
                            comment_user_name.text(single_comment_dic.user.nickname + '：')
                            comment_user_name.attr('style', 'color: #353535; float: left;')
                            // 给用户名标签加上点击事件
                            comment_user_name.on('click', function () {
                                other_user_page(single_comment_dic.user.id)
                            })
                            // 给用户名 a 标签加上 hover 样式
                            comment_user_name.hover(function () {
                                $(this).css('color', 'rgb(249, 151, 9)')
                                $(this).css('text-decoration', 'none')
                            }, function () {
                                $(this).css('color', '#353535')
                            })
                            // 评论内容标签
                            let comment_user_content = $('<div class="comment_user_content"></div>')
                            // 如果当前评论是子评论
                            if (single_comment_dic.comment.parentId) {
                                // 先拿到根评论的用户名
                                let parent_ele = document.getElementById('comment' + single_comment_dic.comment.parentId)
                                let parent_user_id = $(parent_ele).attr('comment_user_id')
                                let parent_username = $(parent_ele).children().first().next().children().first().text()
                                // 再拼接
                                let comment_replay = $('<a href="#" class="comment_replay"></a>')
                                comment_replay.text('@' + parent_username)
                                comment_replay.on('click', function () {
                                    other_user_page(parent_user_id)
                                })
                                // 给 @用户名 加上 hover 样式
                                comment_replay.hover(function () {
                                    $(this).css('color', 'rgb(249, 151, 9)')
                                    $(this).css('text-decoration', 'none')  // 去掉 a 标签的下划线
                                }, function () {
                                    $(this).css('color', '#353535')
                                })
                                comment_user_content.append('回复')
                                comment_user_content.append(comment_replay)
                                for (let i = 0; i < single_comment_dic.comment.content.length; i++) {
                                    let reg1 = /https:/
                                    if (reg1.test(single_comment_dic.comment.content[i])) {
                                        let imgEle = $('<img>')
                                        imgEle.attr('src', single_comment_dic.comment.content[i])
                                        imgEle.attr('style', 'width: 1.2em; margin-left: 0.2em; position: relative; bottom: 0.1em;')
                                        comment_user_content.append(imgEle)
                                    } else {
                                        comment_user_content.append(single_comment_dic.comment.content[i])
                                    }
                                }
                            } else {
                                for (let i = 0; i < single_comment_dic.comment.content.length; i++) {
                                    let reg1 = /https:/
                                    if (reg1.test(single_comment_dic.comment.content[i])) {
                                        let imgEle = $('<img>')
                                        imgEle.attr('src', single_comment_dic.comment.content[i])
                                        imgEle.attr('style', 'width: 1.2em; margin-left: 0.2em; position: relative; bottom: 0.1em;')
                                        comment_user_content.append(imgEle)
                                    } else {
                                        comment_user_content.append(single_comment_dic.comment.content[i])
                                    }
                                }
                            }
                            comment_user_content.attr('style', 'text-align: left; color: #353535; line-height: 1.5em;')
                            comment_outer_div.append(comment_user_name)
                            comment_outer_div.append(comment_user_content)
                            // 评论时间和回复按钮标签
                            let time_and_replay = $('<div class="time_and_replay clearfix"></div>')
                            time_and_replay.attr('style', 'text-align: left;')
                            let comment_time = $('<span class="comment_time"></span>').text(single_comment_dic.comment.commentTime)
                            comment_time.attr('style', 'font-size: 88%; color: grey;')
                            time_and_replay.append(comment_time)
                            let delete_button = $('<button type="button" class="el-button el-button--text"><span>删除</span></button>')
                            delete_button.attr('style', 'padding: 0; float: right; color: rgb(249, 151, 9)')
                            delete_button.attr('commentId', single_comment_dic.comment.id)  // 给删除按钮绑定评论 id
                            delete_button.on('click', function () {
                                delete_comment(single_comment_dic.user.id, single_comment_dic.comment.id, single_comment_dic.comment.diaryId)
                            })
                            time_and_replay.append(delete_button)
                            comment_outer_div.append(time_and_replay)
                            parent_comment.append(comment_outer_div)
                            // 将新生成的标签放在最后一条评论标签的后面
                            outer_div.append(parent_comment)
                            this.current_comment.append(outer_div)
                        } else {
                            this.$message({
                                message: response.data.msg,
                                type: 'error',
                                center: true,
                            })
                        }
                    }).catch(error => {
                        this.$message({
                            message: error,
                            type: 'error',
                            center: true,
                        })
                    })
                }
            },

            // 点击回复评论按钮时，给输入框加入要回复的人名
            replay_comment(comment_username, commentId, diaryId) {
                if (!this.userId) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.parentID = commentId;
                    this.textarea = '@' + comment_username + '：';
                    // 输入框获取焦点
                    let current_weibo = document.getElementById('diary' + diaryId)
                    current_weibo.children[3].children[0].children[0].children[0].focus()
                }
            },

            // 点击删除评论按钮时
            delete_comment(comment_user_id, commentId, diaryId) {
                if (!this.userId) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.$confirm('确认删除这条评论吗？', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning',
                    }).then(() => {
                        this.$axios({
                            method: 'post',
                            url: '/api/comment/delete_comment',
                            headers: {Authorization: this.token},
                            data: {
                                commentId: commentId
                            }
                        }).then(response => {
                            if (response.data.code === 200) {
                                this.$message({
                                    message: response.data.msg,
                                    type: 'success',
                                    duration: 1500,
                                    center: true,
                                })
                                // 删除成功，将当前评论标签删掉
                                document.getElementById('comment' + commentId).remove()
                                // 将显示评论数按钮的值 -1
                                let comment_num = parseInt(document.getElementById('diary' + diaryId).children[2].children[0].children[1].innerText)
                                document.getElementById('diary' + diaryId).children[2].children[0].children[1].innerText = comment_num - 1
                            } else {
                                this.$message({
                                    message: response.data.msg,
                                    type: 'error',
                                    duration: 1500,
                                    center: true,
                                })
                            }
                        }).catch(error => {
                            this.$message({
                                message: error,
                                type: 'error',
                                duration: 1500,
                                center: true,
                            })
                        })
                    }).catch(() => {
                    });
                }
            },

            // 打开新标签页，跳转到个人页面
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

            // 关注用户
            change_follow() {
                if (!this.userId) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.$axios({
                        method: 'post',
                        url: '/api/user/change_follow',
                        headers: {Authorization: this.token},
                        data: qs.stringify({
                            followId: this.otherId  // 修改关注状态的用户
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
            this.otherId = sessionStorage.getItem('otherId');

            // 获取当前查看用户的头像和昵称
            this.$axios.get('/api/user/get_user', {
                params: {userId: this.otherId}
            }).then(response => {
                if (response.data.code === 200) {
                    this.nickname = response.data.user['nickname']
                    this.avatar = response.data.user['avatar']
                } else {
                    this.$message({
                        message: '此用户不存在',
                        type: 'info',
                        duration: 1500,
                        center: true,
                    })
                }
            }).catch(error => {
            })

            // 获取当前查看用户的全部微博
            this.getWeibo()

            // 获取关注数量
            this.$axios.get('/api/user/get_follow_num',
                {params: {userId: this.otherId}}
            ).then(response => {
                this.followed_num = response.data.followNum;
            }).catch(error => {
                console.log(error);
                this.followed_num = '--';
            });

            // 获取粉丝数量
            this.$axios.get('/api/user/get_fans_num',
                {params: {userId: this.otherId}}
            ).then(response => {
                this.fans_num = response.data.fansNum;
            }).catch(error => {
                console.log(error)
                this.fans_num = '--';
            });

            // 查询当前登录用户是否关注了当前查看用户
            this.$axios.get('/api/user/check_follow', {
                params: {
                    checkId: this.otherId, // 查看的用户
                }
            }).then(response => {
                this.is_follow = response.data.code === 200;
            }).catch(error => {
            })

            // 获取当前登录用户的全部图片
            this.$axios.get('/api/user/all_photo', {
                params: {
                    userId: this.otherId,
                }
            }).then(response => {
                if (response.data.code === 200) {
                    this.photo_lis = response.data.photos
                }
            }).catch(error => {
                console.log(error)
            })

        },

        mounted() {
            window.other_user_page = this.other_user_page
            window.replay_comment = this.replay_comment
            window.delete_comment = this.delete_comment
            document.addEventListener('scroll', this.scrollMoreData, false)
        },

        computed: {
            userId() {
                return this.$store.state.id
            }
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

    .jumbotron {
        border-radius: 3px;
        height: 18.7em;
        background-color: rgba(203, 203, 203, 0.19);
        margin-bottom: 0;
        padding-bottom: 0;
    }

    .jumbotron img {
        /*width: 7em;*/
        width: 100px;
        height: 100px;
        border-radius: 50%;
    }

    .jumbotron p {
        margin-top: 8px;
        margin-bottom: 8px;
        font-weight: 400;
    }

    .follow_and_fans {
        margin-bottom: 1em;
    }

    .follow_and_fans span {
        font-size: 90%;
    }

    #s2 {
        padding: 0 8px 0 8px;
        color: #c6c6c6;
    }

    ::v-deep .el-tabs__active-bar {
        background-color: rgb(249, 151, 9) !important;
    }

    ::v-deep .el-tabs__item.is-active {
        color: black !important;
    }

    ::v-deep .el-tabs__item:hover {
        color: rgb(249, 151, 9) !important;
    }

    ::v-deep .el-tabs__header {
        margin-bottom: 30px;
    }

    .weibo-content {
        padding-bottom: 15px;

    }

    .clearfix:after { /*具有clearfix类属性的标签后面添加*/
        content: ''; /*添加的内容为空*/
        display: block; /*让空内容具有块级标签的属性：独占一行*/
        clear: both; /*空内容左右两侧不能有浮动的标签*/
    }

    a:hover {
        text-decoration: none;
        color: rgb(249, 151, 9);
    }

    .single-weibo {
        padding-top: 1.1em;
        margin-bottom: 1.5em;
        background-color: #fafafa;
        border-top: 2px solid rgb(249, 151, 9);
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12)
    }

    .no_weibo {
        color: #bcbcbc;
        margin-top: 50px;
    }

    /* 单条微博上部分样式开始 */
    .single-weibo-up {
        padding-left: 1.1em;
        padding-right: 1.1em;
    }

    .avatar-name-time {
        position: relative;
        width: 100%;
        text-align: left;
    }

    .avatar-name-time img {
        /*width: 8%;*/
        border-radius: 50%;
        float: left;
        width: 53px;
        height: 53px;
    }

    .single-weibo-writer {
        padding-top: 1%;
        padding-left: 3%;
        display: inline-block;
    }

    .writer-name {
        font-weight: 500;
        font-size: 112%;
    }

    .write-time {
        color: grey;
        font-size: 90%;
    }

    /* 单条微博上部分样式结束 */


    /* 单条微博中部分样式开始 */
    .single-weibo-middle {
        margin-left: 12.4%;
        padding-right: 4%;
        margin-top: 5px;
        margin-bottom: 10px;
    }

    .single-weibo-content {
        text-align: left;
        color: #353535;
        font-weight: 400;
        margin-top: 5px;
        margin-bottom: 10px;
        position: relative;
    }

    ::v-deep .faceImage {
        width: 1.2em;
        margin-left: 0.2em;
        position: relative;
        bottom: 0.2em;
    }

    .single-weibo-media {
        text-align: left;
        float: left;
        display: inline;
    }

    .el-image {
        width: 150px;
        height: 150px;
        margin-right: 6px;
    }

    ::v-deep .el-image img {
        object-fit: cover;
    }

    /* 单条微博中部分样式结束 */

    /* 单条微博下部分样式开始 */
    .line {
        margin-left: 2%;
        margin-right: 2%;
    }

    .single-weibo-bottom {
        padding-top: 1%;
    }

    .single-weibo-bottom button {
        color: #464646;
    }

    .single-weibo-bottom button:hover {
        color: rgb(249, 151, 9);
    }

    ::v-deep .single-weibo-bottom button span {
        margin-left: 3px;
    }

    /* 单条微博下部分样式结束 */

    /* 隐藏的评论标签样式 >>> */
    .comment {
        border-top: 1px solid rgb(249, 151, 9);
    }

    .comment_input {
        margin-top: 15px;
    }

    .el-textarea {
        width: 92%;
        margin-bottom: 10px;
    }

    .el-button.el-button--default.el-popover__reference {
        float: left;
        border-width: 0;
        background-color: #fafafa;
        padding-right: 0;
        padding-left: 0;
        margin-left: 5%;
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

    .bottom_right {
        float: right;
        margin-right: 5%;
        margin-bottom: 10px;
    }

    /* <<< 隐藏的评论输入框标签样式 */

    /* 相册样式 >>> */
    .photo-div {
        text-align: center;
        float: left;
        display: inline;
        width: 25%;
    }

    .photo-div .el-image {
        margin-bottom: 3px;
        width: 95%;
        height: 160px;
    }

    .no_photo {
        color: #bababa;
        margin-top: 50px;
    }

    /* <<< 相册样式 */
    .more_p {
        color: #b1b1b1;
    }

    @media screen and (max-width: 600px) {
        #top_block {
            margin-top: 52px;
        }

        .container {
            padding-right: 0;
            padding-left: 0;
        }

        .col-md-10 {
            padding-right: 0;
            padding-left: 0;
        }

        .jumbotron {
            border-radius: 0 !important;
        }

        .jumbotron img {
            /*width: 6em;*/
            width: 90px;
            height: 90px;
        }

        .single-weibo {
            margin-bottom: 1.5em;
        }

        .writer-name {
            font-size: 100%;
        }

        .write-time {
            color: grey;
            font-size: 75%;
        }

        .avatar-name-time img {
            /*width: 12%;*/
            border-radius: 50%;
            float: left;
            width: 44px;
            height: 44px;
        }

        .single-weibo-middle {
            margin-left: 18%;
            padding-right: 5%;
        }

        .single-weibo-content {
            font-size: 90%;
            padding-top: 3%;
        }

        .el-image {
            width: 100px;
            height: 100px;
            margin-left: 3px;
            margin-right: 3px;
        }

        .follow_and_fans {
            margin-bottom: 25px;
        }

        /* 相册样式 >>> */
        .photo-div {
            text-align: center;
            float: left;
            display: inline;
            width: 25%;
        }

        .photo-div .el-image {
            margin-bottom: 0.1%;
            width: 95%;
            height: 100px;
        }

        /* <<< 相册样式 */
    }

    @media screen and (max-width: 414px) {
        /* 相册样式 >>> */
        .photo-div {
            text-align: center;
            float: left;
            display: inline;
            width: 25%;
        }

        .photo-div .el-image {
            margin-bottom: 0.1%;
            width: 95%;
            height: 90px;
        }

        /* <<< 相册样式 */
    }
</style>
