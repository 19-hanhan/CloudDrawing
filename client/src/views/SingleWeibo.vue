<template>
    <div class="single_weibo">
        <Head/>
        <div id="top_block"></div>
        <div class="container">
            <div class="col-md-8 col-md-offset-2">
                <div v-if="!single_weibo_dic">{{ no_weibo }}</div>
                <div class="weibo-content" v-if="single_weibo_dic">
                    <div class="list-item">
                        <div class="single-weibo-up">
                            <div class="avatar-name-time clearfix">
                                <a href="#" @click="other_user_page(single_weibo_dic.user)">
                                    <span class="single-weibo-avatar">
                                      <img :src="single_weibo_dic.icon">
                                    </span>
                                    <ul class="single-weibo-writer">
                                        <li class="writer-name">{{ single_weibo_dic.nickname }}</li>
                                        <li class="write-time">{{ single_weibo_dic.create_time }}</li>
                                    </ul>
                                </a>
                                <el-dropdown v-if="user_id && (parseInt(user_id) !== parseInt(single_weibo_dic.user))"
                                             trigger="click">
                                    <span class="el-dropdown-link">
                                      <i @click="check_follow(user_id, single_weibo_dic.user)"
                                         class="el-icon-arrow-down el-icon--right"></i>
                                    </span>
                                    <el-dropdown-menu slot="dropdown">
                                        <span @click="change_follow(user_id, single_weibo_dic.user)">
                                            <el-dropdown-item v-if="!is_follow">加关注</el-dropdown-item>
                                            <el-dropdown-item v-if="is_follow">取消关注</el-dropdown-item>
                                        </span>
                                    </el-dropdown-menu>
                                </el-dropdown>
                            </div>
                        </div>
                        <div class="single-weibo-middle">
                            <div class="single-weibo-content">
                                加载中...
                            </div>
                            <div class="weibo-media clearfix">
                                <div class="single-weibo-media" v-for="image in single_weibo_dic.image_lis">
                                    <el-image :src="image" :preview-src-list="single_weibo_dic.image_lis"></el-image>
                                </div>
                            </div>
                        </div>
                        <!-- 评论和点赞 >>> -->
                        <div class="single-weibo-bottom">
                            <el-button
                                    @click="show_comment($event, single_weibo_dic['weibo_id'],)"
                                    type="text"
                                    icon="el-icon-chat-dot-square">{{ single_weibo_dic['comment_num'] }}
                            </el-button>
                            <span class="line">｜</span>
                            <el-button type="text" icon="glyphicon glyphicon-thumbs-up"
                                       @click="like_or_unlike($event, single_weibo_dic['weibo_id'])">{{
                                single_weibo_dic['like_num']
                                }}
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
                                                    <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/c4/2018new_ku_org.png">
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
                                                    <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/b0/2018new_xu_org.png">
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
                                                    <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/af/2020_hug_thumb.png">
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
                                                    <img src="https://face.t.sinajs.cn/t4/appstyle/expression/ext/normal/31/2021_tom_thumb.png">
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
                                               @click="post_comment(single_weibo_dic['weibo_id'])">评论
                                    </el-button>
                                </div>
                            </div>
                            <!-- <<< 评论输入框 -->
                        </div>
                        <!-- <<< 隐藏的评论标签 -->
                    </div>
                </div>
            </div>
        </div>
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

    export default {
        name: "SingleWeibo",
        components: {
            Head,
            Footer
        },
        data() {
            return {
                scrollNum: 0, //滚动距离
                isTop: false, //是否显示回到顶部按钮
                myTimer: -1, //防止点击事件过度
                is_follow: false,
                single_weibo_id: null,
                single_weibo_dic: null,
                user_id: this.$cookies.get('user_id'),
                no_weibo: '',
                textarea: '',
                token: this.$cookies.get('token'),
            }
        },

        mounted() {
            window.other_user_page = this.other_user_page
            window.replay_comment = this.replay_comment
            window.delete_comment = this.delete_comment

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

            // 检查关注状态
            check_follow(user_id, check_id) {
                // 发送 get 请求获取当前用户的关注状态
                this.$axios.get(this.$settings.base_url + '/check_follow/', {
                    params: {
                        user_id: user_id,  // 当前登录用户
                        check_id: check_id  // 点击的用户
                    }
                }).then(response => {
                    this.is_follow = response.data.code === 200;
                }).catch(error => {
                    console.log(error)
                })
            },

            // 修改关注状态
            change_follow(user_id, follow_id) {
                if (!this.$cookies.get('user_id')) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.$axios({
                        method: 'post',
                        url: this.$settings.base_url + '/change_follow/',
                        headers: {Authorization: this.token},
                        data: {
                            user_id: user_id,  // 当前登录的用户
                            follow_id: follow_id  //修改关注状态的用户
                        }
                    }).then(response => {
                        this.$message({
                            message: response.data.msg,
                            type: 'success',
                            center: true,
                            duration: 1500
                        });
                        this.is_follow = !this.is_follow  // 修改登录状态以便于前端显示
                    }).catch(error => {
                        this.$message({
                            message: '出现错误，请重试',
                            type: 'error',
                            center: true,
                            duration: 1500
                        });
                    })
                }
            },

            // 点击微博下的评论图标时，显示微博下隐藏的发布评论输入框和已发布的评论
            show_comment(event, weibo_id) {
                // 拿到当前点击的标签 定位到评论标签 改变 display 样式
                let current_target = event.currentTarget
                $(current_target.parentNode.parentNode).attr('id', weibo_id)  // 给当前微博加上 id 方便其他函数操作
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
                        this.$axios.get(this.$settings.base_url + '/weibo/comment/get_comment/', {
                            params: {
                                weibo_id: weibo_id,
                            }
                        }).then(response => {
                            // 生成新的标签，将后端返回的评论内容渲染出来
                            let result_lis = response.data.result

                            for (let i = 0; i < result_lis.length; i++) {
                                let single_comment_dic = result_lis[i]
                                // 先判断当前页面是否已经有了要创建的标签
                                let already_have = document.getElementById(single_comment_dic['comment_id'])
                                if (!already_have) {  // 如果标签不存在，就创建
                                    // 先造评论外层的标签
                                    let outer_div = $('<div class="outer_div"></div>')
                                    comment.append(outer_div)
                                    // 造评论标签
                                    let parent_comment = $('<div class="parent_comment clearfix"></div>')
                                    parent_comment.attr('id', single_comment_dic['comment_id'])
                                    parent_comment.attr('comment_user_id', single_comment_dic['comment_user_id'])
                                    parent_comment.attr('style', 'border-top: 1px solid #cacaca; margin-left: 4%; margin-right: 4%; padding-top: 8px; padding-bottom: 8px;')
                                    // 评论的用户头像标签
                                    let comment_user_icon = $('<a href="#" class="comment_user_icon"></a>')
                                    // 给用户头像添加点击事件
                                    comment_user_icon.on('click', function () {
                                        other_user_page(single_comment_dic['comment_user_id'])
                                    })
                                    let icon = $('<img>').attr('src', single_comment_dic['comment_icon'])
                                    icon.attr('style', 'width: 40px; float: left; margin-top: 0.1em;')
                                    comment_user_icon.append(icon)
                                    parent_comment.append(comment_user_icon)
                                    // 评论用户名和内容和时间外的 div
                                    let comment_outer_div = $('<div class="comment_outer_div clearfix"></div>')
                                    comment_outer_div.attr('style', 'margin-left: 2.9em;')  // 因为左侧头像宽 2.6em，这里整体向右偏 2.9em
                                    // 评论用户名标签
                                    let comment_user_name = $('<a href="#" class="comment_user_name"></a>')
                                    comment_user_name.attr('comment_username', single_comment_dic['comment_username'])
                                    comment_user_name.text(single_comment_dic['comment_username'] + '：')
                                    comment_user_name.attr('style', 'color: #353535; float: left;')
                                    // 给用户名标签加上点击事件
                                    comment_user_name.on('click', function () {
                                        other_user_page(single_comment_dic['comment_user_id'])
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
                                    if (single_comment_dic['parent_id']) {
                                        // 先拿到根评论的用户名
                                        let parent_ele = document.getElementById(single_comment_dic['parent_id'])
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
                                        for (let i = 0; i < single_comment_dic['content'].length; i++) {
                                            let reg1 = /https:/
                                            if (reg1.test(single_comment_dic['content'][i])) {
                                                let imgEle = $('<img>')
                                                imgEle.attr('src', single_comment_dic['content'][i])
                                                imgEle.attr('style', 'width: 1.2em; margin-left: 0.2em; position: relative; bottom: 0.1em;')
                                                comment_user_content.append(imgEle)
                                            } else {
                                                comment_user_content.append(single_comment_dic['content'][i])
                                            }
                                        }
                                    } else {
                                        for (let i = 0; i < single_comment_dic['content'].length; i++) {
                                            let reg1 = /https:/
                                            if (reg1.test(single_comment_dic['content'][i])) {
                                                let imgEle = $('<img>')
                                                imgEle.attr('src', single_comment_dic['content'][i])
                                                imgEle.attr('style', 'width: 1.2em; margin-left: 0.2em; position: relative; bottom: 0.1em;')
                                                comment_user_content.append(imgEle)
                                            } else {
                                                comment_user_content.append(single_comment_dic['content'][i])
                                            }
                                        }
                                    }
                                    comment_user_content.attr('style', 'text-align: left; color: #353535; line-height: 1.5em;')
                                    comment_outer_div.append(comment_user_name)
                                    comment_outer_div.append(comment_user_content)
                                    // 评论时间和回复按钮标签
                                    let time_and_replay = $('<div class="time_and_replay clearfix"></div>')
                                    time_and_replay.attr('style', 'text-align: left;')
                                    let comment_time = $('<span class="comment_time"></span>').text(single_comment_dic['comment_time'])
                                    comment_time.attr('style', 'font-size: 88%; color: grey;')
                                    time_and_replay.append(comment_time)
                                    if (this.$cookies.get('user_id') == single_comment_dic['comment_user_id']) {
                                        // 如果当前评论是当前登录的用户发表的，就显示删除按钮
                                        let delete_button = $('<button type="button" class="el-button el-button--text"><span>删除</span></button>')
                                        delete_button.attr('style', 'padding: 0; float: right; color: rgb(249, 151, 9)')
                                        delete_button.attr('comment_id', single_comment_dic['comment_id'])  // 给删除按钮绑定评论 id
                                        delete_button.on('click', function () {
                                            delete_comment(single_comment_dic['comment_user_id'], single_comment_dic['comment_id'], single_comment_dic['weibo_id'])
                                        })
                                        time_and_replay.append(delete_button)
                                    } else {
                                        // 如果是其他人发表的，就显示回复按钮
                                        let replay_button = $('<button type="button" class="el-button el-button--text"><span>回复</span></button>')
                                        replay_button.attr('style', 'padding: 0; float: right;')
                                        replay_button.attr('comment_id', single_comment_dic['comment_id'])  // 给回复按钮绑定评论 id
                                        replay_button.attr('comment_username', single_comment_dic['comment_username'])  // 给回复按钮绑定评论用户名
                                        replay_button.on('click', function () {
                                            replay_comment(single_comment_dic['comment_username'], single_comment_dic['comment_id'], single_comment_dic['weibo_id'])
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

            // 点击点赞按钮时
            like_or_unlike(e, weibo_id) {
                if (!this.$cookies.get('user_id')) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.$axios({
                        method: 'post',
                        url: this.$settings.base_url + '/weibo/like/post/',
                        headers: {Authorization: this.token},
                        data: {
                            user_id: this.user_id,
                            weibo_id: weibo_id
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
                                if (parseInt(e.target.nextElementSibling.innerText) - 1 >= 0) {  // 防止某些没刷新的页面将点赞数字减为 -1
                                    e.target.nextElementSibling.innerText = parseInt(e.target.nextElementSibling.innerText) - 1
                                    // 点赞 将当前标签颜色改变
                                    $(e.target.parentNode).css('color', '#464646')
                                }
                            } else if (e.target.nodeName == 'BUTTON') {
                                if (parseInt(e.target.children[1].innerText) - 1 >= 0) {  // 防止某些没刷新的页面将点赞数字减为 -1
                                    e.target.children[1].innerText = parseInt(e.target.children[1].innerText) - 1
                                    $(e.target).css('color', '#464646')
                                }
                            } else {
                                if (parseInt(e.target.innerText) - 1 >= 0) {
                                    e.target.innerText = parseInt(e.target.innerText) - 1
                                    $(e.target.parentNode).css('color', '#464646')
                                }
                            }
                        } else {
                            this.$message({
                                message: response.data.msg,
                                type: 'warning',
                                duration: 1500,
                                center: true
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },

            // 点击发表评论时
            post_comment(weibo_id) {
                // 先验证当前是否登录
                if (!this.$cookies.get('user_id')) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                    this.$router.push('/')
                } else {
                    if (this.parentID) {  // 如果是子评论，将要发送内容的 @用户名：去除
                        let indexNum = this.textarea.indexOf('：') + 1;
                        this.textarea = this.textarea.slice(indexNum)
                    }
                    this.$axios({
                        method: 'post',
                        url: this.$settings.base_url + '/weibo/new_comment/post_comment/',
                        headers: {Authorization: this.token},
                        data: {
                            user_id: this.$cookies.get('user_id'),
                            comment: this.textarea,
                            weibo_id: weibo_id,
                            parent_id: this.parentID
                        }
                    }).then(response => {
                        if (response.data.code === 200) {
                            this.$message({
                                message: response.data.msg,
                                type: 'success',
                                duration: 1500,
                                center: true
                            })
                            let current_weibo = document.getElementById(weibo_id)
                            // 将当前显示的评论数 +1
                            let comment_number = current_weibo.children[2].children[0].children[1].innerText
                            current_weibo.children[2].children[0].children[1].innerText = parseInt(comment_number) + 1
                            let single_comment_dic = response.data.result
                            // 创建一个渲染评论的标签，添加到评论楼的最下方（查看更多的上方）
                            // 先造评论外层的标签
                            let outer_div = $('<div class="outer_div"></div>')
                            // 造评论标签
                            let parent_comment = $('<div class="parent_comment clearfix"></div>')
                            parent_comment.attr('id', single_comment_dic['comment_id'])
                            parent_comment.attr('comment_user_id', single_comment_dic['comment_user_id'])
                            parent_comment.attr('style', 'border-top: 1px solid #cacaca; margin-left: 4%; margin-right: 4%; padding-top: 8px; padding-bottom: 8px;')
                            this.textarea = '';
                            this.parentID = null;
                            // 评论的用户头像标签
                            let comment_user_icon = $('<a href="#" class="comment_user_icon"></a>')
                            // 给用户头像添加点击事件
                            comment_user_icon.on('click', function () {
                                other_user_page(single_comment_dic['comment_user_id'])
                            })
                            let icon = $('<img>').attr('src', single_comment_dic['comment_icon'])
                            icon.attr('style', 'width: 40px; float: left; margin-top: 0.1em;')
                            comment_user_icon.append(icon)
                            parent_comment.append(comment_user_icon)
                            // 评论用户名和内容和时间外的 div
                            let comment_outer_div = $('<div class="comment_outer_div clearfix"></div>')
                            comment_outer_div.attr('style', 'margin-left: 2.9em;')  // 因为左侧头像宽 9%，这里整体向右偏 2.9em
                            // 评论用户名标签
                            let comment_user_name = $('<a href="#" class="comment_user_name"></a>')
                            comment_user_name.attr('comment_username', single_comment_dic['comment_username'])
                            comment_user_name.text(single_comment_dic['comment_username'] + '：')
                            comment_user_name.attr('style', 'color: #353535; float: left;')
                            // 给用户名标签加上点击事件
                            comment_user_name.on('click', function () {
                                other_user_page(single_comment_dic['comment_user_id'])
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
                            if (single_comment_dic['parent_id']) {
                                // 先拿到根评论的用户名
                                let parent_ele = document.getElementById(single_comment_dic['parent_id'])
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
                                // 对拿到的评论内容进行判断，如果是表情就变成带 url 的 img 标签
                                for (let i = 0; i < single_comment_dic['content'].length; i++) {
                                    let reg1 = /https:/
                                    if (reg1.test(single_comment_dic['content'][i])) {
                                        let imgEle = $('<img>')
                                        imgEle.attr('src', single_comment_dic['content'][i])
                                        imgEle.attr('style', 'width: 1.2em; margin-left: 0.2em; position: relative; bottom: 0.1em;')
                                        comment_user_content.append(imgEle)
                                    } else {
                                        comment_user_content.append(single_comment_dic['content'][i])
                                    }
                                }
                            } else {
                                for (let i = 0; i < single_comment_dic['content'].length; i++) {
                                    let reg1 = /https:/
                                    if (reg1.test(single_comment_dic['content'][i])) {
                                        let imgEle = $('<img>')
                                        imgEle.attr('src', single_comment_dic['content'][i])
                                        imgEle.attr('style', 'width: 1.2em; margin-left: 0.2em; position: relative; bottom: 0.1em;')
                                        comment_user_content.append(imgEle)
                                    } else {
                                        comment_user_content.append(single_comment_dic['content'][i])
                                    }
                                }
                            }
                            comment_user_content.attr('style', 'text-align: left; color: #353535; line-height: 1.5em;')
                            comment_outer_div.append(comment_user_name)
                            comment_outer_div.append(comment_user_content)
                            // 评论时间和回复按钮标签
                            let time_and_replay = $('<div class="time_and_replay clearfix"></div>')
                            time_and_replay.attr('style', 'text-align: left;')
                            let comment_time = $('<span class="comment_time"></span>').text(single_comment_dic['comment_time'])
                            comment_time.attr('style', 'font-size: 88%; color: grey;')
                            time_and_replay.append(comment_time)
                            let delete_button = $('<button type="button" class="el-button el-button--text"><span>删除</span></button>')
                            delete_button.attr('style', 'padding: 0; float: right; color: rgb(249, 151, 9)')
                            delete_button.attr('comment_id', single_comment_dic['comment_id'])  // 给删除按钮绑定评论 id
                            delete_button.on('click', function () {
                                delete_comment(single_comment_dic['comment_user_id'], single_comment_dic['comment_id'], single_comment_dic['weibo_id'])
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
                                center: true
                            })
                        }
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },

            // 点击删除评论按钮时
            delete_comment(comment_user_id, comment_id, weibo_id) {
                if (!this.$cookies.get('user_id')) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.$confirm('确认删除这条评论吗？', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        let user_id = parseInt(this.user_id)
                        this.$axios({
                            method: 'post',
                            url: this.$settings.base_url + '/weibo/delete_comment/post/',
                            headers: {Authorization: this.token},
                            data: {
                                user_id: user_id,
                                comment_user_id: comment_user_id,
                                comment_id: comment_id
                            }
                        }).then(response => {
                            if (response.data.code === 200) {
                                this.$message({
                                    message: response.data.msg,
                                    type: 'success',
                                    duration: 1500,
                                    center: true
                                })
                                // 删除成功，将当前评论标签删掉
                                document.getElementById(comment_id).remove()
                                // 将显示评论数按钮的值 -1
                                let comment_num = parseInt(document.getElementById(weibo_id).children[2].children[0].children[1].innerText)
                                document.getElementById(weibo_id).children[2].children[0].children[1].innerText = comment_num - 1
                            } else {
                                this.$message({
                                    message: response.data.msg,
                                    type: 'error',
                                    duration: 1500,
                                    center: true
                                })
                            }
                        }).catch(error => {
                            this.$message({
                                message: error,
                                type: 'error',
                                duration: 1500,
                                center: true
                            })
                        })
                    }).catch(() => {
                    });
                }
            },

            // 点击回复评论按钮时，给输入框加入要回复的人名
            replay_comment(comment_username, comment_id, weibo_id) {
                if (!this.$cookies.get('user_id')) {
                    this.$message({
                        message: '请先登录',
                        type: 'warning',
                        center: true
                    })
                } else {
                    this.parentID = comment_id;
                    this.textarea = '@' + comment_username + '：';
                    // 输入框获取焦点
                    let current_weibo = document.getElementById(weibo_id)
                    current_weibo.children[3].children[0].children[0].children[0].focus()
                }
            },

            // 打开新标签页，跳转到个人页面
            other_user_page(other_id) {
                if (this.user_id == other_id) {  // 如果点击的是自己的头像，就进入 MyWeibo 的页面
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

            // 将后端返来的单条微博表情的URL变为img标签
            replaceWeiboContent() {
                // 拿到类名是 single-weibo-content 的 div 标签（微博内容在内部显示）
                let ele = $('.single-weibo-content')[0]
                // 清空标签原内容
                ele.innerText = '';
                // 使用后端传来的数据替换
                let content_lis = this.single_weibo_dic.content;  // 拿到后端传来的对应微博内容
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
            },
        },

        created() {
            this.single_weibo_id = sessionStorage.getItem('single_weibo_id')


            // 向后端请求加载微博
            this.$axios.get(this.$settings.base_url + '/weibo/one_weibo/', {
                params: {
                    weibo_id: this.single_weibo_id
                }
            }).then(res => {
                if (res.data.code === 200) {
                    this.single_weibo_dic = res.data.result
                    // 设置定时器 给网页一点渲染的时间 然后再执行微博内容替换方法
                    clearTimeout(this.timer);
                    this.timer = setTimeout(() => {
                        this.replaceWeiboContent()
                    }, 10)
                } else {
                    this.no_weibo = res.data.msg
                }
            }).catch(error => {

            })

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
        background-color: rgb(249, 151, 9);
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

    #top_block {
        margin-top: 5em;
    }

    .container {
        min-height: 1177px;
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

    .list-item {
        padding-top: 1.1em;
        margin-bottom: 1.5em;
        background-color: #fafafa;
        border-top: 2px solid rgb(249, 151, 9);
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12);
    }

    /* 单条微博上部分样式 >>> */
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
        width: 10%;
        border-radius: 50%;
        float: left;
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

    /* 下拉菜单样式 >>> */
    .el-dropdown {
        float: right;
        right: 4%;
        padding-top: 1%;
    }

    .el-dropdown-link {
        cursor: pointer;
        color: black;
    }

    .el-icon-arrow-down {
        font-size: 100%;
    }

    .el-icon-arrow-down.el-icon--right {
        margin-left: 0;
    }

    .el-dropdown-link:hover {
        color: rgb(249, 151, 9);
    }

    /* <<< 下拉菜单样式 */
    /* <<< 单条微博上部分样式 */

    /* 单条微博中部分样式 >>> */
    .single-weibo-middle {
        margin-left: 14.8%;
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
        margin-left: 3px;
        margin-right: 3px;
    }

    ::v-deep .el-image img {
        object-fit: cover;
    }

    /* <<< 单条微博中部分样式 */

    /* 单条微博下部分样式 >>> */
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

    /* <<< 单条微博下部分样式 */

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


    /* 屏幕宽度小于600px时的样式 >>> */
    @media screen and (max-width: 600px) {
        .container {
            padding-right: 0;
            padding-left: 0;
        }

        .weibo-content {
            padding-right: 15px;
            padding-left: 15px;
        }

        .list-item {
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
            width: 12%;
            border-radius: 50%;
            float: left;
        }

        .el-dropdown {
            right: 2%;
            padding-top: 1%;
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
    }

    /* <<< 屏幕宽度小于600px时的样式 */

    /* 屏幕宽度小于414px时的样式 >>> */
    @media screen and (max-width: 414px) {
        .el-image {
            width: 95px;
            height: 95px;
            margin-left: 3px;
            margin-right: 3px;
        }
    }

    /* <<< 屏幕宽度小于414px时的样式 */

    /* 屏幕宽度小于393px时的样式 >>> */
    @media screen and (max-width: 393px) {
        .el-image {
            width: 92px;
            height: 92px;
            margin-left: 3px;
            margin-right: 3px;
        }
    }

    /* <<< 屏幕宽度小于393px时的样式 */

    /* 屏幕宽度小于373px时的样式 >>> */
    @media screen and (max-width: 373px) {
        .el-image {
            width: 75px;
            height: 75px;
            margin-left: 2px;
            margin-right: 2px;
        }
    }

    /* <<< 屏幕宽度小于373px时的样式 */

</style>
