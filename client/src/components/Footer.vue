<template>
    <div class="footer">
        <p id="p1">CopyRight © 2020 - 2022 Light</p>
        <p id="p2">京ICP备20220108号-1</p>
    </div>
</template>

<script>
    export default {
        name: "Footer"
    }
</script>

<style scoped>
    .footer {
        width: 100%;
        height: 14%;
        background: rgb(59, 59, 59);
        color: #fff;
        margin-top: 3em;
    }

    #p1 {
        text-align: center;
        font-size: 90%;
        padding-top: 15px;
    }

    #p2 {
        font-size: 40%;
        text-align: center;
        padding-bottom: 10px;
        padding-top: 10px;
    }

    .footer ul li {
        display: inline-block;
        width: 112px;
        margin: 0 10px;
    }

    .footer ul {
        margin: 0 auto 2%;
        padding-top: 2%;
        width: 100%;
        text-align: center;
    }

    @media screen and (max-width: 600px) {
        #p1 {
            font-size: 80%;
        }

        #p2 {
            font-size: 60%;
        }
    }
</style>
