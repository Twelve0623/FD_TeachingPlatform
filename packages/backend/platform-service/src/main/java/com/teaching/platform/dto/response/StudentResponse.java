package com.teaching.platform.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(title = "姓名")
    private String studentName;
    /**
     * 学号
     */
    @Schema(title = "学号")
    private String studentNo;
    /**
     * 昵称
     */
    @Schema(title = "昵称")
    private String nickName;
    /**
     * 专业名称
     */
    @Schema(title = "专业名称")
    private String specialityName;

    /**
     * 登录次数
     */
    @Schema(title = "登录次数")
    private Integer loginCount;
    /**
     * 最后一次登录时间
     */
    @Schema(title = "最后一次登录时间")
    private Date lastLoginTime;
}
