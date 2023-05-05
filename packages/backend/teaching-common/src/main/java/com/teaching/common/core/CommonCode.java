package com.teaching.common.core;

/**
 * 公共错误码
 * @author sacher
 */
public enum CommonCode implements ICodeMSG {
    SUCCESS_OK(0, "成功"),

    UNAVAILABLE(400, "请求数据无效，服务器无法解析"),

    NO_AUTHORITY(401, "未授权访问或授权码过期，请登录授权后再继续操作"),

    FORBIDDEN(403, "您没有权限访问该服务"),

    NOT_FOUND(404, "资源未找到"),

    UN_SAFETY(406, "请求数据验证失败（您的网络环境可能不安全）"),

    INVALID_TOKEN(407, "token已过期"),

    ILLEGAL_TOKEN(408, "非法的token"),

    TIME_OUT(408, "请求服务处理超时"),

    INVALID_TIME(412, "客户端时间异常，请调整正确时间后再试"),

    REQUEST_MAX_EXCEEDED(413, "请求数据过大，服务器无法处理"),

    SV_ERROR(500, "服务异常，请联系系统管理员");


    private final int code;
    private final String desc;
    CommonCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override public int code() {
        return code;
    }

    @Override public String msg() {
        return desc;
    }
}
