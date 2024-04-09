package com.teaching.platform.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/10/6 21:48
 **/
@Data
public class StudentResponse {
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String studentName;
    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String studentNo;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickName;
    /**
     * 专业名称
     */
    @ApiModelProperty("专业名称")
    private String specialityName;

    /**
     * 登录次数
     */
    @ApiModelProperty("登录次数")
    private Integer loginCount;
    /**
     * 最后一次登录时间
     */
    @ApiModelProperty("最后一次登录时间")
    private Date lastLoginTime;
}
