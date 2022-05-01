<template>
    <div id="test">
        <Header/>
        <baidu-map :center="center" :zoom="zoom" :style="'width: auto; height:' + fullHeight + 'px'"></baidu-map>
    </div>
</template>

<script>
    import Header from "../components/Header";
    export default {
        components: {
            Header
        },
        data() {
            return {
                fullHeight: document.documentElement.clientHeight,
                center: '南昌',
                zoom: 15
            }
        },
        watch: {
        },
        created () {
            // 动态获取浏览器高度
            window.onresize = () => {
                return (() => {
                    window.fullHeight = document.documentElement.clientHeight
                    this.fullHeight = window.fullHeight
                })()
            }
        },
        methods: {
        }
    }
</script>

<style>
</style>
