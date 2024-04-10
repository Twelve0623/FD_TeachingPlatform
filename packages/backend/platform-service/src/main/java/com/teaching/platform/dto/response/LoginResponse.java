package com.teaching.platform.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/8/9 22:15
 **/
public class LoginResponse {

    @Schema(title = "用户Token")
    public String token;

    @Schema(title = "是否需要修改默认密码")
    public Boolean isDefault;
}
