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
                <el-button-group style="margin-left: 100px;">
                    <!--<el-button size="mini"-->
                               <!--@click="openEditOrInsertDialog(false)">新增-->
                    <!--</el-button>-->
                    <el-button size="mini" @click="generatePriceTable">生成价格表
                    </el-button>
                    <!--<el-button size="mini" :disabled="selectedRows.length==0" @click="activate(false)">停用</el-button>-->
                    <!--<el-button size="mini" :disabled="selectedRows.length==0" @click="activate(true)">启用</el-button>-->
                </el-button-group>
            </div>

            <el-table height="600px" :data="priceTableList" style="width: 100%;text-align: center" stripe
                      highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange">
                <el-table-column type="selection" width="55">
                </el-table-column>
                <el-table-column prop="goodsTypeName" label="衣物名称"></el-table-column>
                <!--<el-table-column prop="goodsId" label="衣物编号"></el-table-column>-->
                <!--<el-table-column prop="laundryId" label="洗衣店编号"></el-table-column>-->
                <el-table-column prop="laundryName" label="洗衣店名称"></el-table-column>
                <el-table-column prop="price" label="价格"></el-table-column>
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

        <el-dialog title="修改价格" :visible.sync="priceTableDialogVisible" @close="closeEditDialog">
            <el-form v-model="priceTable" :label-position="'right'" label-width="80px">
                <el-form-item label="价格">
                    <el-input v-model="priceTable.price" placeholder="请输入价格"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="priceTableDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updatePrice">确 定</el-button>
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
    import MyLaundrySelector from "../coms/MyLaundrySelector"

    export default {
        name: "PriceTableList",
        data() {
            return {
                targetPriceTableId: 0,
                cellDialogVisible: false,
                cellNum: 16,
                pageNum: 1,
                pageSize: 20,
                totalNum: 0,
                priceTable: {},
                priceTableDialogVisible: false,
                state: null,
                editMode: false,
                keyWord: "",
                selectedRows: [],
                priceTableList: [],
                laundryList: [],
                goodsTypeList: [],
                catalogList: [],
                laundryPageNum: 1,
                laundryPageSize: 20,
            };
        },
        mounted() {
            this.query();
        },
        components: {
            MyLaundrySelector
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

                let params = {activate: activate, priceTableIdList: this.getIdList()};
                this.$http.post("/priceTable/activate", qs.stringify(params)).then(res => {
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
                if (this.laundryId) {
                    params.laundryId = this.laundryId;
                }
                this.$http.post("/priceTable/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.priceTableList = res.data.data;
                        this.pageNum = res.data.pageNum;
                        this.pageSize = res.data.pageSize;
                        this.totalNum = res.data.totalNum;
                    }
                });
            },
            queryGoodsType() {
                let params = {pageSize: -1};
                this.$http.post("/goodsType/select", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.goodsTypeList = res.data.data;
                    }
                });
            },
            queryCatalog() {

            },
            checkCode() {
                if (!this.priceTable.priceTableCode) {
                    return;
                }
                this.$http.post("/priceTable/checkCode", qs.stringify(this.priceTable)).then(res => {
                    if (!res.data.result || res.data.data > 0) {
                        this.$message.error("该编号已存在!");
                        this.priceTable.priceTableCode = "";
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
                this.priceTable = {};
            },
            updatePrice() {
                let operation = "update";
                this.$http.post("/priceTable/" + operation, this.priceTable).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                        this.priceTableDialogVisible = false;
                    }
                });
            },
            openCellDialog(priceTableId) {
                this.cellDialogVisible = true;
                this.targetPriceTableId = priceTableId;
            },
            openEditOrInsertDialog(row) {
                if (row) {
                    this.priceTable = row;
                    this.editMode = true;
                } else {
                    this.priceTable = {};
                    this.editMode = false;
                }
                this.priceTableDialogVisible = true;
            },
            createCell() {
                if (this.targetPriceTableId > 0 && this.cellNum > 0) {
                    let params = {cellNum: this.cellNum, priceTableId: this.targetPriceTableId};
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
            laundryChange(laundryId) {
                this.laundryId = laundryId;
                this.query();
            },
            generatePriceTable() {
                this.$confirm('此操作覆盖原来的价格?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.post("/priceTable/generate", qs.stringify({})).then(res => {
                        if (res.data.result && res.data.data) {
                            this.$message({
                                type: "success",
                                message: "操作成功"
                            });
                            this.query();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            },
            onSelectionChange(rows) {
                this.selectedRows = rows;
            },
        }
    };
</script>
