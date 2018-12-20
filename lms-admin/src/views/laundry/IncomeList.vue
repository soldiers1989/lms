<template>
    <div>
        <el-card shadow="never" :body-style="{ padding: '0px' }">
            <div slot="header">

                <el-input style="width: 200px" size="mini" v-model="keyWord" placeholder="关键字"></el-input>
                <el-button type="primary" size="mini" style="margin-left: 20px;" icon="el-icon-search" @click="query">
                    查询
                </el-button>
                <el-button type="primary" size="mini" style="margin-left: 20px;" @click="query">
                    刷新
                </el-button>
                <my-laundry-selector style="margin-left: 10px; float: right;"
                                     @change="laundryChange"></my-laundry-selector>
                <el-button size="mini" :disabled="selectedRows.length==0" @click="withdraw">结算</el-button>
            </div>

            <el-table height="600px" :data="incomeList" style="width: 100%;text-align: center" stripe
                      highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="id" label="订单ID" width="80"></el-table-column>
                <el-table-column prop="orderCode" label="订单编号" width="100"></el-table-column>
                <el-table-column prop="laundryName" label="洗衣店id" width="80"></el-table-column>
                <el-table-column prop="laundryName" align="center" label="洗衣店名称" ></el-table-column>
                <el-table-column prop="dividePercent" label="提成比例(%)" width="100"></el-table-column>
                <el-table-column prop="actIncome" label="商户收入" width="150"></el-table-column>
                <el-table-column prop="sysIncome" label="系统收入" width="150"></el-table-column>
                <el-table-column prop="actCost" label="用户付款" width="140"></el-table-column>
                <el-table-column label="状态" width="90">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.withdrew==0">待结算</el-tag>
                        <el-tag v-if="scope.row.withdrew==1">已结算</el-tag>
                    </template>
                </el-table-column>
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
    import qs from 'qs';
    import MyLaundrySelector from "../coms/MyLaundrySelector"

    export default {
        name: "IncomeList",
        data() {
            return {
                pageNum: 1,
                pageSize: 20,
                totalNum: 0,
                income: {},
                state: null,
                keyWord: "",
                selectedRows: [],
                incomeList: [],
            };
        },
        components: {
            MyLaundrySelector
        },
        mounted() {
            this.query();
        },
        methods: {
            getIdList() {
                let idList = [];
                for (let index in this.selectedRows) {
                    idList.push(this.selectedRows[index].id);
                }
                return idList;
            },
            query() {
                let params = {pageNum: this.pageNum, pageSize: this.pageSize, keyWord: this.keyWord};
                if (this.laundryId) {
                    params.laundryId = this.laundryId;
                }
                this.$http.post("/income/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.incomeList = res.data.data;
                    }
                });
            },
            handleSizeChange(pageSize) {
                this.pageSize = pageSize;
                this.query();

            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum;
                this.query();
            },
            closeEditDialog() {
                this.income = {};
            },
            withdraw() {
                let idList = this.getIdList();
                if (!idList || idList.length == 0) {
                    return;
                }
                let params = {orderIdList: idList};
                this.$http.post("/income/withdraw", qs.stringify(params)).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                    }
                });
            },
            laundryChange(laundryId) {
                this.laundryId = laundryId;
                this.query();
            },
            onSelectionChange(rows) {
                this.selectedRows = rows;
            },
        }
    };
</script>
