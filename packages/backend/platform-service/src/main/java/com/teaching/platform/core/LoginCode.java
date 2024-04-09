package com.teaching.platform.core;


import com.teaching.common.core.ExpireKey;

/**
 * 登录验证码配置信息
 */
public class LoginCode {

    /**
     * 验证码配置
     */
    public LoginCodeEnum codeType;
    /**
     * 验证码有效期 分钟
     */
    public int expiration = ExpireKey.Minutes1.expire * 2;
    /**
     * 验证码内容长度
     */
    public int length = 2;
    /**
     * 验证码宽度
     */
    public int width = 111;
    /**
     * 验证码高度
     */
    public int height = 36;
    /**
     * 验证码字体
     */
    public String fontName = "Action Jackson";
    /**
     * 字体大小
     */
    public int fontSize = 25;

    public LoginCodeEnum getCodeType() {
        return codeType;
    }
}
