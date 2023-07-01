package com.teaching.common.core;

import com.teaching.common.helper.NetworkHelper;
import com.teaching.common.helper.SnowIdHelper;
import com.teaching.common.helper.StringHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/6 15:44
 **/
public class RequestContextSession {
    /**
     * 请求ID
     */
    public String rid;

    /**
     * 访问域名
     */
    public String domain;

    /**
     * 请求方式
     */
    public String method;

    /**
     * URI
     */
    public String uri;

    /**
     * 操作
     */
    public String operate;

    /**
     * QUERY 数据
     */
    public String query;

    /**
     * UserAgent
     */
    public String userAgent;

    /**
     * 应用信息
     */
    public String device;

    /**
     * 客户端IP
     */
    public String clientIp;


    /**
     * 请求数据类型
     */
    public String requestContentType;

    /**
     * Token
     */
    public String signature;

    /**
     * 用户访问到达时间
     */
    public Long accessTime;

    /**
     * BODY 数据
     */
    public String requestBody;

    /**
     * BODY 数据
     */
    public String responseBody;

    /**
     * 用户ID
     */
    public String uid;

    /**
     *  扩展数据
     */
    public String ext;

    private static String header(HttpServletRequest request, RequestHeader headerKey) {
        return request.getHeader(headerKey.name());
    }

    public static RequestContextSession newBorn(HttpServletRequest request){
        RequestContextSession session = new RequestContextSession();
        session.rid = header(request, RequestHeader.R_ID);
        if (StringHelper.isBlank(session.rid)) {
            session.rid = SnowIdHelper.unique();
        }
        session.accessTime = System.currentTimeMillis();
        session.method = request.getMethod();
        // URL 数据
        session.uri = request.getRequestURI();
        session.query = request.getQueryString();
        session.domain = request.getServerName();
        // HEADER 数据
        session.requestContentType = request.getContentType();
        session.userAgent = header(request,RequestHeader.USER_AGENT);
        session.clientIp = NetworkHelper.ofClientIp(request);
        session.device = header(request, RequestHeader.DEVICE);
        session.signature = header(request, RequestHeader.SIGNATURE);
        // Signature 权限签名计算数据
        session.uid = session.ext = session.requestBody = session.responseBody = session.operate = StringHelper.EMPTY;
        return session;
    }
}
