package com.teaching.platform.kern;


import com.teaching.common.core.ICodeMSG;

/** 服务消息， code = port + sequence **/
public enum CodeMSG implements ICodeMSG {
    USER_PASS_ERROR(1000000001, "账号或密码不正确"),
    USER_DEl(1000000002, "账号已删除"),
    USER_STATUS_ERROR(1000000003, "账号已锁定"),
    CODE_NO(1000000004, "验证码已经失效"),
    CODE_ERROR(1000000005, "验证码好像不对"),
    LOGIN_CODE_ERROR(1000000006,"验证码配置信息错误"),
    COURSE_CHAPTER_DETAIL_NO(1000000007,"该内容不存在"),
    COURSE_NO(1000000008,"当前课程不存在"),
    PASSWORD_AGAIN_ERROR(1000000009,"两次密码不相同"),









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
