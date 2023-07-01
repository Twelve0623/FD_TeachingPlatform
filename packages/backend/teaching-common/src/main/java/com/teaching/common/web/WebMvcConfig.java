package com.teaching.common.web;

import com.google.common.collect.Lists;
import com.teaching.common.helper.JvmOSHelper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;
import java.util.List;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 16:31
 **/
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.addAll(Lists.newArrayList(FastJsonMessageConverter.INSTANCE,
                                             FormMessageConverter.INSTANCE,
                                             StringMessageConverter.INSTANCE
                                            ));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + JvmOSHelper.projectDir() + "/uploadPath/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public LogAspect getLogAspect(){
        return new LogAspect();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600 * 24);
    }


    protected IWebFilter configAuthFilter() {
        return new IWebFilter() {
        };
    }

    /**
     * HTTP请求安全认证
     */
    @Bean
    public FilterRegistrationBean authenticationFilter() {
        return createFilterRegistrationBean(getAuthenticationFilter(), (Integer.MIN_VALUE));
    }

    private FilterRegistrationBean createFilterRegistrationBean(Filter filter, int order) {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST));
        frBean.setFilter(filter);
        frBean.setOrder(order);
        return frBean;
    }

    private static IWebFilter AUTHENTICATION_FILTER;

    private IWebFilter getAuthenticationFilter() {
        if (null == AUTHENTICATION_FILTER) {
            AUTHENTICATION_FILTER = configAuthFilter();
        }
        return AUTHENTICATION_FILTER;
    }
}
