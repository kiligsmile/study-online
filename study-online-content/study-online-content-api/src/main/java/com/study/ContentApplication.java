package com.study;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:  内容管理服务启动类
 * @Author: kiligsmile
 * @Date: 2023/10/28 10:49 PM
 * @Param:
 * @Return:
*/
@EnableSwagger2Doc
@SpringBootApplication(scanBasePackages ="com.study" )
public class ContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApplication.class,args);
    }
}
