<template>
    <div class="middle_right">
        <div class="right-page">
            <div class="comment-block">
                <div class="page-title">
                    <span>热门日记</span>
                </div>
                <ul class="hot_topic">
                    <li v-for="hotItem in hotList">
                        <div class="media-left media-middle">
                            <a target="_blank" href="#">
                                <img class="media-object" :src="hotItem.user.avatar" alt="...">
                            </a>
                        </div>
                        <div class="media-body">
                            <a target="_blank" href="#" class="media-heading">
                                <span>#{{ hotItem.diary.content.slice(0, 10) }}#</span>
                            </a>
                            <br>
                            <span class="reads-comments">{{ hotItem.diary.likeNum }}点赞&nbsp{{hotItem.diary.commentNum }}讨论</span>
                        </div>
                    </li>
                </ul>
                <div class="more">
                    <a href="#">
                        <span>查看更多</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                hotList: []
            }
        },
        computed: {
            avatar() {
                return this.$store.state.avatar
            }
        },
        created() {
            this.allDiary()
        },
        methods: {
            allDiary() {
                this.$axios.get('/api/diary/all_diary', {
                    params: {
                        pageNumber: 0,
                        pageSize: 10,  // 每页查询条数
                        orderMode: 1 // 0时间，1热度
                    }
                }).then(result => {
                    this.hotList = result.data.diaries
                })
            }
        }
    }
</script>

<style scoped>

    .comment-block {
        background-color: #f5f5f5;
        border-top-style: solid;
        border-top-width: 1px;
        border-top-color: rgb(249, 151, 9);
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12)
    }

    .page-title {
        text-align: left;
        margin-left: 1em;
        margin-top: 1em;
        font-weight: bold;
        font-size: large;
    }

    .more {
        border-top: 1px solid rgba(128, 128, 128, 0.33);
        padding: 6px 0;
    }

    .more span {
        font-size: small;
    }

    .more a:hover {
        color: rgb(249, 151, 9);
        text-decoration: none;
    }

    .media-object {
        width: 3.12em;
    }

    .media-left.media-middle {
        padding-right: 0;
    }

    .media-body {
        text-align: left;
        padding-left: 10px;
    }

    .media-heading {
        font-size: 13px;
    }

    .media-heading:hover {
        color: rgb(249, 151, 9);
        text-decoration: none;
    }

    .reads-comments {
        font-size: 12px;
        color: grey;
        font-weight: 500;
    }

    .hot_topic {
        padding-left: 1em;
        padding-top: 1.2em;
    }

    .hot_topic li {
        padding-bottom: 1em;
    }

</style>
