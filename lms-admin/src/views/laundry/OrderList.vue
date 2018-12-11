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
            <el-menu default-active="10" mode="horizontal" @select="handleSelect">
                <el-menu-item index="10">待接单</el-menu-item>
                <el-menu-item index="42">待存放</el-menu-item>
                <el-menu-item index="45">待提货</el-menu-item>
                <el-menu-item index="50">已提货</el-menu-item>
                <el-menu-item index="55">待付款</el-menu-item>
                <el-menu-item index="60">待清洁</el-menu-item>
                <el-menu-item index="63">清洁中</el-menu-item>
                <el-menu-item index="65">待送出</el-menu-item>
                <el-menu-item index="100">已完成</el-menu-item>
                <el-menu-item index="0">已失效</el-menu-item>
            </el-menu>
            <el-button-group style="padding: 10px;">
                <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="selectedRows.length==0"
                           @click="acceptOrder" v-if="activeIndex==10">接单
                </el-button>
                <!--<el-button size="mini" icon="el-icon-delete" :disabled="selectedRows.length==0" v-if="activeIndex==10">-->
                <!--取消订单-->
                <!--</el-button>-->
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0" v-if="activeIndex==50">提交价格
                </el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0" v-if="activeIndex==60">进入清洁
                </el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0" v-if="activeIndex==63">清洁完成
                </el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0" v-if="activeIndex==65">发出
                </el-button>
            </el-button-group>
            <el-table height="550" :data="orderList" style="width: 100%" stripe highlight-current-row
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
            <el-pagination align="center"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page="pageNum"
                           :page-sizes="[10, 20, 50]"
                           :page-size="pageSize"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="totalNum">
            </el-pagination>
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
                activeIndex: "10",
                dialogVisible: false,
                state: null,
                dateRange: null,
                test: null,
                test1: null,
                orderTab: "",
                selectedRows: [],
                bucketName: "public",
                pageNum: 1,
                pageSize: 20,
                totalNum: 0,
                orderList: [],
            };
        },
        mounted() {
            this.query();
        },
        methods: {
            handleSelect(index) {
                this.activeIndex = index;
                this.query();
            },
            query() {
                let params = {state: this.activeIndex, pageNum: this.pageNum, pageSize: this.pageSize};
                this.$http.post("/order/selectByLaundryId", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.orderList = res.data.data;
                    }
                });
            },
            insert() {

            },
            openEditOrInsertDialog() {
                this.dialogVisible = true;
            },
            acceptOrder() {
                let params = {orderId: this.selectedRows[0].id};
                this.$http.post("/order/accept", qs.stringify(params)).then(res => {

                });
            },
            onSelectionChange(rows) {
                this.selectedRows = rows;
            },
            handleSizeChange(size) {
                this.pageSize = size;
                this.query();
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum;
                this.query();
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
