package com.teaching.common.exception;

import com.teaching.common.core.ICodeMSG;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 11:27
 **/
public class ServiceException extends RuntimeException{
    private final int code;

    private final String msg;

    public ServiceException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(int code, String msg, String message) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(ICodeMSG codeMSG) {
        this(codeMSG.code(), codeMSG.msg());
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
