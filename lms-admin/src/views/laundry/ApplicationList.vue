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

                <el-button-group style="margin-left: 100px;">
                    <el-button size="mini" :disabled="selectedRows.length==0" @click="acceptApplication(true)">接受
                    </el-button>
                    <el-button size="mini" :disabled="selectedRows.length==0" @click="acceptApplication(false)">拒绝
                    </el-button>
                </el-button-group>
            </div>

            <el-table height="600px" :data="applicationList" style="width: 100%;text-align: center" stripe
                      highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="phone" label="手机号" width="100"></el-table-column>
                <el-table-column prop="userId" label="用户id" width="100"></el-table-column>
                <el-table-column prop="description" label="申请内容"></el-table-column>
                <!--<el-table-column prop="reason" label="拒绝理由" width="120"></el-table-column>-->
                <el-table-column label="状态" width="120">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.state==10">待审核</el-tag>
                        <el-tag v-if="scope.row.state==20" :type="'success'">待通过</el-tag>
                        <el-tag v-if="scope.row.state==30" :type="'danger'">已拒绝</el-tag>
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

    export default {
        name: "ApplicationList",
        data() {
            return {
                targetApplicationId: 0,
                cellDialogVisible: false,
                cellNum: 16,
                pageNum: 1,
                pageSize: 20,
                totalNum: 0,
                application: {},
                applicationDialogVisible: false,
                state: null,
                editMode: false,
                keyWord: "",
                selectedRows: [],
                applicationList: [],
                laundryList: [],
                goodsTypeList: [],
                catalogList: [],
            };
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
            acceptApplication(accept) {

            },
            query() {
                let params = {pageNum: this.pageNum, pageSize: this.pageSize, keyWord: this.keyWord};
                this.$http.post("/application/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.applicationList = res.data.data;
                        this.pageNum = res.data.pageNum;
                        this.pageSize = res.data.pageSize;
                        this.totalNum = res.data.totalNum;
                    }
                });
            },
            queryCatalog() {

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
                this.application = {};
            },
            openCellDialog(applicationId) {
                this.cellDialogVisible = true;
                this.targetApplicationId = applicationId;
            },
            openEditOrInsertDialog(row) {
                if (row) {
                    this.application = row;
                    this.editMode = true;
                } else {
                    this.application = {};
                    this.editMode = false;
                }
                this.applicationDialogVisible = true;
            },
            onSelectionChange(rows) {
                this.selectedRows = rows;
            },
        }
    };
</script>
