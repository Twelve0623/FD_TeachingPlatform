package com.teaching.platform.config;

import com.teaching.common.web.IWebFilter;
import com.teaching.common.web.WebMvcConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 16:54
 **/
public class ApplicationConfig {

    @Configuration
    public static class WebConfig extends WebMvcConfig{
        @Bean
        protected IWebFilter configAuthFilter() {
            return new WebAuthFilter();
        }
    }


}
