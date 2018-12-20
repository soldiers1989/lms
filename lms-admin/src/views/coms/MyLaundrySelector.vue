<template>
    <div style="width: 200px;">
        <el-select v-model="value" placeholder="请选择" size="mini" @change="change">
            <el-option
                    v-for="item in options"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
            </el-option>
        </el-select>
    </div>
</template>

<script>
    import qs from 'qs'

    export default {
        name: "MyLaundrySelector",
        data() {
            return {
                options: [],
                value: 1,
                userId: localStorage.getItem("userInfo").id,
            }
        },
        mounted: function () {
            this.query();
        },

        methods: {
            query() {
                this.$http.post("/laundry/getMyLaundry", qs.stringify({
                    userId: this.userId,
                    pageNum: 1,
                    pageSize: 2000
                })).then(res => {
                    this.options = res.data.data;
                });
            },
            translate() {

            },
            change(value) {
                this.$emit("change", value);
            },
        }
    }
</script>

<style scoped>

</style>