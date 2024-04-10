package com.teaching.platform.config;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 17:37
 **/

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import jakarta.annotation.Resource;
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

import java.util.Collection;
import java.util.List;

@Configuration
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
                                                new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping);
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }

    @Bean
    public OpenAPI createRestApi() {
        return new OpenAPI()
                .info(new Info()
                              .title(swaggerProperties.getTitle())
                              .description(swaggerProperties.getDescription())
                              .contact(new Contact()
                                               .email(swaggerProperties.getEmail())
                                               .url(swaggerProperties.getUrl())
                                               .name(swaggerProperties.getAuthor()))
                              .version(swaggerProperties.getVersion())
                              .license(new License().name("Apache 2.0")
                                                    .url(swaggerProperties.getUrl())));
    }
}
