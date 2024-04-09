package com.teaching.platform.dto.response;


import io.swagger.annotations.ApiModelProperty;

/**
 * @Description：
 * @Author：sacher
 * @Create：2020/10/27 6:03 下午
 **/
public class CaptchaResponse  {
    @ApiModelProperty("图片BASE64")
    public String img;
    @ApiModelProperty("uuid")
    public String uuid;
}
