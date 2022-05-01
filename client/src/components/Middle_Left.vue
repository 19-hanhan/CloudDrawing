<template>
    <div class="middle_left">
        <div class="go_home" id="divId">
            <ul>
                <li>
                    <a class="btn btn-default" href="/" role="button">热门</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">头条</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">视频</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">榜单</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">搞笑</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">社会</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">时尚</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">电影</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">美女</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">体育</a>
                </li>
                <li>
                    <a class="btn btn-default" href="/" role="button">动漫</a>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Middle_left",
        mounted() {//给window添加一个滚动滚动监听事件
            window.addEventListener('scroll', this.handleScroll)
        },

        methods: {
            handleScroll() { //改变元素#searchBar的top值
                var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
                var offsetTop = document.querySelector('#divId').offsetTop;

                if (scrollTop <= 25) {
                    offsetTop = 76 - Number(scrollTop);
                    document.querySelector('#divId').style.top = offsetTop + 'px';
                } else {
                    document.querySelector('#divId').style.top = '51px';
                }
            },
        },
        destroyed() {//离开该页面需要移除这个监听的事件
            window.removeEventListener('scroll', this.handleScroll)
        },
    }
</script>

<style scoped>
    @media screen and (max-width: 2600px) {
        .go_home {
            position: fixed;
            top: 76px; /*开始处于距离顶部76px的位置，之后随着滚动条滚动top值改变，然后在top==51时停止*/
            left: 29%;
            width: 6%;
            z-index: 99;
        }
    }

    @media screen and (max-width: 2350px) {
        .go_home {
            position: fixed;
            top: 76px; /*开始处于距离顶部76px的位置，之后随着滚动条滚动top值改变，然后在top==51时停止*/
            left: 27%;
            width: 6%;
            z-index: 99;
        }
    }

    @media screen and (max-width: 2080px) {
        .go_home {
            position: fixed;
            top: 76px; /*开始处于距离顶部76px的位置，之后随着滚动条滚动top值改变，然后在top==51时停止*/
            left: 25%;
            width: 7%;
            z-index: 99;
        }
    }

    @media screen and (max-width: 1920px) {
        .go_home {
            position: fixed;
            top: 76px; /*开始处于距离顶部76px的位置，之后随着滚动条滚动top值改变，然后在top==51时停止*/
            left: 22%;
            width: 8%;
        }
    }


    @media screen and (max-width: 1772px) {
        .go_home {
            position: fixed;
            top: 76px; /*开始处于距离顶部76px的位置，之后随着滚动条滚动top值改变，然后在top==51时停止*/
            left: 18%;
            width: 9%;
        }
    }

    @media screen and (max-width: 1650px) {
        .go_home {
            position: fixed;
            top: 76px; /*开始处于距离顶部76px的位置，之后随着滚动条滚动top值改变，然后在top==51时停止*/
            left: 14%;
            width: 10%;
        }
    }

    @media screen and (max-width: 1400px) {
        .go_home {
            position: fixed;
            top: 76px; /*开始处于距离顶部76px的位置，之后随着滚动条滚动top值改变，然后在top==51时停止*/
            left: 12%;
            width: 10%;
        }
    }

    .go_home ul li a {
        width: 80%;
        border: 0;
    }

    .go_home ul li a:hover {
        background-color: rgb(249, 151, 9);
        color: black;
    }

</style>
