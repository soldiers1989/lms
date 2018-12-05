package com.yniot.lms;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.sql.SQLException;
import java.util.*;

/**
 * @Author wanggl(lane)
 * @Description //TODO
 * @Date 下午4:33 2018/12/4
 **/
public class MyBatisPlusGenerator {
    public static void main(String[] args) throws SQLException {
        Map<String, String> table = new HashMap<>();

        //直接在这里添加表名和前缀即可,key为表名,value为前缀
//        table.put("biz_order", "biz_");
        table.put("biz_order_shipment", "biz_");
//        table.put("biz_order_goods", "biz_");
//        table.put("biz_order", "biz_");
//        table.put("biz_income_history", "biz_");

        MyBatisPlusGenerator myBatisPlusGenerator = new MyBatisPlusGenerator();
        myBatisPlusGenerator.generateCode(table);
    }


    private void generateCode(Map<String, String> table) {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("wanggl") // 作者
                .setOutputDir("/Users/lane/IdeaProjects/lms/src/main/java") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setServiceName("%sService")  // 设置生成的service接口的名字
                .setBaseResultMap(true)//生成基本的resultMap
                .setBaseColumnList(true);//生成基本的SQL片段

        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://120.79.91.131:3306/lms?useUnicode=true&useSSL=false&characterEncoding=utf8")
                .setUsername("lms_admin")
                .setPassword("lms_admin");


        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.yniot.lms")
                .setMapper("db.dao_")//dao
                .setService("service_")//servcie
                .setServiceImpl("service_.impl")//
                .setController("controller_")//controller
                .setXml("db.xml")
                .setEntity("db.entity");//mapper.xml


        Iterator<String> keySet = table.keySet().iterator();


        while (keySet.hasNext()) {
            AutoGenerator ag = new AutoGenerator();
            ag.setGlobalConfig(config)
                    .setDataSource(dsConfig)
                    .setPackageInfo(pkConfig);
            //3. 策略配置globalConfiguration中
            String tableName = keySet.next();
            String prefix = table.get(tableName);
            StrategyConfig stConfig = new StrategyConfig();
            stConfig.setCapitalMode(true) //全局大写命名
                    .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                    .setTablePrefix(prefix)
                    .setInclude(tableName)// 目标表
                    .setRestControllerStyle(true)
                    .setSuperControllerClass("com.yniot.lms.controller.commonController.BaseController")
                    .setSuperMapperClass("com.yniot.exclude.CommonMapper");//默认为BaseMapper
            //6. 执行
            ag.setStrategy(stConfig);
            ag.execute();
        }


    }

}
