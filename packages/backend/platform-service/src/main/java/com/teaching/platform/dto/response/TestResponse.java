package com.teaching.platform.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/4 17:34
 **/
@ApiModel("响应参数")
public class TestResponse {
    @ApiModelProperty("openId")
    public String openid;
}
