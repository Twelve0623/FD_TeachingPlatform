package com.teaching.platform.kern;


import com.teaching.common.core.ICodeMSG;

/** 服务消息， code = port + sequence **/
public enum CodeMSG implements ICodeMSG {
    NetWorkTimeOut(1000000001, "请求超时"),









    ;

    private final int code;
    private final String msg;
    CodeMSG(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }

    public class AppVersion {
    }
}
