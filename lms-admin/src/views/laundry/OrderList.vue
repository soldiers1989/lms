<template>
    <div>
        <el-card shadow="never" :body-style="{ padding: '0px' }">
            <div style="margin:10px;">
                <el-date-picker size="mini" type="daterange" v-model="dateRange" align="right" unlink-panels
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                :picker-options="$store.state.dateRangePickerOptions">
                </el-date-picker>
                <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-search" @click="query">
                    查询
                </el-button>
                <el-button style="margin-left: 10px;" type="primary" size="mini" icon="el-icon-search" @click="query">
                    刷新
                </el-button>
                <refresh-bar @refresh="refreshData" style="float: right;"></refresh-bar>
                <my-laundry-selector style="margin-left: 10px; float: right;"
                                     @change="laundryChange"></my-laundry-selector>
            </div>
            <el-menu default-active="10" mode="horizontal" @select="handleSelect">
                <el-menu-item index="10">待接单({{getCntByState(10)}})</el-menu-item>
                <el-menu-item index="42">待存放({{getCntByState(42)}})</el-menu-item>
                <el-menu-item index="45">待提货({{getCntByState(45)}})</el-menu-item>
                <el-menu-item index="50">已提货({{getCntByState(50)}})</el-menu-item>
                <el-menu-item index="55">待付款({{getCntByState(55)}})</el-menu-item>
                <el-menu-item index="60">待清洁({{getCntByState(60)}})</el-menu-item>
                <el-menu-item index="63">清洁中({{getCntByState(63)}})</el-menu-item>
                <el-menu-item index="65">待送出({{getCntByState(65)}})</el-menu-item>
                <el-menu-item index="67">已送出({{getCntByState(67)}})</el-menu-item>
                <el-menu-item index="100">已完成</el-menu-item>
                <el-menu-item index="0">已失效</el-menu-item>
            </el-menu>
            <el-button-group style="padding: 10px;">
                <!--@click="acceptOrder"-->
                <el-button size="mini" type="primary" icon="el-icon-plus" :disabled="selectedRows.length==0"
                           @click="invokeMethod('acceptBatch')"
                           v-if="activeIndex==10">接单
                </el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0" v-if="activeIndex==60"
                           @click="invokeMethod('startCleaning')">进入清洁
                </el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0" v-if="activeIndex==63"
                           @click="invokeMethod('cleaned')">清洁完成
                </el-button>
                <el-button size="mini" type="primary" :disabled="selectedRows.length==0" v-if="activeIndex==65"
                           @click="invokeMethod('send')">发出
                </el-button>
            </el-button-group>
            <el-table height="550" :data="orderList" style="width: 100%" stripe highlight-current-row
                      v-loading="$store.state.loading" @selection-change="onSelectionChange"
                      @cell-dblclick="showOrderDetailDialog">
                <el-table-column type="selection" width="55" align="center">
                </el-table-column>
                <el-table-column prop="code" label="订单编号"></el-table-column>
                <el-table-column prop="count" width="120" label="数量"></el-table-column>
                <el-table-column prop="wardrobeId" width="120" label="柜子id"></el-table-column>
                <el-table-column prop="commitTime" label="提交时间"></el-table-column>
                <el-table-column prop="expiredTime" label="超时时间"></el-table-column>
                <!--<el-table-column prop="commitTime" label="金额"></el-table-column>-->
                <el-table-column label="操作" width="120" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="openCommitPriceDialog(scope.row)"
                                   v-if="activeIndex==50">提交价格
                        </el-button>
                        <el-button type="text" size="small" @click="autoRelateCode(scope.row.id)"
                                   v-if="activeIndex==45">
                            自动编号
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

        <el-dialog :title="'订单:'+orderDetail.code" width="55%" label-position="top"
                   :visible.sync="orderDetailDialogVisible"
                   @close="targetOrderShipment={};targetOrder={}">
            <el-form :model="orderDetail" :label-position="'right'" label-width="100px" size="mini">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="状态:">
                            <el-input :readonly='true' v-model="orderDetail.stateName"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="地址:">
                            <el-input :readonly='true' v-model="orderDetail.address"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="电话:">
                            <el-input :readonly='true' v-model="orderDetail.phone"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="开柜密码:">
                            <el-input :readonly='true' v-model="orderDetail.password"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="柜子id:">
                            <el-input :readonly='true' v-model="orderDetail.wardrobeId"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="格子id:">
                            <el-input :readonly='true' v-model="orderDetail.cellId"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="过期时间:">
                            <el-input :readonly='true' v-model="orderDetail.expiredTime"></el-input>
                        </el-form-item>

                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="账单生成时间:">
                            <el-input :readonly='true' v-model="orderDetail.generateTime"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="预计总价:">
                            <el-input :readonly='true' v-model="orderDetail.estTotalCost"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="实际总价:">
                            <el-input :readonly='true' v-model="orderDetail.actTotalCost"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="折扣:">
                            <el-input :readonly='true' v-model="orderDetail.diacount"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="其他费用:">
                            <el-input :readonly='true' v-model="orderDetail.extCost"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="已付款:">
                            {{orderDetail.confirmed?'是':'否'}}
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="加急:">
                            {{orderDetail.asap?'是':'否'}}
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20" v-if="orderDetail.confirmed">
                    <el-col :span="12">
                        <el-form-item label="实付:">
                            <el-input :readonly='true' v-model="orderDetail.actPaidCost"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="付款时间:">
                            <el-input :readonly='true' v-model="orderDetail.confirmTime"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="订单备注:">
                            <el-input type="textarea" :readonly='true' v-model="orderDetail.description"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="价格备注:">
                            <el-input type="textarea" :readonly='true' v-model="orderDetail.description"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <el-table height="150" :data="orderGoodsList" style="width: 100%" stripe highlight-current-row
                      v-loading="$store.state.loading">
                <el-table-column label="图片" width="70">
                    <template slot-scope="scope">
                        <img :src="scope.row.imgUrl" style="width: 50px;height: 50px;">
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="名称" align="center"></el-table-column>
                <el-table-column prop="count" width="90" label="数量" align="center"></el-table-column>
                <el-table-column prop="launderType" width="100" label="洗涤方式">
                    <template slot-scope="scope">
                        <el-tag v-if="!scope.row.launderType">默认</el-tag>
                    </template>
                </el-table-column>
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button @click="orderDetailDialogVisible = false">关闭</el-button>
            </div>
        </el-dialog>


        <el-dialog :visible.sync="commitPriceDialogVisible" @close="targetOrder=null">
            <el-form :model="targetOrderCost" :label-position="'right'" label-width="80px">
                <el-form-item label="总价格:">
                    <el-input v-model="targetOrderCost.actTotalCost"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input type="textarea" v-model="targetOrderCost.description"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="commitPriceDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="commitPrice">确 定</el-button>
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
    import qs from 'qs'
    import MyLaundrySelector from "../coms/MyLaundrySelector"
    import RefreshBar from "../coms/RefreshBar"

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
                orderDetailDialogVisible: false,
                selectedRows: [],
                bucketName: "public",
                pageNum: 1,
                targetOrderCost: {
                    actTotalCost: 0,
                    description: ""
                },
                laundryId: 1,
                orderGoodsList: [],
                orderDetail: {},
                orderStateEnums: [],
                targetOrder: null,
                pageSize: 20,
                totalNum: 0,
                statisticInfo: {},
                targetOrderShipment: {},
                commitPriceDialogVisible: false,
                orderList: [],
            };
        },
        mounted() {
            this.query();
            this.getOrderState();
        },
        components: {
            MyLaundrySelector,
            RefreshBar
        },
        methods: {
            handleSelect(index) {
                this.activeIndex = index;
                this.query();
            },
            getCntByState(state) {
                for (let index in this.statisticInfo) {
                    if (this.statisticInfo[index].state == state) {
                        return this.statisticInfo[index].cnt;
                    }
                }
                return 0;
            },
            commitPrice() {
                if (!this.targetOrder || !this.targetOrderCost) {
                    return;
                }
                this.$confirm('确定提交价格吗?[' + this.targetOrderCost.actTotalCost + ']', '订单号:' + this.targetOrder.code, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let params = {
                        orderId: this.targetOrder.id,
                        orderCode: this.targetOrder.code,
                        totalPrice: this.targetOrderCost.actTotalCost
                    };
                    this.$http.post("/orderCost/commitPrice", qs.stringify(params)).then(res => {
                        if (res.data.result && res.data.data) {
                            this.$message({
                                type: "success",
                                message: "操作成功"
                            });
                            this.commitPriceDialogVisible = false;
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
            refreshData() {
                this.query();
            },
            laundryChange(laundryId) {
                this.laundryId = laundryId;
                this.query();
            },
            query() {
                let params = {state: this.activeIndex, pageNum: this.pageNum, pageSize: this.pageSize};
                if (this.laundryId) {
                    params.laundryId = this.laundryId;
                }
                this.$http.post("/order/selectByLaundryId", qs.stringify(params)).then(res => {
                    if (res.data.result) {
                        this.orderList = res.data.data;
                        this.pageNum = res.data.pageNum;
                        this.pageSize = res.data.pageSize;
                        this.totalNum = res.data.totalNum;
                    }
                    this.getStatisticInfo();
                });
            },
            showOrderDetailDialog(row, column, cell, event) {
                this.getOrderFullDetail(row.id);
                this.orderDetailDialogVisible = true;
            },
            insert() {

            },
            openEditOrInsertDialog() {
                this.dialogVisible = true;
            },
            openCommitPriceDialog(row) {
                if (row) {
                    this.commitPriceDialogVisible = true;
                    this.targetOrder = row;
                    this.getOrderCost();
                }
            },
            invokeMethod(method) {
                let idList = this.getSelectedOrderIdList();
                if (!idList || idList.length == 0) {
                    return;
                }
                this.$confirm('确定执行吗?', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.post("/order/" + method, qs.stringify({orderIdList: idList.join(",")})).then(res => {
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
            autoRelateCode(orderId) {
                this.$http.post("/goodsCode/autoRelate", qs.stringify({orderId: orderId})).then(res => {
                    if (res.data.result && res.data.data) {
                        this.$message({
                            type: "success",
                            message: "操作成功"
                        });
                    }
                });
            },
            getOrderGoods(orderId) {
                this.$http.post("/orderGoods/getByOrderId", qs.stringify({orderId: orderId})).then(res => {
                    if (res.data.result && res.data.data) {
                        this.orderGoodsList = res.data.data;
                    }
                });
            },
            getStatisticInfo() {
                let params = {};
                if (this.laundryId) {
                    params = {laundryId: this.laundryId};
                }
                this.$http.post("/order/getStatisticInfo", qs.stringify(params)).then(res => {
                    if (res.data.result && res.data.data) {
                        this.statisticInfo = res.data.data;
                    }
                });
            },
            getOrderCost() {
                if (this.targetOrder) {
                    let params = {orderId: this.targetOrder.id};
                    this.$http.post("/orderCost/getByOrderId", qs.stringify(params)).then(res => {
                        if (res.data.result && res.data.data) {
                            this.targetOrderCost = res.data.data;
                        }
                    });
                }
            },
            //String orderCode, Integer orderId, BigDecimal totalPrice
            getOrderFullDetail(orderId) {
                if (orderId) {
                    // let orderIdList = [];
                    // orderIdList.push(orderId);
                    // let params = {orderIdList: orderIdList};
                    let params = {orderId: orderId};
                    this.$http.post("/order/getFullDetail", qs.stringify(params)).then(res => {
                        if (res.data.result && res.data.data) {
                            res.data.data[0].stateName = this.getStateName(res.data.data[0].state);
                            this.orderDetail = res.data.data[0];
                            this.getOrderGoods(orderId);
                        }
                    });
                }
            },
            acceptOrder() {
                let params = {orderId: this.selectedRows[0].id};
                this.$http.post("/order/accept", qs.stringify(params)).then(res => {

                });
            },
            getSelectedOrderIdList() {
                let orderIdList = [];
                this.selectedRows.forEach(item => {
                    orderIdList.push(item.id);
                });
                return orderIdList;
            },
            getOrderState() {
                this.$http.post("/enums/order/state", {}).then(res => {
                    if (res.data.result && res.data.data) {
                        this.orderStateEnums = JSON.parse(res.data.data);
                    }
                });
            },
            getStateName(state) {
                for (let index in this.orderStateEnums) {
                    if (this.orderStateEnums[index].state == state) {
                        return this.orderStateEnums[index].name;
                    }
                }
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
            }
        }
    };
</script>
