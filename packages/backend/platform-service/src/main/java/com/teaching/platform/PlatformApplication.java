package com.teaching.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sacher
 */
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.teaching.platform"})
public class PlatformApplication{
    @Bean
    public ServletWebServerFactory servletContainer() {
        return new JettyServletWebServerFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}
