package com.teaching.common.web;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.teaching.common.core.IConstant;
import com.teaching.common.core.RequestContextSession;
import com.teaching.common.core.RequestContextThreadHolder;
import com.teaching.common.helper.BytesHelper;
import com.teaching.common.helper.StringHelper;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/6 16:09
 **/
public interface IWebFilter extends Filter {

    Logger LOG = LoggerFactory.getLogger(IWebFilter.class);

    /**
     *
     * @param session
     * @param request
     * @param response
     * @return
     */
    default boolean authentication(RequestContextSession session, HttpServletRequest request, HttpServletResponse response){

        return true;
    }

    Set<String> BODY_METHODS = Sets.newHashSet("POST", "PUT", "PATCH", "DELETE");

    /**
     * body 获取
     * @param request
     * @param type
     * @return
     * @throws IOException
     */
    default Pair<? extends HttpServletRequest, String> bodyToString(HttpServletRequest request, String type) throws IOException {
        boolean noBody = !BODY_METHODS.contains(request.getMethod().toUpperCase());
        final String body =  noBody ? StringHelper.EMPTY : BytesHelper.string(request.getInputStream());
        return new Pair<>(new RequestWrapper(request, body), body);
    }

    class RequestWrapper extends HttpServletRequestWrapper {
        private final HttpServletRequest request;
        private final byte[] bodies;

        RequestWrapper(HttpServletRequest request, String body) {
            super(request); this.request = request;
            this.bodies = BytesHelper.utf8Bytes(body);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public int getContentLength() {
            return bodies.length;
        }
        @Override
        public String getContentType() {
            return MediaType.APPLICATION_JSON_UTF8_VALUE;
        }
        //过滤 Client 请求体数据
        @Override
        public ServletInputStream getInputStream() throws IOException {
            return 0 == bodies.length ? request.getInputStream() : BytesHelper.castServletInputStream(new ByteArrayInputStream(bodies));
        }
    }

    @Override
    default void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        //过滤请求
        if (IConstant.isActuatorEndpoint(uri) || IConstant.isStreamEndpoint(uri) || IConstant.isStaticsEndpoint(uri) || IConstant.isErrorEndpoint(uri)) {
            if(IConstant.isActuatorEndpoint(uri)){
                res.setContentType("application/vnd.spring-boot.actuator.v2+json;charset=UTF-8");
            }
            chain.doFilter(request, res);
            return;
        }
        //初始化数据
        RequestContextSession session = RequestContextThreadHolder.get().setSession(RequestContextSession.newBorn(request));
        HttpServletResponse response = (HttpServletResponse) res;
        try {
            if(authentication(session,request,response)){
                String type = StringHelper.defaultString(request.getContentType());
                if(!type.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE) || !type.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE) || !type.startsWith(MediaType.APPLICATION_XML_VALUE) || !type.startsWith(MediaType.TEXT_XML_VALUE)) {
                    Pair<? extends HttpServletRequest, String> bodyPair = bodyToString(request, type);
                    session.requestBody = bodyPair.getValue();
                    chain.doFilter(bodyPair.getKey(), response);
                }else {
                    chain.doFilter(request, res);
                }
            }
        } finally {
            if(!StringHelper.isBlank(session.operate)){
                LOG.info(JSONObject.toJSONString(session));
            }
            RequestContextThreadHolder.get().clear();
        }
    }

    @Override
    default void init(FilterConfig filterConfig) {
        LOG.debug("authentication init");
    }

    @Override
    default void destroy(){
        LOG.debug("authentication destroy");
    }
}
