package com.teaching.platform.dto.request;

import com.teaching.common.core.IRequest;
import com.teaching.common.helper.EncryptHelper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/9 22:15
 **/
@ApiModel("学生登录请求值")
public class LoginRequest implements IRequest {

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    @NotBlank(message = "学号不能为空")
    public String studentNo;
    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码",notes = "RSA加密传输")
    @NotBlank(message = "密码不能为空")
    public String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    public String code;

    @ApiModelProperty(value = "验证码UUID")
    @NotBlank(message = "UUID不能为空")
    public String uuid = "";

    @Override
    public void verify() {
        this.password = EncryptHelper.decryptByPrivateKey(EncryptHelper.PRI, this.password);
    }

}
