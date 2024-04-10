package com.teaching.platform.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 课程章节信息(CourseChapter)表实体类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Data
public class CourseChapterResponse  {
    @Schema(title = "id")
    private Long id;

    /**
     * 父节点id
     */
    @Schema(title = "父节点id")
    private Integer parentId;
    /**
     * 等级 0
     */
    @Schema(title = "等级")
    private Integer level;
    /**
     * 章节标题 例：绪论
     */
    @Schema(title = "章节标题 例：绪论")
    private String chapterName;
    /**
     * 章节描述 例：第一章
     */
    @Schema(title = "章节描述 例：第一章/1.1")
    private String chapterDesc;
    /**
     * 章节页面标记 是否页面内章节 0 否 1 是
     */
    @Schema(title = "章节页面标记 是否页面内章节 0 否 1 是")
    private Integer chapterTag;

    @Schema(title = "是否含有子章节")
    private Boolean hasChild;


}

