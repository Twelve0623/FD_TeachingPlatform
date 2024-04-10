package com.teaching.platform.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Description：
 * @Author：sacher
 * @Create：2020/10/27 6:03 下午
 **/
public class CaptchaResponse  {
    @Schema(title = "图片BASE64")
    public String img;
    @Schema(title = "uuid")
    public String uuid;
}
