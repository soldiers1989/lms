<template>
    <div>
        <el-card shadow="never" :body-style="{ padding: '0px' }">
            <div slot="header">
                <el-row :gutter="10">
                    <el-col :span="3">
                        <el-input v-model="keyWord" placeholder="关键字"></el-input>
                    </el-col>
                    <el-col :span="6" style="float: right;">
                        <el-button type="primary" icon="el-icon-search" @click="query">查询</el-button>
                    </el-col>
                </el-row>
                <br>
                <el-button size="medium" type="primary" icon="el-icon-plus" @click="dialogVisible = true">新增</el-button>
                <!--<el-button size="medium" icon="el-icon-delete" :disabled="selectedRows.length==0">删除</el-button>-->
            </div>
            <el-table height="550" :data="codeList" style="width: 100%" stripe highlight-current-row
                      v-loading="$store.state.loading">
                <el-table-column prop="uniqueCode" label="编码"></el-table-column>
                <el-table-column prop="description" label="描述"></el-table-column>
                <el-table-column label="状态" width="120">
                    <template slot-scope="scope">
                        <el-tag v-if="!scope.row.used" :type="'success'">空闲</el-tag>
                        <el-tag v-else :type="'danger'">使用中</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="上次使用" width="240">
                    <template slot-scope="scope">
                        {{ scope.row.modifyTime==scope.row.createTime?"无":scope.row.modifyTime }}
                    </template>
                </el-table-column>
                <el-table-column label="创建日期" width="240">
                    <template slot-scope="scope">
                        {{ scope.row.createTime }}
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

        <el-dialog title="新增编码" :visible.sync="dialogVisible">
            <el-form :model="codeObj" :label-position="'right'" label-width="80px">
                <el-form-item label="编码">
                    <el-input v-model="codeObj.uniqueCode"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input type="textarea" v-model="codeObj.description"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="generateCode">自动生成</el-button>
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="insert">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<style>


</style>
<script>
    import qs from 'qs'

    export default {
        name: "codeList",
        data() {
            return {
                keyWord: "",
                dialogVisible: false,
                state: null,
                dateRange: null,
                test: null,
                test1: null,
                codeObj: {
                    uniqueCode: "",
                    description: ""
                },
                selectedRows: [],
                codeList: [],
                bucketName: "public",
                pageNum: 1,
                pageSize: 20,
                totalNum: 0
            };
        },
        mounted() {
            this.query();
        },
        methods: {
            query() {
                let params = {pageNum: this.pageNum, pageSize: this.pageSize, keyWord: this.keyWord};
                // let params = this.pager;
                this.$http.post("/goodsCode/select", qs.stringify(params)).then(res => {
                    this.codeList = res.data.data;
                    this.pageNum = res.data.pageNum;
                    this.pageSize = res.data.pageSize;
                    this.totalNum = res.data.totalNum;
                });
            },
            handleSizeChange(size) {
                this.pageSize = size;
                this.query();
            },
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum;
                this.query();
            },
            generateCode() {

            },
            insert() {
                let that = this;
                this.codeIsValid(function () {
                    that.$http.post("/goodsCode/create", qs.stringify(that.codeObj)).then(res => {
                        if (res.result) {

                        }

                        that.dialogVisible = false;
                    });
                });
            },
            codeIsValid: function (callback) {
                this.$http.post("/goodsCode/checkCode", qs.stringify(this.codeObj)).then(res => {
                    if (res.data.result && res.data.data == 0) {
                        callback && callback();
                    } else {
                        this.$message.error("该编码已经存在!");
                    }
                });
            },
            openEditOrInsertDialog() {
                this.dialogVisible = true;
            },
        }
    };
</script>
