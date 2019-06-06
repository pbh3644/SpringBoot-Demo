package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * 1.约定启动剔除SecurityAutoConfiguration配置
 * 2.扫描 mybatis mapper 包路径
 * 3.开启定时任务
 * 4.开启异步调用方法
 * 5.支持Spring缓存
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan(basePackages = "com.mapper")
@EnableScheduling
@EnableAsync
@EnableCaching
/**
 * @author pangbohuan
 * @description
 */
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
