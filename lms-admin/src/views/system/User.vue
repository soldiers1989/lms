<template>
    <div>
        <el-card shadow="never" :body-style="{ padding: '0px' }">
            <div slot="header">
                <el-row :gutter="10">
                    <el-col :span="3">
                        <el-input placeholder="用户名" size="mini"></el-input>
                    </el-col>
                    <el-col :span="3">
                        <el-input placeholder="手机号码" size="mini"></el-input>
                    </el-col>
                    <!--<el-col :span="3">-->
                    <!--<el-select v-model="userType" placeholder="用户类型">-->
                    <!--<el-option :label="'普通用户'" :value="0"></el-option>-->
                    <!--<el-option :label="'配送员'" :value="10"></el-option>-->
                    <!--<el-option :label="'洗衣店'" :value="20"></el-option>-->
                    <!--<el-option :label="'运营人员'" :value="30"></el-option>-->
                    <!--</el-select>-->
                    <!--</el-col>-->
                    <!--<el-col :span="3">-->
                    <!--<el-select v-model="activated" placeholder="用户状态">-->
                    <!--<el-option :label="'启用'" :value="0"></el-option>-->
                    <!--<el-option :label="'禁用'" :value="1"></el-option>-->
                    <!--</el-select>-->
                    <!--</el-col>-->
                    <!--<el-col :span="6">-->
                    <!--<el-date-picker type="daterange" v-model="dateRange" align="right" unlink-panels-->
                    <!--range-separator="至"-->
                    <!--start-placeholder="注册开始日期"-->
                    <!--end-placeholder="注册结束日期"-->
                    <!--:picker-options="$store.state.dateRangePickerOptions">-->
                    <!--</el-date-picker>-->
                    <!--</el-col>-->
                    <el-col :span="6" style="float: right;">
                        <el-button type="primary" icon="el-icon-search" @click="query" size="mini">查询</el-button>
                    </el-col>
                </el-row>
                <br>
                <el-button type="primary" icon="el-icon-plus" @click="dialogVisible = true" size="mini">
                    新增
                </el-button>
                <el-button icon="el-icon-delete" :disabled="selectedRows.length==0" size="mini">删除
                </el-button>
            </div>
            <el-table height="550" :data="userList" style="width: 100%" stripe highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55" align="center">
                </el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column label="手机号码" width="150">
                    <template slot-scope="scope">
                        {{ scope.row.phone }}
                    </template>
                </el-table-column>
                <el-table-column label="状态" width="120">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.activated==1" :type="'success'">已启用</el-tag>
                        <el-tag v-else :type="'danger'">已禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="gender" label="性别" width="100"></el-table-column>
                <el-table-column label="创建日期" width="140">
                    <template slot-scope="scope">
                        {{ scope.row.createTime | moment('YYYY-MM-DD hh:mm') }}
                    </template>
                </el-table-column>
                <el-table-column label="最后登陆日期" width="140">
                    <template slot-scope="scope">
                        {{ scope.row.lastLogin | moment('YYYY-MM-DD hh:mm') }}
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="80">
                    <template slot-scope="scope">
                        <!--<el-button type="text" size="small" @click="openEditOrInsertDialog()">编辑</el-button>-->
                        <!--<el-button type="text" size="small" @click="remove()">删除</el-button>-->
                        <el-button type="text" size="mini" @click="openRelateLaundryDialog(scope.row.id)">关联洗衣店
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination align="center"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page="pageNum"
                           :page-sizes="[10,20,50]"
                           :page-size="pageSize"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="totalNum">
            </el-pagination>
        </el-card>


        <el-dialog title="关联洗衣店" :visible.sync="relateLaundryDialogVisible" @open="queryLaundry"
                   @close="targetUserId=0">
            <!--<el-input style="width: 200px" size="mini" v-model="wardrobeKeyWord" placeholder="关键字"></el-input>-->
            <!--<el-button type="primary" size="mini" style="margin-left: 20px;" icon="el-icon-search"-->
            <!--@click="queryWardrobe">-->
            <!--查询-->
            <!--</el-button>-->
            <el-table height="500px" :data="laundryList" style="width: 100%;text-align: center" stripe
                      highlight-current-row
                      v-loading="$store.state.loading">
                <!--<el-table-column type="selection" width="55" @selection-change="onLaundrySelectionChange">-->
                <!--</el-table-column>-->
                <el-table-column prop="code" label="编号"></el-table-column>
                <el-table-column prop="address" label="地址"></el-table-column>
                <el-table-column prop="phone" label="电话" width="120"></el-table-column>
                <el-table-column label="状态" width="80">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.activated==1" :type="'success'">已启用</el-tag>
                        <el-tag v-else :type="'danger'">已停用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="120" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="relateLaundry(scope.row.id,false)"
                                   v-if="targetUserId==scope.row.userId ">取消关联
                        </el-button>
                        <el-button type="text" size="small" @click="relateLaundry(scope.row.id,true)"
                                   v-if="!scope.row.userId">关联
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination align="center"
                           @size-change="handleLaundrySizeChange"
                           @current-change="handleLaundryCurrentChange"
                           :current-page="laundryPageNum"
                           :page-sizes="[10, 20]"
                           :page-size="laundryPageSize"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="laundryTotalNum">
            </el-pagination>
        </el-dialog>


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
        name: "user",
        data() {
            return {
                dialogVisible: false,
                state: null,
                dateRange: null,
                laundryPageNum: 1,
                laundryPageSize: 10,
                laundryTotalNum: 0,
                selectedRows: [],
                selectedLaundryRows: [],
                targetUserId: 0,
                relateLaundryDialogVisible: false,
                bucketName: "public",
                pageNum: 1,
                pageSize: 20,
                totalNum: 0,
                userList: [],
                laundryList: []
            };
        },
        mounted() {
            this.query();
        },
        methods: {
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum;
                this.query();
            },
            handleSizeChange(pageSize) {
                this.pageSize = pageSize;
                this.query();
            },
            handleLaundryCurrentChange(pageNum) {
                this.laundryPageNum = pageNum;
                this.queryLaundry();
            },
            handleLaundrySizeChange(pageSize) {
                this.laundryPageSize = pageSize;
                this.queryLaundry();
            },
            query() {
                let params = {};
                this.$http.get("/admin/allUser", qs.stringify(params)).then(res => {
                    this.userList = res.data.data;
                    this.pageNum = res.data.pageNum;
                    this.pageSize = res.data.pageSize;
                    this.totalNum = res.data.totalNum;
                });
            },
            insert() {

            },
            onLaundrySelectionChange(rows) {
                this.selectedLaundryRows = rows;
            },
            queryLaundry() {
                let params = {userId: this.targetUserId};
                this.$http.post("/laundry/getForRelate", qs.stringify(params)).then(res => {
                    this.laundryList = res.data.data;
                    this.laundryPageNum = res.data.pageNum;
                    this.laundryPageSize = res.data.pageSize;
                    this.laundryTotalNum = res.data.totalNum;
                });
            },
            openRelateLaundryDialog(userId) {
                this.targetUserId = userId;
                this.relateLaundryDialogVisible = true;
            },
            getLaundryIdList() {
                let laundryIdList = [];
                for (let index in this.selectedLaundryRows) {
                    laundryIdList.push(this.selectedLaundryRows[index].id);
                }
                return laundryIdList;
            },
            relateLaundry(laundryId, relate) {
                let params = {userId: this.targetUserId, relate: relate, laundryIdList: laundryId};//批量this.getLaundryIdList.join(",")
                // console.log(params);
                this.$http.post("/laundry/relate", qs.stringify(params)).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                        this.queryLaundry();
                    }
                });
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
            }
        }
    };
</script>
