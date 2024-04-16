package com.teaching.platform.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/10/6 14:20
 **/
public class CourseUserResponse {

    @Schema(title = "当前在线人数")
    public Long nowStudentCount;

    @Schema(title = "登录次数")
    public Long learningCount;

    @Schema(title = "累计学习时长")
    public Long learningDuration;

    @Schema(title = "学习进度")
    public List<CourseUserDetailDTO> courseUserDetailList;
}
