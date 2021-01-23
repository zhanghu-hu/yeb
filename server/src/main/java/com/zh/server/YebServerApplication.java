package com.zh.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 * @author ZH
 * @date 2021/1/19
 */
@SpringBootApplication
@EnableTransactionManagement //开启事务
@MapperScan("com.zh.server.mapper")  //开启映射文件扫描
public class YebServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebServerApplication.class,args);
    }
}
