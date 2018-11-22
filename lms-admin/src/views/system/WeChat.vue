<template>
    <el-card shadow="never" :body-style="{ padding: '20px 20px 20px 20px' }">
        <el-tabs @tab-click="clickMenuTab">
            <el-tab-pane label="基本配置">
                <el-form ref="weChatConfig" v-model="weChatConfig" label-width="140px">
                    <el-form-item size="mini" prop="appId" label="AppId">
                        <el-input v-model="weChatConfig.appId"></el-input>
                    </el-form-item>
                    <el-form-item size="mini" prop="appSecret" label="AppSecret">
                        <el-input v-model="weChatConfig.appSecret"></el-input>
                    </el-form-item>
                    <el-form-item size="mini" prop="aesKey" label="EncodingAESKey">
                        <el-input v-model="weChatConfig.aesKey"></el-input>
                    </el-form-item>
                    <el-form-item size="mini" prop="token" label="token">
                        <el-input v-model="weChatConfig.token"></el-input>
                    </el-form-item>
                    <el-form-item size="mini" prop="oriId" label="原始id">
                        <el-input v-model="weChatConfig.oriId"></el-input>
                    </el-form-item>
                    <el-form-item size="mini" prop="callbackDomain" label="授权回调域名">
                        <el-input v-model="weChatConfig.callbackDomain"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button size="mini" type="primary" @click="updateConfig">更新</el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="菜单管理">
                <el-input type="textarea" v-model="menu" rows="6"></el-input>
                <el-button size="mini" type="primary" @click="updateMenu">更新</el-button>
                <el-button size="mini" type="danger" @click="deleteAllMenu">删除</el-button>
            </el-tab-pane>

        </el-tabs>
    </el-card>
</template>
<script>
    export default {
        name: "WeChat",
        data() {
            return {
                weChatConfig: {},
                menu: "",
            };
        },
        mounted() {
            this.getConfig();
        },
        methods: {
            getConfig() {
                this.$http.post("/WeChat/config/select").then(res => {
                    if (res.data.result) {
                        this.weChatConfig = res.data.data;
                    }
                });
            },
            updateConfig() {
                this.$http.post("/WeChat/config/update", this.weChatConfig).then(res => {
                    if (res.data.result && res.data.data > 0) {

                    }
                });
            },
            getMenu() {
                this.$http.post("/WeChat/menu/select").then(res => {
                    if (res.data.result) {
                        this.menu = JSON.stringify(res.data.data);
                    }
                });
            },
            clickMenuTab(tab) {
                if (tab.index == 1) {
                    this.getMenu();
                }
            },
            updateMenu() {
                this.$http.post("/WeChat/menu/update", {menu: JSON.stringify(this.menu)}
                ).then(res => {
                    if (res.data.result) {
                        this.menu = res.data.data
                    }
                });
            },
            deleteAllMenu() {
                this.$http.post("/WeChat/menu/delete").then(res => {
                    if (res.data.result) {
                       console.log(res.data);
                    }
                });
            }
        }
    };
</script>
