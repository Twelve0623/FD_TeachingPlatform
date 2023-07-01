package com.teaching.common.web;

import com.google.common.collect.Lists;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;

/**
 * @author sacher
 */
public final class StringMessageConverter extends StringHttpMessageConverter {
    public static final StringMessageConverter INSTANCE = new StringMessageConverter();

    private StringMessageConverter(){
        super(StandardCharsets.UTF_8);
        super.setWriteAcceptCharset(false);
        super.setSupportedMediaTypes(Lists.newArrayList(MediaType.TEXT_PLAIN,
                                                        MediaType.TEXT_HTML,
                                                        MediaType.TEXT_XML,
                                                        MediaType.APPLICATION_XML,
                                                        MediaType.ALL));
    }
}
