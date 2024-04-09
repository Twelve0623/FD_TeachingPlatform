package com.teaching.platform.dto.response;

import com.baomidou.mybatisplus.annotation.TableName;
import com.teaching.common.core.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 课程章节信息(CourseChapter)表实体类
 *
 * @author sacher
 * @since 2023-10-06 14:05:03
 */
@Data
public class CourseChapterResponse  {
    @ApiModelProperty("id")
    private Long id;

    /**
     * 父节点id
     */
    @ApiModelProperty("父节点id")
    private Integer parentId;
    /**
     * 等级 0
     */
    @ApiModelProperty("等级")
    private Integer level;
    /**
     * 章节标题 例：绪论
     */
    @ApiModelProperty("章节标题 例：绪论")
    private String chapterName;
    /**
     * 章节描述 例：第一章
     */
    @ApiModelProperty("章节描述 例：第一章/1.1")
    private String chapterDesc;
    /**
     * 章节页面标记 是否页面内章节 0 否 1 是
     */
    @ApiModelProperty("章节页面标记 是否页面内章节 0 否 1 是")
    private Integer chapterTag;

    @ApiModelProperty("是否含有子章节")
    private Boolean hasChild;


}

