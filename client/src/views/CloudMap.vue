<template>
    <div id="cloud-map">
        <div class="cloud-header">
            <Header/>
        </div>
        <!-- 工具栏 -->
        <div class="cloud-tool">
            <div class="box">
                <div class="searchBox">
                    <input type="text" placeholder="请输入关键词进行搜索" class="searchInput" style="padding-left: 10px;">
                    <input type="button" @click="changePlace" value="搜索" class="searchButton">
                </div>
            </div>
            <div class="change-bgc-buttons">
                <el-button class="change-bgc-button" type="primary" plain size="small" @click="changeBgcLight">浅色
                </el-button>
                <br>
                <el-button class="change-bgc-button" type="danger" plain size="small" @click="changeBgcDark">深色
                </el-button>
            </div>
            <div class="center-location">
                <p>当前位置：{{ centerLocation.lng }}，{{ centerLocation.lat }}</p>
            </div>
        </div>
        <!-- 地图 -->
        <div class="cloud-mid">
            <baidu-map
                    :center="place"
                    :min-zoom="minZoom"
                    :max-zoom="maxZoom"
                    :zoom="zoom"
                    :style="'width: 100%; height:' + fullHeight + 'px'"
                    :map-style="mapBgc"
                    :scroll-wheel-zoom=true
                    @moving="syncCenterAndZoom"
                    @moveend="syncCenterAndZoom"
                    @zoomend="syncCenterAndZoom"
                    @ready="changeBgcDark"
            >
                <bm-scale anchor="BMAP_ANCHOR_BOTTOM_RIGHT"></bm-scale>
                <!--                <bm-local-search :keyword="keyword" :auto-viewport="true" :centerLocation="place"></bm-local-search>-->
                <bm-marker v-for="pointItem in pointList" :key="pointItem.id" :position="{lng: pointItem.lng, lat: pointItem.lat}"
                           animation="BMAP_ANIMATION_BOUNCE" @click="infoWindowOpen(pointItem)">
                    <bm-info-window
                            :show="pointItem.show"
                            @open="infoWindowOpen(pointItem)"
                            @close="infoWindowClose(pointItem)"
                            style="height: 200px; width: 250px"
                    >
                        {{ pointItem.name }}
                        <wordcloud
                                :data="pointItem.keyword"
                                nameKey="name"
                                valueKey="value"
                                :color="wordColor"
                                :wordClick="wordClickHandler"
                                :rotate="{from: -30, to: 30, numOfOrientation: 10}"
                                :fontSize="[10, 30]"
                                :margin="{top: 3, right: 3, bottom: 3, left: 3 }"
                                :wordPadding="5"
                                fontScale="log"
                                spiral="rectangular"
                                :showTooltip="false"
                                style="height: 200px; width: 250px"
                        ></wordcloud>
                    </bm-info-window>
                </bm-marker>
            </baidu-map>
        </div>
    </div>
</template>

<script>
    import Header from "../components/Header";
    import wordcloud from 'vue-wordcloud'

    export default {
        components: {
            Header,
            wordcloud
        },
        data() {
            return {
                fullHeight: document.documentElement.clientHeight,
                mapBgc: {
                    type: null,
                    styleJson: []
                }, // 地图背景样式
                place: '南昌', // 地图中心位置
                keyword: [
                    // '南昌三中（新校区）',
                    // '江西师',
                    // '江西师范大学青山湖校区',
                ], // 查询键
                centerLocation: {
                    lng: null,
                    lat: null,
                }, // 中心经纬度
                minZoom: 12, // 最小视图大小
                maxZoom: 17, // 最大视图大小
                zoom: 15, // 初始加载视图大小
                pointList: [],
                // pointList: [
                //     {
                //         id: 1,
                //         name: '滕王阁', // 地点名称
                //         lng: 115.887582, // 经度
                //         lat: 28.688093, // 纬度
                //         keyword: [ // 词云关键词
                //             {name: '高雅', value: 26},
                //             {name: '美丽', value: 20},
                //             {name: '富饶', value: 18},
                //             {name: '恢弘', value: 17},
                //             {name: '历史', value: 15},
                //             {name: '学习', value: 14},
                //             {name: '探索', value: 11},
                //             {name: '古韵', value: 9},
                //             {name: '科技', value: 5},
                //             {name: '建设', value: 4}
                //         ],
                //         show: false
                //     },
                //     {
                //         id: 2,
                //         name: '羊子巷',
                //         lng: 115.904687,
                //         lat: 28.682361,
                //         keyword: [
                //             {name: '高雅', value: 26},
                //             {name: '美丽', value: 20},
                //             {name: '富饶', value: 18},
                //             {name: '恢弘', value: 17},
                //             {name: '历史', value: 15},
                //             {name: '学习', value: 14},
                //             {name: '探索', value: 11},
                //             {name: '古韵', value: 9},
                //             {name: '科技', value: 5},
                //             {name: '建设', value: 4}
                //         ],
                //         show: false
                //     },
                //     {
                //         id: 3,
                //         name: '恒茂梦时代广场',
                //         lng: 115.94569,
                //         lat: 28.679047,
                //         keyword: [
                //             {name: '高雅', value: 26},
                //             {name: '美丽', value: 20},
                //             {name: '富饶', value: 18},
                //             {name: '恢弘', value: 17},
                //             {name: '历史', value: 15},
                //             {name: '学习', value: 14},
                //             {name: '探索', value: 11},
                //             {name: '古韵', value: 9},
                //             {name: '科技', value: 5},
                //             {name: '建设', value: 4}
                //         ],
                //         show: false
                //     },
                // ],
                wordColor: ['#1f77b4', '#629fc9', '#94bedb', '#c9e0ef']
            }
        },
        computed: {},
        created() {
            // 动态获取浏览器高度
            window.onresize = () => {
                return (() => {
                    window.fullHeight = document.documentElement.clientHeight
                    this.fullHeight = window.fullHeight
                })()
            }
            this.mapPoints()
        },
        methods: {
            // 修改查询的地址
            changePlace() {
                var place = $('.searchInput')[0].value;
                this.place = place;
            },

            // 修改字体颜色
            changeFontColor(bgc) {
                let fontLocation = $('.center-location');
                if (bgc === 'dark') {
                    fontLocation.attr('style', 'color: white;')
                } else if (bgc === 'white') {
                    fontLocation.attr('style', 'color: black;')
                }
            },

            // 修改背景为暗色
            changeBgcDark() {
                this.mapBgc.styleJson = [{
                    "featureType": "land",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#091220ff"
                    }
                }, {
                    "featureType": "water",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#113549ff"
                    }
                }, {
                    "featureType": "green",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#0e1b30ff"
                    }
                }, {
                    "featureType": "building",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "building",
                    "elementType": "geometry.topfill",
                    "stylers": {
                        "color": "#113549ff"
                    }
                }, {
                    "featureType": "building",
                    "elementType": "geometry.sidefill",
                    "stylers": {
                        "color": "#143e56ff"
                    }
                }, {
                    "featureType": "building",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#dadada00"
                    }
                }, {
                    "featureType": "subwaystation",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#113549B2"
                    }
                }, {
                    "featureType": "education",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "medical",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "scenicspots",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "weight": 4
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#fed66900"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "weight": 2
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffeebb00"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "weight": 1
                    }
                }, {
                    "featureType": "local",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#979c9aff"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "railway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "weight": 1
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#d8d8d8ff"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#979c9aff"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "continent",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "continent",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "continent",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "continent",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "city",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "city",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "city",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "city",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "town",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "town",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "town",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#454d50ff"
                    }
                }, {
                    "featureType": "town",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "road",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "poilabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "districtlabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "road",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "road",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "road",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "district",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "poilabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "poilabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "poilabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "manmade",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "districtlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "entertainment",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "shopping",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "nationalway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "6"
                    }
                }, {
                    "featureType": "nationalway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "7"
                    }
                }, {
                    "featureType": "nationalway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "8"
                    }
                }, {
                    "featureType": "nationalway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "9"
                    }
                }, {
                    "featureType": "nationalway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "10"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "6"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "7"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "8"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "9"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "10"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "6"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "7"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "8"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "9"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,10",
                        "level": "10"
                    }
                }, {
                    "featureType": "cityhighway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "6"
                    }
                }, {
                    "featureType": "cityhighway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "7"
                    }
                }, {
                    "featureType": "cityhighway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "8"
                    }
                }, {
                    "featureType": "cityhighway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "9"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "6"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "7"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "8"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "9"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "6"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "7"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "8"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,9",
                        "level": "9"
                    }
                }, {
                    "featureType": "subwaylabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "subwaylabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "tertiarywaysign",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "tertiarywaysign",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "provincialwaysign",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "provincialwaysign",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "nationalwaysign",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "nationalwaysign",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "highwaysign",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "highwaysign",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "village",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "district",
                    "elementType": "labels.text",
                    "stylers": {
                        "fontsize": 20
                    }
                }, {
                    "featureType": "district",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "district",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "country",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "country",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "water",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "water",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "tertiaryway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "tertiaryway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff10"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels.text",
                    "stylers": {
                        "fontsize": 20
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels.text",
                    "stylers": {
                        "fontsize": 20
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "labels.text",
                    "stylers": {
                        "fontsize": 20
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels.text",
                    "stylers": {
                        "fontsize": 20
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "estate",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "tertiaryway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "tertiaryway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "fourlevelway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "fourlevelway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "scenicspotsway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "scenicspotsway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "universityway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "universityway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "vacationway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "vacationway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "fourlevelway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "fourlevelway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#12223dff"
                    }
                }, {
                    "featureType": "fourlevelway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "transportationlabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "transportationlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "transportationlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "transportationlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "educationlabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "educationlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "educationlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "educationlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "transportation",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#113549ff"
                    }
                }, {
                    "featureType": "airportlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "airportlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "scenicspotslabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "scenicspotslabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "medicallabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "medicallabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "medicallabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "scenicspotslabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "airportlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "entertainmentlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "entertainmentlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "entertainmentlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "estatelabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "estatelabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "estatelabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "businesstowerlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "businesstowerlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "businesstowerlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "companylabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "companylabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "companylabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "governmentlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "governmentlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "governmentlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "restaurantlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "restaurantlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "restaurantlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "hotellabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "hotellabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "hotellabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "shoppinglabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "shoppinglabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "shoppinglabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "lifeservicelabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "lifeservicelabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "lifeservicelabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "carservicelabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "carservicelabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "carservicelabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "financelabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "financelabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "financelabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "otherlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "otherlabel",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "otherlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "manmade",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "manmade",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "transportation",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "transportation",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "education",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "education",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "medical",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "medical",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }, {
                    "featureType": "scenicspots",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#2dc4bbff"
                    }
                }, {
                    "featureType": "scenicspots",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffff00"
                    }
                }]
                this.changeFontColor('dark');
            },

            // 修改背景为亮色
            changeBgcLight() {
                this.mapBgc.styleJson = [{
                    "featureType": "water",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#ccd6d7ff"
                    }
                }, {
                    "featureType": "green",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#dee5e5ff"
                    }
                }, {
                    "featureType": "building",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "building",
                    "elementType": "geometry.topfill",
                    "stylers": {
                        "color": "#d1dbdbff"
                    }
                }, {
                    "featureType": "building",
                    "elementType": "geometry.sidefill",
                    "stylers": {
                        "color": "#d1dbdbff"
                    }
                }, {
                    "featureType": "building",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#aab6b6ff"
                    }
                }, {
                    "featureType": "subwaystation",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "color": "#888fa0ff"
                    }
                }, {
                    "featureType": "education",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#e1e7e7ff"
                    }
                }, {
                    "featureType": "medical",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#d1dbdbff"
                    }
                }, {
                    "featureType": "scenicspots",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "color": "#d1dbdbff"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "weight": 4
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#cacfcfff"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "weight": 2
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#fbfffeff"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#cacfcfff"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "arterial",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "on",
                        "weight": 1
                    }
                }, {
                    "featureType": "local",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#fbfffeff"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#cacfcfff"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "local",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "railway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "weight": 1
                    }
                }, {
                    "featureType": "railway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#9494941a"
                    }
                }, {
                    "featureType": "railway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff1a"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "weight": 1
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#c3bed433"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#ffffff33"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#979c9aff"
                    }
                }, {
                    "featureType": "subway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "continent",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "continent",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "continent",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#333333ff"
                    }
                }, {
                    "featureType": "continent",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "city",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "city",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "city",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#454d50ff"
                    }
                }, {
                    "featureType": "city",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "town",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "on"
                    }
                }, {
                    "featureType": "town",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "town",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#454d50ff"
                    }
                }, {
                    "featureType": "town",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "road",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "road",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#fbfffeff"
                    }
                }, {
                    "featureType": "poilabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "districtlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "poilabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "districtlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#888fa0ff"
                    }
                }, {
                    "featureType": "transportation",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#d1dbdbff"
                    }
                }, {
                    "featureType": "companylabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "restaurantlabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "lifeservicelabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "carservicelabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "financelabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "otherlabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "village",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "district",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "land",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#edf3f3ff"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#cacfcfff"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#cacfcfff"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#cacfcfff"
                    }
                }, {
                    "featureType": "road",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#cacfcfff"
                    }
                }, {
                    "featureType": "subwaylabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "subwaylabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "tertiarywaysign",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "tertiarywaysign",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "provincialwaysign",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "provincialwaysign",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "nationalwaysign",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "nationalwaysign",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "highwaysign",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "highwaysign",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#fbfffeff"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "highway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "highway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "highway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "highway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "nationalway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "nationalway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "nationalway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "nationalway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "provincialway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "8,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "8,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "8,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "cityhighway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "cityhighway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "cityhighway",
                    "stylers": {
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "6"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "7"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off",
                        "curZoomRegionId": "0",
                        "curZoomRegion": "6,8",
                        "level": "8"
                    }
                }, {
                    "featureType": "cityhighway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#fbfffeff"
                    }
                }, {
                    "featureType": "water",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#8f5a33ff"
                    }
                }, {
                    "featureType": "water",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "country",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#8f5a33ff"
                    }
                }, {
                    "featureType": "country",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "country",
                    "elementType": "labels.text",
                    "stylers": {
                        "fontsize": 28
                    }
                }, {
                    "featureType": "manmade",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#dfe7e7ff"
                    }
                }, {
                    "featureType": "provincialway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#fbfffeff"
                    }
                }, {
                    "featureType": "tertiaryway",
                    "elementType": "geometry.fill",
                    "stylers": {
                        "color": "#fbfffeff"
                    }
                }, {
                    "featureType": "manmade",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "manmade",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "scenicspots",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "scenicspots",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "airportlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "airportlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "scenicspotslabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "scenicspotslabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "educationlabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "educationlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "medicallabel",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "medicallabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "companylabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "restaurantlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "hotellabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "hotellabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "shoppinglabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "shoppinglabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "lifeservicelabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "carservicelabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "transportationlabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "transportationlabel",
                    "elementType": "labels",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "financelabel",
                    "elementType": "labels.icon",
                    "stylers": {
                        "visibility": "off"
                    }
                }, {
                    "featureType": "entertainment",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#d1dbdbff"
                    }
                }, {
                    "featureType": "estate",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#d1dbdbff"
                    }
                }, {
                    "featureType": "shopping",
                    "elementType": "geometry",
                    "stylers": {
                        "color": "#d1dbdbff"
                    }
                }, {
                    "featureType": "education",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "education",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "medical",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "medical",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }, {
                    "featureType": "transportation",
                    "elementType": "labels.text.fill",
                    "stylers": {
                        "color": "#999999ff"
                    }
                }, {
                    "featureType": "transportation",
                    "elementType": "labels.text.stroke",
                    "stylers": {
                        "color": "#ffffffff"
                    }
                }]
                this.changeFontColor('white');
            },

            // 同步更新地图的中心位置信息
            syncCenterAndZoom(e) {
                const {lng, lat} = e.target.getCenter()
                this.centerLocation.lng = lng
                this.centerLocation.lat = lat
                this.zoom = e.target.getZoom()
            },

            // 弹窗打开事件处理器
            infoWindowOpen(item) {
                item.show = true;
            },

            // 弹窗关闭事件处理器
            infoWindowClose(item) {
                item.show = false;
            },

            // 词云点击事件处理
            wordClickHandler(name, value, vm) {
                console.log('wordClickHandler', name, value, vm);
                this.$router.push({
                    name: 'Home',
                    query: {
                        searchKeyword: name
                    }
                })
            },

            // 获取地图标记点
            mapPoints() {
                this.$axios.get('/api/location/all_location').then(result => {
                    result.data.locations.forEach(location => {
                        location.show = false
                    })
                    console.log(result.data.locations)
                    this.pointList = result.data.locations
                })
            }
        }
    }
</script>

<style scoped>
    * {
        padding: 0;
        margin: 0;
    }

    .center-location {
        position: fixed;
        bottom: 3%;
        left: 3%;
        z-index: 10;
        font-size: 16px;
    }

    .box {
        position: fixed;
        top: 60px;
        left: 10px;
        z-index: 10;
        height: 50px;
        width: 20%;
    }

    .searchBox {
        margin: 0 auto;
        position: relative;
    }

    .searchInput {
        display: inline-block;
        width: 75%;
        height: 38px;
        border: 1px solid #cccccc;
        float: left;
        box-sizing: border-box;
        -moz-box-sizing: border-box; /* Firefox */
        -webkit-box-sizing: border-box; /* Safari */
        border-bottom-left-radius: 5px;
        border-top-left-radius: 5px;
    }

    .searchButton {
        display: inline-block;
        width: 25%;
        height: 38px;
        line-height: 40px;
        float: left;
        background-color: #00a0e9;
        font-size: 16px;
        cursor: pointer;
        border-bottom-right-radius: 5px;
        border-top-right-radius: 5px;
        border: none;
        color: #fff;
    }

    .change-bgc-buttons {
        position: fixed;
        top: 60px;
        right: 10px;
        z-index: 10;
    }

    .change-bgc-button {
        margin: 5px;
        padding: 5px 15px;
        border-radius: 30px;
        font-size: large;
    }
</style>
