package com.teaching.common.web;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.IOUtils;
import com.google.common.collect.Sets;
import com.teaching.common.helper.CollectsHelper;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Set;

/**
 * @author sacher
 */
public final class FastJsonMessageConverter extends FastJsonHttpMessageConverter {
    public static final FastJsonMessageConverter INSTANCE = new FastJsonMessageConverter();

    private FastJsonMessageConverter() {
        super.setDefaultCharset(IOUtils.UTF8);
        super.getFastJsonConfig().setSerializerFeatures(serializerFeatures());
        super.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON_UTF8,
                MediaType.TEXT_XML,
                MediaType.APPLICATION_XML,
                MediaType.APPLICATION_FORM_URLENCODED,
                // spring-boot actuator V2_JSON
                MediaType.valueOf("application/vnd.spring-boot.actuator.v2+json;charset=UTF-8")));
    }

    private static SerializerFeature[] serializerFeatures(SerializerFeature ...features) {
        Set<SerializerFeature> fSet = Sets.newHashSet(
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.SkipTransientField,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.DisableCircularReferenceDetect
                                                     );
        if(!CollectsHelper.isNullOrEmpty(features)) {
            fSet.addAll(Arrays.asList(features));
        }
        return fSet.toArray(new SerializerFeature[0]);
    }
}
