<template>
    <div>
        <el-card shadow="never" :body-style="{ padding: '0px' }">
            <div slot="header">

                <el-input style="width: 200px" size="mini" v-model="keyWord" placeholder="关键字"></el-input>
                <el-button type="primary" size="mini" style="margin-left: 20px;" icon="el-icon-search" @click="query">
                    查询
                </el-button>


                <el-button-group style="margin-left: 100px;">
                    <el-button size="mini"
                               @click="openEditOrInsertDialog(false)">新增
                    </el-button>
                    <!--<el-button size="mini" :disabled="selectedRows.length==0" @click="openLaundryListDialog()">关联洗衣店-->
                    <!--</el-button>-->
                    <!--<el-button size="mini" :disabled="selectedRows.length==0" @click="relateLaundry(false)">取消关联-->
                    <!--</el-button>-->
                    <el-button size="mini" :disabled="selectedRows.length==0" @click="activate(false)">停用</el-button>
                    <el-button size="mini" :disabled="selectedRows.length==0" @click="activate(true)">启用</el-button>
                </el-button-group>
            </div>


            <el-table :data="wardrobeList" style="width: 100%" stripe highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55" align="center">
                </el-table-column>
                <el-table-column prop="laundryName" label="洗衣店"></el-table-column>
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
                <el-table-column prop="avaCellNum" label="可用格子" width="100"></el-table-column>
                <el-table-column prop="totalCellNum" label="格子总数" width="100"></el-table-column>
                <el-table-column prop="usedCellNum" label="已使用的格子" width="100"></el-table-column>
                <!--<el-table-column label="创建日期" width="140">-->
                <!--<template slot-scope="scope">-->
                <!--{{ scope.row.createTime }}-->
                <!--</template>-->
                <!--</el-table-column>-->
                <!--<el-table-column label="最后修改日期" width="140">-->
                <!--<template slot-scope="scope">-->
                <!--{{ scope.row.modifyTime }}-->
                <!--</template>-->
                <!--</el-table-column>-->
                <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="openEditOrInsertDialog(scope.row)">编辑
                        </el-button>
                        <el-button type="text" size="small" @click="openCellDialog(scope.row.id)">添加格子
                        </el-button>
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

        <el-dialog title="新增柜子" :visible.sync="wardrobeDialogVisible" @close="closeEditDialog">
            <el-form v-model="wardrobe" :label-position="'right'" label-width="80px">
                <el-form-item label="地址">
                    <el-input v-model="wardrobe.address" placeholder="请输入地址"></el-input>
                </el-form-item>
                <el-form-item label="经度">
                    <el-input v-model="wardrobe.longitude" placeholder="请输入经度"></el-input>
                </el-form-item>
                <el-form-item label="纬度">
                    <el-input v-model="wardrobe.latitude" placeholder="请输入纬度"></el-input>
                </el-form-item>
                <el-form-item label="版本">
                    <el-select v-model="wardrobe.swVersion" placeholder="请选择软件版本">
                        <el-option label="1.0" value="1"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="wardrobeDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="insertOrUpdate">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="添加格子" :visible.sync="cellDialogVisible">
            <el-input v-model="cellNum" placeholder="请输入格子数"></el-input>
            <div slot="footer" class="dialog-footer">
                <el-button @click="wardrobeDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="createCell">确 定</el-button>
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
        name: "WardrobeList",
        data() {
            return {
                targetWardrobeId: 0,
                cellDialogVisible: false,
                cellNum: 16,
                pageNum: 1,
                pageSize: 20,
                totalNum: 0,
                wardrobe: {},
                wardrobeDialogVisible: false,
                state: null,
                editMode: false,
                keyWord: "",
                selectedRows: [],
                wardrobeList: [],
                laundryList: [],
                laundryPageNum: 1,
                laundryPageSize: 20,
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

                let params = {activate: activate, wardrobeIdList: this.getIdList()};
                this.$http.post("/wardrobe/activate", qs.stringify(params)).then(res => {
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
            query() {
                let params = {pageNum: this.pageNum, pageSize: this.pageSize, keyWord: this.keyWord};
                this.$http.post("/wardrobe/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.wardrobeList = res.data.data;
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
                this.wardrobe = {};
            },
            insertOrUpdate() {
                let operation = this.editMode ? "update" : "insert";
                this.$http.post("/wardrobe/" + operation, qs.stringify(this.wardrobe)).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                        this.wardrobeDialogVisible = false;
                    }
                });
            },
            openCellDialog(wardrobeId) {
                this.cellDialogVisible = true;
                this.targetWardrobeId = wardrobeId;
            },
            openEditOrInsertDialog(row) {
                if (row) {
                    this.wardrobe = row;
                    this.editMode = true;
                } else {
                    this.wardrobe = {};
                    this.editMode = false;
                }
                this.wardrobeDialogVisible = true;
            },
            createCell() {
                if (this.targetWardrobeId > 0 && this.cellNum > 0) {
                    let params = {cellNum: this.cellNum, wardrobeId: this.targetWardrobeId};
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
