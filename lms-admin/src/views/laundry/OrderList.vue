<template>
    <div>
        <el-card shadow="never" :body-style="{ padding: '0px' }">
            <div style="margin:10px;">
                <el-date-picker size="mini" type="daterange" v-model="dateRange" align="right" unlink-panels
                                range-separator="至"
                                start-placeholder="订单开始日期"
                                end-placeholder="订单结束日期"
                                :picker-options="$store.state.dateRangePickerOptions">
                </el-date-picker>
                <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-search" @click="query">
                    查询
                </el-button>
            </div>
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                <el-menu-item index="1">待接单</el-menu-item>
                <el-menu-item index="2">待存放</el-menu-item>
                <el-menu-item index="3">待提货</el-menu-item>
                <el-menu-item index="4">已提货</el-menu-item>
                <el-menu-item index="5">待付款</el-menu-item>
                <el-menu-item index="6">待清洁</el-menu-item>
                <el-menu-item index="7">待送出</el-menu-item>
                <el-menu-item index="8">已失效</el-menu-item>
            </el-menu>
            <el-button-group style="padding: 10px;">
                <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="selectedRows.length==0"
                           @click="acceptOrder" v-if="tabIndex==1">接单
                </el-button>
                <el-button size="mini" icon="el-icon-delete" v-if="tabIndex==1">取消订单</el-button>
                <el-button size="mini" type="primary" v-if="tabIndex==4">提交价格</el-button>
                <el-button size="mini" type="primary" v-if="tabIndex==6">清洁完成</el-button>
                <el-button size="mini" type="primary" v-if="tabIndex==7">发出</el-button>
            </el-button-group>
            <el-table :data="pager.data" style="width: 100%" stripe highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55" align="center">
                </el-table-column>
                <el-table-column prop="code" label="订单编号"></el-table-column>
                <el-table-column prop="count" width="120" label="数量"></el-table-column>
                <el-table-column prop="wardrobeId" width="120" label="柜子id"></el-table-column>
                <el-table-column prop="commitTime" label="提交时间"></el-table-column>
                <el-table-column prop="expiredTime" label="超时时间"></el-table-column>
                <!--<el-table-column prop="commitTime" label="金额"></el-table-column>-->
            </el-table>
            <el-pagination :current-page="pager.pageNum" :page-size="pager.pageSize" :total="pager.totalNum"
                           class="pagination text-right" :page-sizes="$store.state.paginationPageSizes"
                           :layout="$store.state.paginationLayout"></el-pagination>
        </el-card>
    </div>
</template>
<style>
    .input-with-select .el-input-group__prepend {
        background-color: #fff;
    }
</style>
<script>
    import qs from 'qs'

    export default {
        name: "orderList",
        data() {
            return {
                activeIndex: "1",
                tabIndex: 1,
                dialogVisible: false,
                state: null,
                dateRange: null,
                test: null,
                test1: null,
                orderTab: "",
                selectedRows: [],
                bucketName: "public",
                pager: {current: 1, size: 10, total: 0, records: []}
            };
        },
        mounted() {
            this.query();
        },
        methods: {
            handleSelect(index) {
                this.tabIndex = index;
                console.log(index);
            },
            query() {
                this.$http.get("/order/selectByLaundryId").then(res => {
                    this.pager = res.data;
                });
            },
            insert() {

            },
            openEditOrInsertDialog() {
                this.dialogVisible = true;
            },
            acceptOrder() {
                let params = {orderId: this.selectedRows[0].id};
                console.log(params);
                console.log(this.selectedRows);
                this.$http.post("/order/accept", qs.stringify(params)).then(res => {

                });
            },
            onSelectionChange(rows) {
                this.selectedRows = rows;
            },
            remove() {
                this.$confirm("此操作将不能恢复, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                });
            },
            onRemoveFile(file) {
                this.$http.delete(`/oss/remove/${this.bucketName}/${file.response}`);
            }
        }
    };
</script>
