package com.teaching.common.web;

import com.google.common.collect.Lists;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author sacher
 */
public final class FormMessageConverter extends FormHttpMessageConverter {

    public static final FormMessageConverter INSTANCE = new FormMessageConverter();

    private FormMessageConverter(){
        setCharset(StandardCharsets.UTF_8);
        List<HttpMessageConverter<?>> converters = Lists.newArrayList();
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(StringMessageConverter.INSTANCE);
        converters.add(new SourceHttpMessageConverter());
        super.setPartConverters(converters);
        super.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA));
    }
}
