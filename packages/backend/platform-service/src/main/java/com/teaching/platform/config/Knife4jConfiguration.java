package com.teaching.platform.config;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 17:37
 **/

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import com.teaching.common.helper.SpringHelper;
import io.swagger.annotations.Api;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
@EnableConfigurationProperties(SwaggerProperties.class)
public class Knife4jConfiguration {

    @Resource
    private SwaggerProperties swaggerProperties;

    @Bean
    public WebMvcEndpointHandlerMapping handlerMapping(WebEndpointsSupplier webEndpointsSupplier,
                                                       ServletEndpointsSupplier servletEndpointsSupplier,
                                                       ControllerEndpointsSupplier controllerEndpointsSupplier,
                                                       EndpointMediaTypes endpointMediaTypes,
                                                       CorsEndpointProperties corsProperties,
                                                       WebEndpointProperties webEndpointProperties,
                                                       Environment environment) {
        List<ExposableEndpoint<?>> allEndpoints = Lists.newArrayList();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(basePath);
        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(),
                                                new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                                                   .groupName(SpringHelper.applicationName())
                                                   // 将api的元信息设置为包含在json ResourceListing响应中。
                                                   .apiInfo(apiInfo())
                                                   // 选择哪些接口作为swagger的doc发布
                                                   .select()
                                                   //指定某个路径才能生成swagger
                                                   .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                                                   .build()
                                                   //是否启用
                                                   .enable(swaggerProperties.isEnable())
                                                   // 支持的通讯协议集合
                                                   .protocols(new HashSet<>(Arrays.asList("https", "http")));
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(swaggerProperties.getTitle())
                                   .description(swaggerProperties.getDescription())
                                   .contact(new Contact(swaggerProperties.getAuthor(), swaggerProperties.getUrl(), swaggerProperties.getEmail()))
                                   .version(swaggerProperties.getVersion())
                                   .build();
    }
}
