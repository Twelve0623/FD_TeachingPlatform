package com.teaching.platform.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/10/6 14:20
 **/
public class CourseUserResponse {

    @ApiModelProperty("当前在线人数")
    public Long nowStudentCount;

    @ApiModelProperty("登录次数")
    public Long learningCount;

    @ApiModelProperty("累计学习时长")
    public Long learningDuration;

    @ApiModelProperty("学习进度")
    public List<CourseUserDetailDTO> courseUserDetailList;
}
