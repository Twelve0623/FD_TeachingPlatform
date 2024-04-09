package com.teaching.platform.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/10/6 14:20
 **/
@Data
public class CoursePageResponse {
    /**
     * id
     */
    @ApiModelProperty("id")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    private String courseName;
    /**
     * 课程简介
     */
    @ApiModelProperty("课程简介")
    private String courseProfile;
    /**
     * 课程图片
     */
    @ApiModelProperty("课程图片")
    private String courseImages;
    /**
     * 课程类型 0 自建课程 1 外链
     */
    @ApiModelProperty("课程类型 0 自建课程 1 外链")
    private Integer courseType;
    /**
     * 课程拓展字段（外链需要）
     */
    @ApiModelProperty("课程拓展字段（外链需要）")
    private String courseExt;

    @ApiModelProperty("是否开放 0 开放 1 未开放")
    private Integer courseStatus;

    @ApiModelProperty("章节数")
    private Integer courseChapterNumber;

    @ApiModelProperty("学习人数")
    private Integer learnedNumber;

}
