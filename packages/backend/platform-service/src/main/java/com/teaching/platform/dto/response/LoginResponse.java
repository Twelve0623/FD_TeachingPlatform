package com.teaching.platform.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/9 22:15
 **/
@ApiModel("学生登录响应值")
public class LoginResponse {

    @ApiModelProperty("用户Token")
    public String token;

    @ApiModelProperty("是否需要修改默认密码")
    public Boolean isDefault;
}
