package com.teaching.platform.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 17:27
 **/
@ConfigurationProperties(prefix = "swagger.doc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwaggerProperties {
    private String version;

    private boolean enable;

    /**
     * 作者
     */
    private String author;

    /**
     * 标题
     */
    private String title;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 官网地址
     */
    private String url;

    /**
     * 邮箱地址
     */
    private String email;
}
