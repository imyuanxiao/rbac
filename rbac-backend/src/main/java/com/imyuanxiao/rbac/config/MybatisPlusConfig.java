package com.imyuanxiao.rbac.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.imyuanxiao.rbac.security.plugin.MyPaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description MyBatis-Plus pagination plugin configuration.
 * @Author imyuanxiao
 * @Date 2023/5/3 15:17
 * @Version 1.0
 **/
@Configuration
@MapperScan("com.imyuanxiao.rbac.mapper")
public class MybatisPlusConfig {

    /**
     * Pagination plugin
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new MyPaginationInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
