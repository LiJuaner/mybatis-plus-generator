package com.hxy.lab.mybatisplusgenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: MyBatisPlusGenerator
 * @Desription: (描述此类的功能)
 * @author: lijuan.li
 * @date: 2024/4/7 5:47 PM
 */
@Service
public class MyBatisPlusGenerator {


    /**
     * FastAutoGenerator： 自动生成MyBatis文件
     */
    public void fastAutoGenerate() {

        // TODO: 2024/4/8 数据库配置要改
        String url = "jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true";
        String userName = "root";
        String password = "root";

        // TODO: 2024/4/8 工程路径要改，对应包的位置要改
        String baseUrl = "/Users/lilijuan/Document-LLJ/3_Project/SpringLab/axe";
        Map<OutputFile, String> pathInfoMap = new HashMap<>();
        pathInfoMap.put(OutputFile.controller,  baseUrl + "/axe-web/src/main/java/com/hxy/lab/axe/controller");
        pathInfoMap.put(OutputFile.service,  baseUrl + "/axe-service/src/main/java/com/hxy/lab/axe/service");
        pathInfoMap.put(OutputFile.serviceImpl,  baseUrl + "/axe-service/src/main/java/com/hxy/lab/axe/service/impl");
        pathInfoMap.put(OutputFile.entity,  baseUrl + "/axe-model/src/main/java/com/hxy/lab/axe/model/entity");
        pathInfoMap.put(OutputFile.mapper,  baseUrl + "/axe-service/src/main/java/com/hxy/lab/axe/dao");
        pathInfoMap.put(OutputFile.xml, baseUrl + "/axe-service/src/main/resources/mapper");

        FastAutoGenerator.create(url, userName, password)
                .globalConfig(builder -> {
                    builder.author("LiJuan.Li") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(baseUrl); // 指定输出目录
                    builder.enableSpringdoc(); // 开启SpringDoc注解（默认是Swagger）
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                }))

                // TODO: 2024/4/8 包配置要改
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.hxy.lab") // 设置父包名
                            .moduleName("axe") // 设置父包模块名
                            .entity("model.entity") // entity的包名
                            .mapper("dao") // mapper的包名：{parent}+{moduleName}+{mapper}
                            .pathInfo(pathInfoMap); // 设置生成路径
                })
                // 策略配置
                .strategyConfig(builder -> {
                    // TODO: 2024/4/8 表名要改
                    builder.addInclude("people"); // 设置需要生成的表名
                            // .addTablePrefix("t_", "c_"); // 设置过滤表前缀

                    builder.entityBuilder()
                            .enableFileOverride()
                            // .enableLombok(); // 开启Lombok
                            .enableTableFieldAnnotation();
                    builder.serviceBuilder()
                            .enableFileOverride();
                    //
                    builder.controllerBuilder()
                            .enableFileOverride()
                            .enableRestStyle();

                    builder.mapperBuilder()
                            .enableFileOverride()
                            .enableBaseResultMap()
                            .enableBaseColumnList();
                    //         .formatMapperFileName("%Dao")
                    //         .formatXmlFileName("%Mapper");

                })
                .templateConfig(builder -> {
                    builder
                            // .controller("") // 不自动生成controller
                            .service("") // 不自动生成service
                            .serviceImpl(""); // 不自动生成serviceImpl
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    public static void main(String[] args) {
        MyBatisPlusGenerator generator = new MyBatisPlusGenerator();
        generator.fastAutoGenerate();
    }

}
