package com.teaching.common.core;

/**
 * @author sacher
 */

public enum RequestHeader implements StringEnum {
    R_ID("X-Request-Id", "每次请求唯一编号"),

    DEVICE("X-Device", "应用信息"),

    SIGNATURE("X-Signature", "权限签名数据"),
    USER_AGENT("User-Agent", "User-Agent"),

    ;

    RequestHeader(String value, String desc) {
        changeNameTo(this, value);
    }
}
