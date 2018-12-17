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

                <!--<el-button-group style="margin-left: 100px;">-->
                <!--<el-button size="mini"-->
                <!--@click="openEditOrInsertDialog(false)">新增-->
                <!--</el-button>-->
                <!--<el-button size="mini" :disabled="selectedRows.length==0" @click="activate(false)">停用</el-button>-->
                <!--<el-button size="mini" :disabled="selectedRows.length==0" @click="activate(true)">启用</el-button>-->
                <!--</el-button-group>-->
            </div>

            <el-table height="600px" :data="laundryList" style="width: 100%;text-align: center" stripe
                      highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="id" label="编号" width="60"></el-table-column>
                <el-table-column prop="name" label="名称" width="100"></el-table-column>
                <el-table-column prop="phone" label="电话" width="130"></el-table-column>
                <el-table-column prop="wardrobeNum" label="柜子数" width="70"></el-table-column>
                <el-table-column prop="dividePercent" label="提成(%)" width="140"></el-table-column>
                <el-table-column label="收费方式" width="120">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.divideType==0">租金</el-tag>
                        <el-tag v-if="scope.row.divideType==1">提成</el-tag>
                        <el-tag v-if="scope.row.divideType==2">提成+租金</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="address" label="地址"></el-table-column>
                <!--<el-table-column label="状态" width="80">-->
                <!--<template slot-scope="scope">-->
                <!--<el-tag v-if="scope.row.inBiz" :type="'success'">合作中</el-tag>-->
                <!--<el-tag v-else :type="'danger'">已停止</el-tag>-->
                <!--</template>-->
                <!--</el-table-column>-->
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="openEditOrInsertDialog(scope.row)">编辑</el-button>
                        <el-button type="text" size="small" @click="openRelateWardrobeDialog(scope.row)">关联柜子</el-button>
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

        <el-dialog :visible.sync="wardrobeDialogVisible" @open="queryWardrobe">
            <el-input style="width: 200px" size="mini" v-model="wardrobeKeyWord" placeholder="关键字"></el-input>
            <el-button type="primary" size="mini" style="margin-left: 20px;" icon="el-icon-search"
                       @click="queryWardrobe">
                查询
            </el-button>
            <el-table height="500px" :data="wardrobeList" style="width: 100%;text-align: center" stripe
                      highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="wardrobeCode" label="编号"></el-table-column>
                <el-table-column prop="address" label="地址"></el-table-column>
                <el-table-column prop="longitude" label="经度" width="120"></el-table-column>
                <el-table-column prop="latitude" label="纬度" width="120"></el-table-column>
                <el-table-column label="状态" width="80">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.activated==1" :type="'success'">已启用</el-tag>
                        <el-tag v-else :type="'danger'">已停用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="洗衣店编号" width="100">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.laundryId" :type="'success'">{{scope.row.laundryId}}</el-tag>
                        <el-tag v-else :type="'danger'">未关联</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="120" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="relateLaundry(scope.row,true)"
                                   v-if="!scope.row.laundryId">关联
                        </el-button>
                        <el-button type="text" size="small" @click="relateLaundry(scope.row,false)" v-else>取消关联
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination align="center"
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page="wardrobePageNum"
                           :page-sizes="[10, 20]"
                           :page-size="wardrobePageSize"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="wardrobeTotalNum">
            </el-pagination>
        </el-dialog>


        <el-dialog :title="editMode?'编辑洗衣店':'新增洗衣店'" :visible.sync="laundryDialogVisible" @close="closeEditDialog">
            <el-form v-model="laundry" :label-position="'right'" label-width="100px">
                <!--<el-form-item label="管理人员账户">-->
                <!--<el-input v-model="laundry.phone" placeholder="请输入编号"></el-input>-->
                <!--</el-form-item>-->
                <el-form-item label="电话">
                    <el-input v-model="laundry.phone" placeholder="请输入编号"></el-input>
                </el-form-item>
                <el-form-item label="提成比例(%)">
                    <el-input v-model="laundry.dividePercent" placeholder="请输入比例" max="100" min="10" :disabled="laundry.divideType==0"></el-input>
                </el-form-item>
                <el-form-item label="收费方式">
                    <el-select v-model="laundry.divideType" placeholder="请选择软件版本">
                        <el-option label="租金" :value="0"></el-option>
                        <el-option label="提成" :value="1"></el-option>
                        <el-option label="租金+提成" :value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="laundry.address" placeholder="请输入地址"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="laundryDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="insertOrUpdate">确 定</el-button>
            </div>
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
        name: "LaundryList",
        data() {
            return {
                wardrobeKeyWord: "",
                targetLaundryId: 0,
                wardrobeDialogVisible: false,
                wardrobeList: [],
                cellNum: 16,
                pageNum: 1,
                pageSize: 20,
                totalNum: 0,
                laundry: {},
                laundryDialogVisible: false,
                state: null,
                editMode: false,
                keyWord: "",
                targetRelateLaundryId: 0,
                selectedRows: [],
                laundryList: [],
                wardrobePageNum: 1,
                wardrobePageSize: 20,
                wardrobeTotalNum: 0
            };
        },
        mounted() {
            this.query();
        },
        methods: {
            activate(activate) {
                if (!activate) {
                    for (let index in this.selectedRows) {
                        if (this.selectedRows[index].avaCellNum < this.selectedRows[index].totalCellNum) {
                            this.$message.error("有部分柜子正在使用中,不能停用");
                            return;
                        }
                    }
                }
                let params = {activate: activate, laundryIdList: this.getIdList()};
                this.$http.post("/laundry/activate", qs.stringify(params)).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                        this.query();
                    }
                });
            },
            getIdList() {
                let idList = [];
                for (let index in this.selectedRows) {
                    idList.push(this.selectedRows[index].id);
                }
                return idList;
            },
            queryWardrobe() {
                let params = {
                    pageNum: this.wardrobePageNum,
                    pageSize: this.wardrobePageSize,
                    keyWord: this.wardrobeKeyWord
                };
                this.$http.post("/wardrobe/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.wardrobeList = res.data.data;
                    }
                });
            },
            relateLaundry: function (row, relate) {
                let params = {relate: relate, laundryId: this.targetRelateLaundryId, wardrobeIdList: [row.id]};
                this.$http.post("/wardrobe/relateLaundry", qs.stringify(params)).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                        this.wardrobeDialogVisible = false;
                    }
                });
            },
            query() {
                let params = {pageNum: this.pageNum, pageSize: this.pageSize, keyWord: this.keyWord};
                this.$http.post("/laundry/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.laundryList = res.data.data;
                    }
                });
            },
            checkCode() {
                if (!this.laundry.laundryCode) {
                    return;
                }
                this.$http.post("/laundry/checkCode", qs.stringify(this.laundry)).then(res => {
                    if (!res.data.result || res.data.data > 0) {
                        this.$message.error("该编号已存在!");
                        this.laundry.laundryCode = "";
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
                this.laundry = {};
            },
            insertOrUpdate() {
                let operation = this.editMode ? "update" : "insert";
                this.$http.post("/laundry/" + operation, this.laundry).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                        this.laundryDialogVisible = false;
                    }
                });
            },
            openCellDialog(laundryId) {
                this.cellDialogVisible = true;
                this.targetLaundryId = laundryId;
            },
            openEditOrInsertDialog(row) {
                if (row) {
                    this.laundry = row;
                    this.editMode = true;
                } else {
                    this.laundry = {};
                    this.editMode = false;
                }
                this.laundryDialogVisible = true;
            },
            openRelateWardrobeDialog(row) {
                this.targetRelateLaundryId = row.id;
                this.wardrobeDialogVisible = true;
            },
            createCell() {
                if (this.targetLaundryId > 0 && this.cellNum > 0) {
                    let params = {cellNum: this.cellNum, laundryId: this.targetLaundryId};
                    this.$http.post("/cell/createCell", qs.stringify(params)).then(res => {
                        if (res.data.result) {
                            this.$message({
                                type: "success",
                                message: "操作成功"
                            });
                            this.cellDialogVisible = false;
                            this.query();
                        }
                    });
                } else {
                    this.$message.error("请填写正确的格子数");
                }

            },
            onSelectionChange(rows) {
                this.selectedRows = rows;
            },
        }
    };
</script>
