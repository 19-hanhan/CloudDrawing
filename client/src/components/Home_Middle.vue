<template>
    <div class="home-middle">
        <div id="top_block"></div>
        <div class="container">
            <div class="col-md-2">
                <Middle_Left/>
            </div>
            <div class="col-md-7">
                <Middle_Middle/>
            </div>
            <div class="col-md-3">
                <Middle_Right/>
            </div>
        </div>
    </div>
</template>

<script>
    import Middle_Left from "./Middle_Left";
    import Middle_Middle from "./Middle_Middle";
    import Middle_Right from "./Middle_Right";

    export default {
        name: "Home_Middle",
        components: {
            Middle_Left,
            Middle_Middle,
            Middle_Right
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
    }

    @media screen and (max-width: 600px) {
        .container {
            padding-right: 0;
            padding-left: 0;
        }

        .col-md-7 {
            padding-right: 0;
            padding-left: 0;
        }

        #top_block {
            margin-top: 53px;
        }


    }
</style>
