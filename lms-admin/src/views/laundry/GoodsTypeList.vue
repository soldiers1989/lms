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
                    <el-button size="mini"
                               @click="openEditOrInsertDialog(false)">新增
                    </el-button>
                </el-button-group>
            </div>

            <el-table height="650px" :data="goodsTypeList" style="width: 100%;text-align: center" stripe
                      highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="id" label="编号" width="60"></el-table-column>
                <el-table-column prop="name" label="名称" width="100"></el-table-column>
                <el-table-column prop="avgPrice" label="均价" width="120"></el-table-column>
                <el-table-column prop="bannerImgUrl" label="图标url"></el-table-column>
                <el-table-column prop="description" label="描述"></el-table-column>
                <el-table-column label="操作" width="120" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="openEditOrInsertDialog(scope.row)">编辑
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

        <el-dialog title="新增类型" :visible.sync="goodsTypeDialogVisible" @close="closeEditDialog" @open="queryCatalog">
            <el-form v-model="goodsType" :label-position="'right'" label-width="80px">
                <el-form-item label="名称">
                    <el-input v-model="goodsType.name" placeholder="请输入名称"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="goodsType.description" placeholder="描述"></el-input>
                </el-form-item>
                <el-form-item label="图片Url">
                    <el-input v-model="goodsType.bannerImgUrl"></el-input>
                </el-form-item>
                <el-form-item label="参考价格">
                    <el-input v-model="goodsType.avgPrice"></el-input>
                </el-form-item>
                <el-form-item label="一级目录">
                    <el-select v-model="goodsType.catalogId" placeholder="请选择">
                        <el-option
                                v-for="item in catalogList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="goodsTypeDialogVisible = false">取 消</el-button>
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
        name: "GoodsTypeList",
        data() {
            return {
                targetGoodsTypeId: 0,
                pageNum: 1,
                pageSize: 20,
                totalNum: 0,
                goodsType: {},
                goodsTypeDialogVisible: false,
                state: null,
                editMode: false,
                keyWord: "",
                selectedRows: [],
                catalogList: [],
                goodsTypeList: [],
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
            query() {
                let params = {pageNum: this.pageNum, pageSize: this.pageSize, keyWord: this.keyWord};
                this.$http.post("/goodsType/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.goodsTypeList = res.data.data;
                        this.pageNum = res.data.pageNum;
                        this.pageSize = res.data.pageSize;
                        this.totalNum = res.data.totalNum;

                    }
                });
            },
            queryCatalog() {
                let params = {pageSize: -1};
                this.$http.post("/catalog/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.catalogList = res.data.data;
                    }
                });
            },
            checkCode() {
                if (!this.goodsType.goodsTypeCode) {
                    return;
                }
                this.$http.post("/goodsType/checkCode", qs.stringify(this.goodsType)).then(res => {
                    if (!res.data.result || res.data.data > 0) {
                        this.$message.error("该编号已存在!");
                        this.goodsType.goodsTypeCode = "";
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
                this.goodsType = {};
            },
            insertOrUpdate() {
                let operation = this.editMode ? "update" : "insert";
                this.$http.post("/goodsType/" + operation, this.goodsType).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                        this.goodsTypeDialogVisible = false;
                    }
                });
            },
            openEditOrInsertDialog(row) {
                if (row) {
                    this.goodsType = row;
                    this.editMode = true;
                } else {
                    this.goodsType = {};
                    this.editMode = false;
                }
                this.goodsTypeDialogVisible = true;
            },
            onSelectionChange(rows) {
                this.selectedRows = rows;
            },
        }
    };
</script>
