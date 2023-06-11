package com.imyuanxiao.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 */
@EnableOpenApi
@SpringBootApplication
@MapperScan(basePackages = {"com.imyuanxiao.rbac.mapper"})
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

}
