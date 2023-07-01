package com.teaching.platform.dto.request;

import com.teaching.common.core.IRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/6 21:38
 **/
@ApiModel("请求参数")
public class DemoRequest implements IRequest {

    @ApiModelProperty("openId")
    public String openId;

    @Override
    public void verify() {

    }
}
