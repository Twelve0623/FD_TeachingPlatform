package com.teaching.platform.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 课程章节详情信息(CourseChapterDetail)表实体类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Data
public class CourseChapterDetailResponse {

    /**
     * 章节详情信息
     */
    @Schema(title = "章节详情信息 类型为2时返回题库ID 类型为3时返回动画标识")
    private String detailContent;

    /**
     * 章节类型 0 富文本 1 代码块 2 题库 3 动画
     */
    @Schema(title = "章节类型 0 富文本 1 代码块 2 题库 3 动画")
    private Integer detailType;



}

