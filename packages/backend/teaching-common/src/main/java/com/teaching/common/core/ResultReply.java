package com.teaching.common.core;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通用返回体
 * @author sacher
 **/
public final class ResultReply<T> {

    @JSONField(ordinal = 0)
    public int code;
    @JSONField(ordinal = 1)
    public String msg;
    @JSONField(ordinal = 2)
    public T data;

    private ResultReply(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResultReply() {
    }

    public static ResultReply<Void> onOk() {
        return onOk(null);
    }

    public static <R> ResultReply<R> onOk(final R results) {
        return new ResultReply<>(CommonCode.SUCCESS_OK.code(), CommonCode.SUCCESS_OK.msg(), results);
    }


}
