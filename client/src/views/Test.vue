<template>
    <div id="test">
        <div v-for="point in pointList">
            {{point}}
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                pointList: [],
            }
        },
        created() {
            this.mapPoints()
        },
        methods: {
            // 获取地图标记点
            mapPoints() {
                console.log(this.pointList)
                this.$axios.get('/api/location/all_location').then(result => {
                    result.data.locations
                })
            }
        }
    }
</script>

<style scoped>

</style>
