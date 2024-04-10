package com.teaching.platform.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(title = "id")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;
    /**
     * 课程名称
     */
    @Schema(title = "课程名称")
    private String courseName;
    /**
     * 课程简介
     */
    @Schema(title = "课程简介")
    private String courseProfile;
    /**
     * 课程图片
     */
    @Schema(title = "课程图片")
    private String courseImages;
    /**
     * 课程类型 0 自建课程 1 外链
     */
    @Schema(title = "课程类型 0 自建课程 1 外链")
    private Integer courseType;
    /**
     * 课程拓展字段（外链需要）
     */
    @Schema(title = "课程拓展字段（外链需要）")
    private String courseExt;

    @Schema(title = "是否开放 0 开放 1 未开放")
    private Integer courseStatus;

    @Schema(title = "章节数")
    private Long courseChapterNumber;

    @Schema(title = "学习人数")
    private Long learnedNumber;

}
