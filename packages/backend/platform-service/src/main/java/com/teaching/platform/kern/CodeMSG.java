package com.teaching.platform.kern;


import com.teaching.common.core.ICodeMSG;

/** 服务消息， code = port + sequence **/
public enum CodeMSG implements ICodeMSG {
    USER_PASS_ERROR(1000000001, "账号或密码不正确"),
    USER_DEl(1000000002, "账号已删除"),
    USER_STATUS_ERROR(1000000003, "账号已锁定"),









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
