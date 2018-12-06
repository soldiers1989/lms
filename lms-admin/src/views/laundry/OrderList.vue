<template>
    <div>
        <el-card shadow="never" :body-style="{ padding: '0px' }">
            <div slot="header">
                <el-row :gutter="10">
                    <el-col :span="18">
                        <el-date-picker type="daterange" v-model="dateRange" align="right" unlink-panels
                                        range-separator="至"
                                        start-placeholder="订单开始日期"
                                        end-placeholder="订单结束日期"
                                        :picker-options="$store.state.dateRangePickerOptions">
                        </el-date-picker>
                    </el-col>
                    <el-col :span="6" style="float: right;">
                        <el-button type="primary" size="mini" icon="el-icon-search" @click="query">查询</el-button>
                    </el-col>
                </el-row>
                <br>
                <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="selectedRows.length==0">接单
                </el-button>
                <el-button size="mini" icon="el-icon-delete" :disabled="selectedRows.length==0">取消</el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0">提交价格</el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0">清洁完成</el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0">发出</el-button>
            </div>
            <el-table :data="pager.data" style="width: 100%" stripe highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55" align="center">
                </el-table-column>
                <el-table-column prop="code" label="订单编号"></el-table-column>
                <el-table-column label="状态" width="120">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.state==10" :type="'success'">待接单</el-tag>
                        <el-tag v-if="scope.row.state>=30 ||scope.row.state<40" :type="'danger'">已超时</el-tag>
                    </template>
                </el-table-column>
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
    export default {
        name: "orderList",
        data() {
            return {
                dialogVisible: false,
                state: null,
                dateRange: null,
                test: null,
                test1: null,
                selectedRows: [],
                bucketName: "public",
                pager: {current: 1, size: 10, total: 0, records: []}
            };
        },
        mounted() {
            this.query();
        },
        methods: {
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
            onSelectionChange(rows) {
                this.selectedRows = rows.map(item => item.userId);
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
