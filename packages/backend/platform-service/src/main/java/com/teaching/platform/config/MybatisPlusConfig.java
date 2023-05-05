package com.teaching.platform.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther huchao
 * @date 2022/10/17 14:20
 */
@Configuration
@MapperScan("com.teaching.platform.dao")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor mybatisPlusInterceptor() {
        return new PaginationInterceptor();
    }

}
