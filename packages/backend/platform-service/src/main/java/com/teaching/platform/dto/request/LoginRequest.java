package com.teaching.platform.dto.request;

import com.teaching.common.core.IRequest;
import com.teaching.common.helper.EncryptHelper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/9 22:15
 **/
public class LoginRequest implements IRequest {

    /**
     * 学号
     */
    @Schema(description = "学号")
    @NotBlank(message = "学号不能为空")
    public String studentNo;
    /**
     * 用户密码
     */
    @Schema(title = "用户密码",description = "RSA加密传输")
    @NotBlank(message = "密码不能为空")
    public String password;

    @Schema(title = "验证码")
    @NotBlank(message = "验证码不能为空")
    public String code;

    @Schema(title = "验证码UUID")
    @NotBlank(message = "UUID不能为空")
    public String uuid = "";

    @Override
    public void verify() {
//        this.password = EncryptHelper.decryptByPrivateKey(EncryptHelper.PRI, this.password);
    }

}
