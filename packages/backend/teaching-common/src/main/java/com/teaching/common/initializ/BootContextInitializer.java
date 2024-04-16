package com.teaching.common.initializ;

import com.teaching.common.helper.SpringHelper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Locale;

public class BootContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        // Set ApplicationContext
        new SpringHelper(){}.setApplicationContext(context);
    }

    static {
        // setting user country language
        System.setProperty("user.country", Locale.CHINESE.getCountry());
        System.setProperty("user.language", Locale.CHINESE.getLanguage());

        System.setProperty("java.net.preferIPv4Stack", String.valueOf(true));
        System.setProperty("java.net.preferIPv4Addresses", String.valueOf(true));

        // 统一设置默认文件上传限制100MB, 单个文件 100M, 多文件总上传的数据 100M
        System.setProperty("spring.servlet.multipart.max-file-size", "100MB");
        System.setProperty("spring.servlet.multipart.max-request-size", "100MB");

    }
}
